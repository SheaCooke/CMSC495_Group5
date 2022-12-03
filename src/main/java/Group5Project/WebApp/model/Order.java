package Group5Project.WebApp.model;

import java.util.*;

public class Order {

    public UUID TempID = java.util.UUID.randomUUID();

    public int ID;
    public List<Item> ItemsInOrder = new ArrayList<>();

    public double TotalPrice;

    public String userName;

    public Date EstimatedCompletionDate;

    public String CompletedDate = "";

    public Order(List<Item> items, String userName)
    {
        this.ItemsInOrder = new ArrayList<Item>(items);
        this.TotalPrice = CalculateTotalPrice();
        this.EstimatedCompletionDate = CalculateEstimatedDate();
        this.userName = userName;
    }

    public Order(int id, List<Item> items, double totalPrice, String userName, String completedDate)
    {
        this.ID = id;
        this.ItemsInOrder = items;
        this.TotalPrice = totalPrice;
        this.CompletedDate = completedDate;
        this.userName = userName;
    }

    private double CalculateTotalPrice()
    {
        double total = 0;

        for (Item item: ItemsInOrder) {
            total += (item.Price * item.Quantity);
        }

        return total;
    }

    private Date CalculateEstimatedDate()
    {
        int estimatedTimePerItem = 30000 * ItemsInOrder.size(); //estimating 30 seconds per item

        Date completionDate = new Date( System.currentTimeMillis() + estimatedTimePerItem);

        return completionDate;
    }

}
