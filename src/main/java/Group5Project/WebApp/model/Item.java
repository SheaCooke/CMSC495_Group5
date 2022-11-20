package Group5Project.WebApp.model;

import java.util.UUID;

public class Item {

    public UUID ID = UUID.randomUUID();
    public String ItemName;

    public int Quantity;

    public double Price;

    public Item(/*int id,*/ String itemName, int quantity, double price)
    {
        //this.ID = id;
        this.ItemName = itemName;
        this.Quantity = quantity;
        this.Price = price;
    }
}
