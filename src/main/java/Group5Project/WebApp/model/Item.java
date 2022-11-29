package Group5Project.WebApp.model;

import java.util.UUID;

public class Item {

    //public UUID ID = UUID.randomUUID();

    public int ID;

    public String ItemName;

    public int Quantity;

    public double Price;

    public String Description;

    public String Category;

    public Item(int id, String itemName, int quantity, double price, String description, String category)
    {
        this.ID = id;
        this.ItemName = itemName;
        this.Quantity = quantity;
        this.Price = price;
        this.Description = description;
        this.Category = category;
    }
}
