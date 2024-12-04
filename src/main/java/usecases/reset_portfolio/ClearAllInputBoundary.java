package usecases.reset_portfolio;

/**
 * The input boundary for the clear all use case.
 */
public interface ClearAllInputBoundary {
    /**
     * Reset the portfolio to empty.
     */
    void execute();

    /**
     * Switch to the buy stock view.
     */
    void switchToBuyStock();

    /**
     * Switch to the remove stock view.
     */
    void switchToRemoveStock();

    /**
     * Switch to the predict revenue view.
     */
    void switchToPredictRevenue();

    /**
     * Switch to the model evaluation view.
     */
    void switchToBacktest();
}
