package Group5Project.WebApp.model;

import java.util.*;

public class Order {

    public UUID ID = java.util.UUID.randomUUID();
    public List<Item> ItemsInOrder = new ArrayList<>();

    public double TotalPrice;

    //public int EstimatedTimeToComplete;

    public Date EstimatedCompletionDate;

    public Order(List<Item> items)
    {
        this.ItemsInOrder = new ArrayList<Item>(items);
        this.TotalPrice = CalculateTotalPrice();
        //this.EstimatedTimeToComplete = CalculateEstimatedTime();
        this.EstimatedCompletionDate = CalculateEstimatedDate();
    }

    private double CalculateTotalPrice()
    {
        double total = 0;

        for (Item item: ItemsInOrder) {
            total += (item.Price * item.Quantity);
        }

        return total;
    }

    private int CalculateEstimatedTime()
    {
        int estimatedTimePerItem = 30; //estimating 30 seconds per item

        return estimatedTimePerItem * ItemsInOrder.size();
    }

    private Date CalculateEstimatedDate()
    {
        int estimatedTimePerItem = 30000 * ItemsInOrder.size(); //estimating 30 seconds per item

        Date completionDate = new Date( System.currentTimeMillis() + estimatedTimePerItem);

        return completionDate;
    }

}
