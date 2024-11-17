package interface_adapters.reset_portfolio;

import usecases.reset_portfolio.ClearAllInputBoundary;

public class ClearAllController {
    private final ClearAllInputBoundary clearAllInputBoundary;
    public ClearAllController(ClearAllInputBoundary clearAllInputBoundary) {
        this.clearAllInputBoundary = clearAllInputBoundary;
    }

    /**
     * Clear the portfolio.
     */
    public void execute() {
        clearAllInputBoundary.execute();
    }
}
