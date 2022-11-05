package Group5Project.WebApp.Data;
import Group5Project.WebApp.model.Item;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static Item item1 = new Item("item1", 1, 10);

    private static Item item2 = new Item("item2", 1, 15);

    public static List<Item> MenuItems = new ArrayList<Item>(){
        {add(item1); add(item2);}
    };

}
