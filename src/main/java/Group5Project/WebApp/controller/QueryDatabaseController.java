package Group5Project.WebApp.controller;

import Group5Project.WebApp.model.Item;
import Group5Project.WebApp.model.Order;
import Group5Project.WebApp.model.QueryModels.IncomeModel;
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
import java.util.*;

import static Group5Project.WebApp.WebAppApplication.connection;

@Controller
public class QueryDatabaseController {

    private List<UserModel> query_userAccounts = new ArrayList<UserModel>();

    private List<ItemPopularity> query_ItemPopularity = new ArrayList<ItemPopularity>();

    private List<Order> query_OrdersByUsername = new ArrayList<Order>();

    private List<IncomeModel> query_TotalIncome = new ArrayList<IncomeModel>();

    private HelperMethods helperMethods = new HelperMethods();


    @GetMapping("/queryDatabase")
    public String queryDatabaseHome (Map<String, Object> model) throws SQLException {

        model.put("UserAccounts", query_userAccounts);
        model.put("ItemPopularity", query_ItemPopularity);
        model.put("OrdersByUsername", query_OrdersByUsername);
        model.put("TotalIncome", query_TotalIncome);
        model.put("ordersByUsernameModel", new OrdersByUserQueryDto());


        return "queryDatabase";
    }

    @GetMapping("/queryDatabase/getUserAccounts")
    public String GetUserAccounts (Map<String, Object> model) throws SQLException {
        //clear all other collections
        query_ItemPopularity.clear();
        query_OrdersByUsername.clear();
        query_TotalIncome.clear();

        LoadUsersFromDatabase();

        return "redirect:/queryDatabase";
    }

    @GetMapping("/queryDatabase/getItemPopularity")
    public String GetItemPopularity (Map<String, Object> model) throws SQLException {
        //clear all other collections
        query_userAccounts.clear();
        query_OrdersByUsername.clear();
        query_TotalIncome.clear();


        LoadItemPopularity();

        return "redirect:/queryDatabase";
    }

    @PostMapping("/queryDatabase/getOrdersByUserName")
    public String GetOrdersByUserName(Model model, OrdersByUserQueryDto dto) throws SQLException {

        query_userAccounts.clear();
        query_ItemPopularity.clear();
        query_TotalIncome.clear();

        LoadOrdersByUser(dto.getUserID());

        return "redirect:/queryDatabase";
    }

    @GetMapping("/queryDatabase/getTotalIncome")
    public String GetTotalIncome (Map<String, Object> model) throws SQLException {
        //clear all other collections
        query_userAccounts.clear();
        query_OrdersByUsername.clear();
        query_ItemPopularity.clear();


        CalculateTotalIncome();

        return "redirect:/queryDatabase";
    }


    private void LoadUsersFromDatabase() throws SQLException {

        query_userAccounts.clear();

        String sql = "select * from Customer_Accounts";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            String Username = rs.getString("Username");
            String ID = rs.getString("C_AID");
            String Email = rs.getString("Email");
            String Password = rs.getString("Password");

            UserModel newUser = new UserModel(Email, Username, Password, ID);

            query_userAccounts.add(newUser);
        }
    }

    private void LoadItemPopularity() throws SQLException
    {
        query_ItemPopularity.clear();

        Gson gson = new Gson();

        String sql = "select * from Order_History";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        Map<String, Integer> popularityMap = new HashMap<String, Integer>();

        while (rs.next())
        {
            Type type = new TypeToken<List<Item>>() {}.getType();

            String storedList = rs.getString("Items");

            List<Item> items = gson.fromJson(storedList, type);

            for (var i : items)
            {
                //check if item has already been found
                //if found, increase count by quantity
                if (popularityMap.containsKey(i.ItemName))
                {
                    int currentCount = popularityMap.get(i.ItemName);

                    int newCount = currentCount + i.Quantity;

                    popularityMap.put(i.ItemName, newCount);
                }
                else//otherwise add to dictionary with count of 1
                {
                    popularityMap.put(i.ItemName, i.Quantity);
                }

            }
        }

        //iterate over map and create itemPopularity objects
        for (Map.Entry<String, Integer> kvp : popularityMap.entrySet())
        {
            String name = kvp.getKey();
            int count = kvp.getValue();

            ItemPopularity itemPopularity = new ItemPopularity(name, count);

            query_ItemPopularity.add(itemPopularity);
        }

        // Sort query_ItemPopularity in ascending order
        Collections.sort(query_ItemPopularity, new Comparator<ItemPopularity>(){
            public int compare(ItemPopularity o1, ItemPopularity o2) {
                return o1.quantityOrdered.compareTo(o2.quantityOrdered);
            }
        });

    }

    private void LoadOrdersByUser(String userID) throws SQLException {

        query_OrdersByUsername.clear();

        Gson gson = new Gson();

        String sql = String.format("select * from Order_History where C_AID_FK == '%1$s'", userID);

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            Type type = new TypeToken<List<Item>>() {}.getType();

            String storedList = rs.getString("Items");

            List<Item> items = gson.fromJson(storedList, type);

            int id = rs.getInt("orderID");

            double Price = rs.getDouble("Price");

            String CompletedDate = rs.getString("Date");

            int studentID = rs.getInt("C_AID_FK");

            Order order = new Order(id, items, Price, helperMethods.getUsernameFromUserID(studentID), CompletedDate);

            query_OrdersByUsername.add(order);

        }
    }


    private void CalculateTotalIncome() throws SQLException {

        query_TotalIncome.clear();

        String sql = "select * from Order_History";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        double sum = 0;
        int numberOfOrders = 0;

        while (rs.next())
        {
            double totalPrice = rs.getDouble("Price");
            sum += totalPrice;
            numberOfOrders++;
        }

        IncomeModel incomeModel = new IncomeModel(sum, numberOfOrders);
        query_TotalIncome.add(incomeModel);
    }
}
