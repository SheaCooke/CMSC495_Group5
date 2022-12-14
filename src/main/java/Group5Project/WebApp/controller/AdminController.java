package Group5Project.WebApp.controller;

import Group5Project.WebApp.Data.*;
import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import Group5Project.WebApp.model.QueryModels.ItemPopularity;
import Group5Project.WebApp.model.QueryModels.OrdersByUserQueryDto;
import Group5Project.WebApp.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static Group5Project.WebApp.Data.Menu.MenuItems;
import static Group5Project.WebApp.Data.Menu.PopulateMenuItemsFromDatabase;
import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class AdminController {

    private List<String> errorMessages = new ArrayList<String>();

    private HelperMethods helperMethods = new HelperMethods();

    @GetMapping("/admin")
    public String admin (Model model) throws SQLException{

        PopulateMenuItemsFromDatabase();

        model.addAttribute("errors", errorMessages);

        model.addAttribute("un", UserManager.currentUserName);

        model.addAttribute("MenuItems", MenuItems);

        model.addAttribute("dto", new ItemDto());

        return "admin";
    }

    @PostMapping("/admin/AddNewItem")
    public String AddNewItem(Model model, ItemDto dto) throws SQLException {

        errorMessages.clear();

        //check if item name already exists
        if (MenuItems.stream().anyMatch(i -> i.ItemName.equals(dto.getItemName())))
        {
            errorMessages.add("Item name already exists");

            return "redirect:/admin";
        }

        double price = helperMethods.tryParseDouble(dto.getItemPrice(), 0.0);

        String sql = String.format("insert into Menu_Items (ItemName, Category, Description, Price)" +
                        "values ('%1$s', '%2$s', '%3$s', '%4$s')",
                dto.getItemName(), dto.getItemCategory(), dto.getItemDescription(), dto.getItemPrice());

        Statement statement = connection.createStatement();

        statement.execute(sql);

        return "redirect:/admin";
    }

    @PostMapping("/admin/UpdateItem")
    public String UpdateItem(Model model, ItemDto dto) throws SQLException {

        errorMessages.clear();

        if (!MenuItems.stream().anyMatch(i -> i.ItemName.equals(dto.getItemName())))
        {
            errorMessages.add("Item with name " + dto.getItemName() + " could not be found");

            return "redirect:/admin";
        }

        String sql = String.format("update Menu_Items set ItemName='%1$s', Category='%2$s', Description='%3$s', Price='%4$s' " +
                        "where ItemName='%1$s'",
                dto.getItemName(), dto.getItemCategory(), dto.getItemDescription(), dto.getItemPrice());

        Statement statement = connection.createStatement();

        statement.execute(sql);

        return "redirect:/admin";
    }

    @PostMapping("/admin/DeleteItem/{ID}")
    public String DeleteItem(@PathVariable final int ID) throws SQLException {

        errorMessages.clear();

        String sql = String.format("delete from Menu_Items where ItemID=%1$s",ID);

        Statement statement = connection.createStatement();

        statement.execute(sql);

        return "redirect:/admin";
    }


}
