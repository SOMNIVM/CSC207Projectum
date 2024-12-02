package usecases.buy_stock;

public interface AddStockInputBoundary {
    void execute(AddStockInputData addStockInputData);
    void switchBack();
}
