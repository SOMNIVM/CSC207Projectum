package usecases.AddStock;

public interface BuyStockInputBoundary {
    void execute(BuyStockInputData buyStockInputData);
    void switchBack();
}
