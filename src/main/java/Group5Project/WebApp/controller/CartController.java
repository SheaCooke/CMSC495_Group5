package Group5Project.WebApp.controller;

import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;

@Controller
public class CartController {

    @GetMapping("Cart")
    public String Cart (Map<String, Object> model) {

        model.put("ItemsInCart", ItemsInCart);

        return "Cart";
    }

    @PostMapping("/Checkout")
    public String Checkout() {

        Order order = new Order(ItemsInCart);

        CurrentPendingOrders.add(order);

        ItemsInCart.clear();

        return "redirect:PastAndPendingOrders";
    }
}
