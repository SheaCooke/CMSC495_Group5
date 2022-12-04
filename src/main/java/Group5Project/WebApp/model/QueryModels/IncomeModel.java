package Group5Project.WebApp.model.QueryModels;

public class IncomeModel {
    public double totalIncome = 0;

    public int numberOfOrders = 0;

    public IncomeModel(double totalIncome, int numberOfOrders)
    {
        this.totalIncome = totalIncome;
        this.numberOfOrders = numberOfOrders;
    }
}
