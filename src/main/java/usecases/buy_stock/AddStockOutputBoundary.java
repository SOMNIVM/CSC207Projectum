package usecases.buy_stock;

public interface AddStockOutputBoundary {
    void prepareSuccessView(AddStockOutputData addStockOutputData);
    void prepareFailView(String errorDescription);
    void switchBack();
}
