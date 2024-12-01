package usecases.AddStock;

public interface AddStockInputBoundary {
    void execute(AddStockInputData addStockInputData);
    void switchBack();
}
