package Group5Project.WebApp.controller;
import Group5Project.WebApp.Data.Cart;
import Group5Project.WebApp.Data.Menu;
import Group5Project.WebApp.Data.UserDto;
import Group5Project.WebApp.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.Cart.addItem;
import static Group5Project.WebApp.Data.Menu.MenuItems;

@Controller
public class IndexController {

    @GetMapping("/")
    public String home (Map<String, Object> model) {

        //for testing, replace with query to sql

        MenuItems.clear();

        Item item1 = new Item(1,"item1", 1, 10);

        Item item2 = new Item(2,"item2", 1, 15);

        MenuItems.add(item1);
        MenuItems.add(item2);

        model.put("MenuItems", MenuItems);



        return "index";
    }

    @PostMapping("/AddToCart/{ID}")
    public String AddToCart(@PathVariable final int ID) {

        Item itemToAdd = MenuItems.stream().filter(i -> i.ID == ID).findFirst().get();

        addItem(itemToAdd);

        return "redirect:/";
    }



}
