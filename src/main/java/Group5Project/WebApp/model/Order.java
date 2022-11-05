package Group5Project.WebApp.model;

import java.util.*;

public class Order {

    public List<Item> ItemsInOrder = new ArrayList<>();

    public double TotalPrice;

    public int EstimatedTimeToComplete;

    public Order(List<Item> items)
    {
        this.ItemsInOrder = items;
        this.TotalPrice = CalculateTotalPrice();
        this.EstimatedTimeToComplete = CalculateEstimatedTime();
    }

    private double CalculateTotalPrice()
    {
        double total = 0;

        for (Item item: ItemsInOrder) {
            total += item.Price;
        }

        return total;
    }

    private int CalculateEstimatedTime()
    {
        int estimatedTimePerItem = 30; //estimating 30 seconds per item

        return estimatedTimePerItem * ItemsInOrder.size();
    }

}