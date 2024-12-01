package usecases.AddStock;

public interface AddStockOutputBoundary {
    void prepareSuccessView(AddStockOutputData addStockOutputData);
    void prepareFailView(String errorDescription);
    void switchBack();
}
