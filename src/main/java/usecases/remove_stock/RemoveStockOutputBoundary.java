package usecases.remove_stock;

public interface RemoveStockOutputBoundary {
    void prepareSuccessView(RemoveStockOutputData removeStockOutputData);
    void prepareFailView(String errorDescription);
    void switchBack();
}
