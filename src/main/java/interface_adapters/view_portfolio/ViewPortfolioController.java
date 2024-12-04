package interface_adapters.view_portfolio;

import usecases.view_portfolio.ViewPortfolioInputBoundary;

/**
 * The controller of the view portfolio use case.
 */
public class ViewPortfolioController {
    private final ViewPortfolioInputBoundary viewPortfolioUseCaseInteractor;

    public ViewPortfolioController(ViewPortfolioInputBoundary interactor) {
        this.viewPortfolioUseCaseInteractor = interactor;
    }

    /**
     * Execute the view portfolio use case.
     */
    public void execute() {
        viewPortfolioUseCaseInteractor.execute();
    }

    /**
     * Execute the view portfolio use case.
     */
    public void switchBack() {
        viewPortfolioUseCaseInteractor.switchBack();
    }
}
