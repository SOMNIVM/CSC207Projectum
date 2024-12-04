package interface_adapters.reset_portfolio;

import usecases.reset_portfolio.ClearAllInputBoundary;

/**
 * The controller for the reset portfolio use case.
 */
public class ClearAllController {
    private final ClearAllInputBoundary clearAllInteractor;

    public ClearAllController(ClearAllInputBoundary interactor) {
        this.clearAllInteractor = interactor;
    }

    /**
     * Clear the portfolio.
     */
    public void execute() {
        clearAllInteractor.execute();
    }

    /**
     * Switch to the buy stock use case.
     */
    public void switchToBuyStock() {
        clearAllInteractor.switchToBuyStock();
    }

    /**
     * Switch to the remove stock use case.
     */
    public void switchToRemoveStock() {
        clearAllInteractor.switchToRemoveStock();
    }

    /**
     * Switch to the predict revenue use case.
     */
    public void switchToPredictRevenue() {
        clearAllInteractor.switchToPredictRevenue();
    }

    /**
     * Switch to the model evaluation use case.
     */
    public void switchToBacktest() {
        clearAllInteractor.switchToBacktest();
    }
}
