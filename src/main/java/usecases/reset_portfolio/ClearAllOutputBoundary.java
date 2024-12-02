package usecases.reset_portfolio;

public interface ClearAllOutputBoundary {
    void prepareClearedView();
    void switchToBuyStock();
    void switchToRemoveStock();
    void switchToPredictRevenue();
    void switchToBacktest();
}
