package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.UserManager;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import Group5Project.WebApp.model.UserModel;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.UserManager.*;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.WebAppApplication.connection;


@Controller
public class CartController {

    private HelperMethods helperMethods = new HelperMethods();
    @GetMapping("Cart")
    public String Cart (Map<String, Object> model) {

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(UserManager.currentUserName));

        Cart cartToModoify = helperMethods.GetCartByUserName(UserManager.currentUserName);

        model.put("ItemsInCart", cartToModoify.ItemsInCart);

        return "Cart";
    }

    @PostMapping("/Checkout")
    public String Checkout() {

        Cart cartToModoify = helperMethods.GetCartByUserName(UserManager.currentUserName);

        if (cartToModoify.ItemsInCart.size() == 0)
        {
            return "redirect:Cart";
        }

        Order order = new Order(cartToModoify.ItemsInCart, UserManager.currentUserName);

        //create timer task
        TimerHelper timerHelper = new TimerHelper();

        //timerHelper.ID = order.ID;
        timerHelper.tempID = order.TempID;

        Timer timer = new Timer(true);

        timer.schedule(timerHelper, order.EstimatedCompletionDate);

        CurrentPendingOrders.add(order);

        cartToModoify.ItemsInCart.clear();

        return "redirect:PastAndPendingOrders";
    }

    class TimerHelper extends TimerTask
    {
        //public int ID;

        public UUID tempID;
        @SneakyThrows
        @Override
        public void run()
        {
            //Order order = CurrentPendingOrders.stream().filter(i -> i.ID == ID).findFirst().get();
            Order order = CurrentPendingOrders.stream().filter(i -> i.TempID.equals(tempID)).findFirst().get();


            createCompletedOrderInDB(order);

            //remove from Pending orders
            //CurrentPendingOrders.removeIf(x -> x.ID == ID);
            CurrentPendingOrders.removeIf(x -> x.TempID.equals(tempID));


            //add to completed orders

            CompletedOrdersList.add(order);

            IncrementNotificationCount(UserManager.currentUserName);
        }

        private void createCompletedOrderInDB(Order order) throws SQLException {

            String orderPriceString = String.valueOf(order.TotalPrice);

            System.out.println("total price: " + orderPriceString);

            String sql = String.format("insert into Order_History (Date, Price, Items, C_AID_FK)" +
                            "values ('%1$s', '%2$s', '%3$s', '%4$s')",
                    helperMethods.getDayMonthYear(), orderPriceString, formatOrderItems(order.ItemsInOrder), getUserIDByUserName(order.userName));

            Statement statement = connection.createStatement();

            statement.execute(sql);
        }

//        private String getDayMonthYear()
//        {
//
//            Date date = new Date();
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
//            String day = simpleDateFormat.format(date);
//
//            simpleDateFormat = new SimpleDateFormat("MMMM");
//            String month = simpleDateFormat.format(date).toUpperCase();
//
//            simpleDateFormat = new SimpleDateFormat("YYYY");
//            String year = simpleDateFormat.format(date).toUpperCase();
//
//            String res = day+ "-" + month + "-" + year;
//
//            return res;
//        }

        private String getUserIDByUserName(String username)
        {
            UserModel user = Users.stream().filter(i -> i.getUsername().equals(username)).findFirst().get();

            String res = user.getStudentID();

            return res;
        }

        private String formatOrderItems(List<Item> items)
        {
            Gson gson = new Gson();

            String formattedList = gson.toJson(items);

            return formattedList;
        }

    }
}
