package Group5Project.WebApp.model.QueryModels;

public class ItemPopularity {

    public Integer quantityOrdered = 0;
    public String itemName = "";

    public ItemPopularity(String itemName ,int quantityOrdered)
    {
        this.itemName = itemName;
        this.quantityOrdered = quantityOrdered;
    }
}
