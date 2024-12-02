package usecases.reset_portfolio;

public interface ClearAllInputBoundary {
    /**
     * Reset the portfolio to empty.
     */
    void execute();
    void switchToBuyStock();
    void switchToRemoveStock();
    void switchToPredictRevenue();
    void switchToBacktest();
}
