package interface_adapters.reset_portfolio;

import usecases.reset_portfolio.ClearAllInputBoundary;

public class ClearAllController {
    private final ClearAllInputBoundary clearAllUseCaseInteractor;
    public ClearAllController(ClearAllInputBoundary interactor) {
        this.clearAllUseCaseInteractor = interactor;
    }

    /**
     * Clear the portfolio.
     */
    public void execute() {
        clearAllUseCaseInteractor.execute();
    }
}
