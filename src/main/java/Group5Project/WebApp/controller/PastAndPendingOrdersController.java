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
import static Group5Project.WebApp.Data.CurrentUser.GetNotificationsByUserName;
import static Group5Project.WebApp.Data.CurrentUser.ResetNotificationCount;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class PastAndPendingOrdersController {

    @GetMapping("PastAndPendingOrders")
    public String PastAndPendingOrders (Map<String, Object> model) {

        ResetNotificationCount(CurrentUser.currentUserName);

        model.put("NewlyCompletedOrders", GetNotificationsByUserName(CurrentUser.currentUserName));

        model.put("CurrentPendingOrders", CurrentPendingOrders.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));
        model.put("CompletedOrdersList", CompletedOrdersList.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));

        return "PastAndPendingOrders";
    }

}
