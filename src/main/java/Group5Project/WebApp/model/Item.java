package Group5Project.WebApp.model;

public class Item {

    public String ItemName;

    public int Quantity;

    public double Price;

    public Item(String itemName, int quantity, double price)
    {
        this.ItemName = itemName;
        this.Quantity = quantity;
        this.Price = price;
    }
}
