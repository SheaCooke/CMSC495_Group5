package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.CurrentUser;
import Group5Project.WebApp.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.CompletedOrders.CompletedOrdersList;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;

@Controller
public class PastAndPendingOrdersController {

    @GetMapping("PastAndPendingOrders")
    public String PastAndPendingOrders (Map<String, Object> model) {
        model.put("CurrentPendingOrders", CurrentPendingOrders.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));
        model.put("CompletedOrdersList", CompletedOrdersList.stream().filter(p -> p.userName.equals(CurrentUser.currentUserName)).collect(Collectors.toList()));
        return "PastAndPendingOrders";
    }

    @GetMapping("/CompletedOrder/{ID}")
    public String CompletedOrder(@PathVariable UUID ID) {

        //get Order object
        Order order = CurrentPendingOrders.stream().filter(i -> i.ID.equals(ID)).findFirst().get();

        //remove from Pending orders
        CurrentPendingOrders.removeIf(x -> x.ID.equals(ID));

        //add to completed orders

        CompletedOrdersList.add(order);


        return "redirect:/PastAndPendingOrders";
    }


}
