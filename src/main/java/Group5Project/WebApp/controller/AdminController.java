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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.Data.Menu.PopulateMenuItemsFromDatabase;
import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin (Model model) throws SQLException{

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

        String[] categoryOptions = {"entree","appetizer","dessert"};

        model.addAttribute("categoryOptions", categoryOptions);



        PopulateMenuItemsFromDatabase();

        model.addAttribute("un", CurrentUser.currentUserName);

        model.addAttribute("MenuItems", MenuItems);

        model.addAttribute("dto", new ItemDto());

        return "admin";
    }

    @PostMapping("/admin/AddNewItem")
    public String AddNewItem(Model model, ItemDto dto) throws SQLException {

        double price = tryParseDouble(dto.getItemPrice(), 0.0);

        //String sql = "insert into Menu_Items values ('1234', 'testCategory', 'testDesc', '100')";

        String sql = String.format("insert into Menu_Items (ItemName, Category, Description, Price)" +
                        "values ('%1$s', '%2$s', '%3$s', '%4$s')",
                dto.getItemName(), dto.getItemCategory(), dto.getItemDescription(), dto.getItemPrice());

        Statement statement = connection.createStatement();

        statement.execute(sql);



//        Item newItem = new Item(dto.getItemName(), 1, price, dto.getItemDescription(), dto.getItemCategory());
//
//        MenuItems.add(newItem);

        return "redirect:/admin";

    }

    @PostMapping("/admin/UpdateItem")
    public String UpdateItem(Model model, ItemDto dto) throws SQLException {

        String sql = String.format("update Menu_Items set ItemName='%1$s', Category='%2$s', Description='%3$s', Price='%4$s' " +
                        "where ItemName='%1$s'",
                dto.getItemName(), dto.getItemCategory(), dto.getItemDescription(), dto.getItemPrice());

        Statement statement = connection.createStatement();

        statement.execute(sql);

//        double price = tryParseDouble(dto.getItemPrice(), 0.0);
//
//        String name = dto.getItemName();
//
//        for (var i : MenuItems)
//        {
//            if (i.ItemName.equals(name))
//            {
//                i.Price = price;
//            }
//        }

        return "redirect:/admin";
    }

    @PostMapping("/admin/DeleteItem/{ID}")
    public String DeleteItem(@PathVariable final int ID) throws SQLException {

        String sql = String.format("delete from Menu_Items where ItemID=%1$s",ID);

        Statement statement = connection.createStatement();

        statement.execute(sql);

        //MenuItems.removeIf(i -> i.ID == ID);

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
