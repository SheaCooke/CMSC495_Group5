package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.*;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Cart.ItemsInCart;
import static Group5Project.WebApp.Data.Cart.addItem;
import static Group5Project.WebApp.Data.Menu.MenuItems;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin (Model model) {

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String username = "";
//
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails)principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        CurrentUser.currentUserName = username;

        model.addAttribute("un", CurrentUser.currentUserName);

        model.addAttribute("MenuItems", MenuItems);

        model.addAttribute("dto", new ItemDto());

        return "admin";
    }

    @PostMapping("/admin/AddNewItem")
    public String AddNewItem(Model model, ItemDto dto) {

        double price = tryParseDouble(dto.getItemPrice(), 0.0);

        Item newItem = new Item(dto.getItemName(), 1, price);

        MenuItems.add(newItem);

        return "redirect:/admin";

    }

    public double tryParseDouble(String value, double defaultVal) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

}
