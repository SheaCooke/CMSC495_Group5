package Group5Project.WebApp.Data;
import Group5Project.WebApp.model.Item;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    public List<Item> ItemsInCart = new ArrayList<Item>();

    public String UserName = "";

    public boolean addItem(Item itemToAdd)
    {
        for (var item : this.ItemsInCart)
        {
            if (item.ID.equals(itemToAdd.ID))
            {
                item.Quantity += 1;
                return true;
            }
        }

        this.ItemsInCart.add(itemToAdd);

        return true;
    }

    public Cart(String userName)
    {
        this.UserName = userName;
    }

}
