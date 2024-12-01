package usecases.buy_stock;

public interface BuyStockInputBoundary {
    void execute(BuyStockInputData buyStockInputData);
    void switchBack();
}
