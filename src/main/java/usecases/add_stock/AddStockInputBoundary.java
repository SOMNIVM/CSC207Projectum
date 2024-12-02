package usecases.add_stock;

public interface AddStockInputBoundary {
    void execute(AddStockInputData addStockInputData);
    void switchBack();
}
