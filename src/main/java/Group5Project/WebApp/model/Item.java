package Group5Project.WebApp.model;
public class Item {

    public int ID;
    public String ItemName;

    public int Quantity;

    public double Price;

    public Item(int id, String itemName, int quantity, double price)
    {
        this.ID = id;
        this.ItemName = itemName;
        this.Quantity = quantity;
        this.Price = price;
    }
}
