package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.UserManager;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.UserManager.*;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class PastAndPendingOrdersController {

    private HelperMethods helperMethods = new HelperMethods();

    @GetMapping("PastAndPendingOrders")
    public String PastAndPendingOrders (Map<String, Object> model) throws SQLException {

        ResetNotificationCount(UserManager.currentUserName);

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(UserManager.currentUserName));

        //read completed orders in from database
        PopulateMenuItemsFromDatabase();

        model.put("CurrentPendingOrders", CurrentPendingOrders.stream().filter(p -> p.userName.equals(UserManager.currentUserName)).collect(Collectors.toList()));
        model.put("CompletedOrdersList", helperMethods.Get5RecentOrdersForUser(currentUserName));


        return "PastAndPendingOrders";
    }

    private void PopulateMenuItemsFromDatabase() throws SQLException {

        CompletedOrdersList.clear();

        Gson gson = new Gson();

        String sql = "select * from Order_History";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            Type type = new TypeToken<List<Item>>() {}.getType();

            String storedList = rs.getString("Items");

            List<Item> items = gson.fromJson(storedList, type);

            int id = rs.getInt("orderID");

            double Price = rs.getDouble("Price");

            String CompletedDate = rs.getString("Date");

            int studentID = rs.getInt("C_AID_FK");

            Order order = new Order(id, items, Price, helperMethods.getUsernameFromUserID(studentID), CompletedDate);

            CompletedOrdersList.add(order);
        }
    }
//    private String getUsernameFromUserID(int userId)
//    {
//        String idString = String.valueOf(userId);
//
//        return Users.stream().filter(i -> i.getStudentID().equals(idString)).findFirst().get().getUsername();
//    }
//
//    private List<Order> Get5RecentOrdersForUser(String username)
//    {
//        List<Order> ordersByUserName =
//            CompletedOrdersList.stream().filter(p -> p.userName.equals(UserManager.currentUserName))
//                    .collect(Collectors.toList());
//
//        Collections.sort(ordersByUserName, new Comparator<Order>(){
//            public int compare(Order o1, Order o2) {
//                return o1.CompletedDate.compareTo(o2.CompletedDate);
//            }
//        });
//
//        int listSize = ordersByUserName.size();
//
//        return ordersByUserName.subList(Math.max(listSize - 5, 0), listSize);
//    }

}
