package usecases.AddStock;

public interface BuyStockOutputBoundary {
    void prepareSuccessView(BuyStockOutputData buyStockOutputData);
    void prepareFailView(String errorDescription);
    void switchBack();
}
