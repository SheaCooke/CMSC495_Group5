package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.CurrentUser;
import Group5Project.WebApp.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collectors;


import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.CompletedOrders.newlyCompletedOrders;
import static Group5Project.WebApp.Data.CurrentUser.GetNotificationsByUserName;
import static Group5Project.WebApp.Data.CurrentUser.ResetNotificationCount;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class PastAndPendingOrdersController {

    @GetMapping("PastAndPendingOrders")
    public String PastAndPendingOrders (Map<String, Object> model) throws SQLException {

//        String sql = "insert into contacts values ('fName', 'lName')";
//		Statement statement = connection.createStatement();
//		statement.execute(sql);


        //CurrentUser.currentView = "PastAndPendingOrders";

        ResetNotificationCount(CurrentUser.currentUserName);

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(CurrentUser.currentUserName));

        model.put("CurrentPendingOrders", CurrentPendingOrders.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));
        model.put("CompletedOrdersList", CompletedOrdersList.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));

        return "PastAndPendingOrders";
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
//        return url;
//    }




}
