package Group5Project.WebApp.Data;
import Group5Project.WebApp.model.Item;
import org.springframework.security.core.parameters.P;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static Group5Project.WebApp.WebAppApplication.connection;

public class Menu {

    public static List<Item> MenuItems = new ArrayList<Item>();

    public static void PopulateMenuItemsFromDatabase() throws SQLException {

        MenuItems.clear();

        String sql = "select * from Menu_Items";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int ID = rs.getInt("ItemID");
            String ItemName = rs.getString("ItemName");
            int Quantity = 1;
            double Price = rs.getDouble("Price");
            String Description = rs.getString("Description");
            String Category = rs.getString("Category");

            Item item = new Item(ID, ItemName, Quantity, Price, Description, Category);

            MenuItems.add(item);
        }

    }



}
