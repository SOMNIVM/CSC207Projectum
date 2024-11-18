package usecases.buy_stock;

public interface BuyStockOutputBoundary {
    void prepareSuccessView(double buyingPrice);
    void prepareFailView(String errorDescription);
}
