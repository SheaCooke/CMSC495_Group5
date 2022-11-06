package Group5Project.WebApp.Data;
import Group5Project.WebApp.model.Item;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static List<Item> ItemsInCart = new ArrayList<Item>();



    public static boolean addItem(Item itemToAdd)
    {
        for (var item : ItemsInCart)
        {
            if (item.ID == itemToAdd.ID)
            {
                item.Quantity += 1;
                return true;
            }
        }

        ItemsInCart.add(itemToAdd);

        return true;
    }

}
