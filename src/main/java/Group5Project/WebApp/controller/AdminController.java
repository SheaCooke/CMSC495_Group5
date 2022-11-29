package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.*;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.WebAppApplication.connection;

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
    public String AddNewItem(Model model, ItemDto dto) throws SQLException {

        double price = tryParseDouble(dto.getItemPrice(), 0.0);

        //String sql = "insert into Menu_Items values ('1234', 'testCategory', 'testDesc', '100')";

        String sql = String.format("insert into Menu_Items (Category, Description, Price)" +
                        "values ('%1$s', '%2$s', '%3$s')",
                dto.getItemCategory(), dto.getItemDescription(), dto.getItemPrice());

        Statement statement = connection.createStatement();

        statement.execute(sql);



        Item newItem = new Item(dto.getItemName(), 1, price);

        MenuItems.add(newItem);

        return "redirect:/admin";

    }

    @PostMapping("/admin/UpdateItem")
    public String UpdateItem(Model model, ItemDto dto) {

        double price = tryParseDouble(dto.getItemPrice(), 0.0);

        String name = dto.getItemName();

        for (var i : MenuItems)
        {
            if (i.ItemName.equals(name))
            {
                i.Price = price;
            }
        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/DeleteItem/{ID}")
    public String DeleteItem(@PathVariable final UUID ID) {

        MenuItems.removeIf(i -> i.ID.equals(ID));

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
