package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.CurrentUser;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.CurrentUser.GetNotificationsByUserName;
import static Group5Project.WebApp.Data.CurrentUser.IncrementNotificationCount;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.controller.IndexController.GetCartByUserName;

@Controller
public class CartController {

    @GetMapping("Cart")
    public String Cart (Map<String, Object> model) {

        //CurrentUser.currentView = "/Cart";

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(CurrentUser.currentUserName));

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        model.put("ItemsInCart", cartToModoify.ItemsInCart);

        return "Cart";
    }


    @PostMapping("/Checkout")
    public String Checkout() {

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        if (cartToModoify.ItemsInCart.size() == 0)
        {
            return "redirect:Cart";
        }

        Order order = new Order(cartToModoify.ItemsInCart, CurrentUser.currentUserName);

        //create timer task
        TimerHelper timerHelper = new TimerHelper();

        timerHelper.ID = order.ID;

        Timer timer = new Timer(true);

        timer.schedule(timerHelper, order.EstimatedCompletionDate);


        CurrentPendingOrders.add(order);

        cartToModoify.ItemsInCart.clear();

        return "redirect:PastAndPendingOrders";
    }

//    @GetMapping("/CompletedOrder/{ID}")
//    public String CompletedOrder(@PathVariable UUID ID) {
//
//        //get Order object
//        Order order = CurrentPendingOrders.stream().filter(i -> i.ID.equals(ID)).findFirst().get();
//
//        //remove from Pending orders
//        CurrentPendingOrders.removeIf(x -> x.ID.equals(ID));
//
//        //add to completed orders
//
//        CompletedOrdersList.add(order);
//
//        newlyCompletedOrders++;
//
//        String url = "redirect:/" + CurrentUser.currentView;
//
//        return "redirect:Cart";
//    }

    //@Component
    class TimerHelper extends TimerTask
    {
        public UUID ID;
        @Override
        public void run()
        {
            Order order = CurrentPendingOrders.stream().filter(i -> i.ID.equals(ID)).findFirst().get();

            //remove from Pending orders
            CurrentPendingOrders.removeIf(x -> x.ID.equals(ID));

            //add to completed orders

            CompletedOrdersList.add(order);

            IncrementNotificationCount(CurrentUser.currentUserName);
        }

    }
}
