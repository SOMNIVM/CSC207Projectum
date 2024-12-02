package usecases.remove_stock;

public interface RemoveStockInputBoundary {
    void execute(RemoveStockInputData removeStockInputData);
    void switchBack();
}
