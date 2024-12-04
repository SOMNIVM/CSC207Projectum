package usecases.reset_portfolio;

/**
 * The output boundary for the clear all use case.
 */
public interface ClearAllOutputBoundary {
    /**
     * Prepare the view for demonstrating the completion of clearance.
     */
    void prepareClearedView();

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
