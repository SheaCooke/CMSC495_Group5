package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.CurrentUser;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.Data.PendingOrders.CurrentPendingOrders;
import static Group5Project.WebApp.controller.IndexController.GetCartByUserName;

@Controller
public class CartController {

    @GetMapping("/Cart")
    public String Cart (Map<String, Object> model) {

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        model.put("ItemsInCart", cartToModoify.ItemsInCart);

        return "Cart";
    }


    @PostMapping("/Checkout")
    public String Checkout() {

        Cart cartToModoify = GetCartByUserName(CurrentUser.currentUserName);

        Order order = new Order(cartToModoify.ItemsInCart, CurrentUser.currentUserName);

        CurrentPendingOrders.add(order);

        cartToModoify.ItemsInCart.clear();

        return "redirect:PastAndPendingOrders";
    }
}
