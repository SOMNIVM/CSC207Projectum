package interface_adapters.reset_portfolio;

import usecases.reset_portfolio.ClearAllInputBoundary;

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

    public void switchToBuyStock() {
        clearAllInteractor.switchToBuyStock();
    }

    public void switchToRemoveStock() {
        clearAllInteractor.switchToRemoveStock();
    }

    public void switchToPredictRevenue() {
        clearAllInteractor.switchToPredictRevenue();
    }

    public void switchToBacktest() {
        clearAllInteractor.switchToBacktest();
    }
}
