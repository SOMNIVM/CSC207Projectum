package interface_adapters.view_portfolio;

import usecases.view_portfolio.ViewPortfolioInputBoundary;

public class ViewPortfolioController {
    private final ViewPortfolioInputBoundary viewPortfolioUseCaseInteractor;
    public ViewPortfolioController(ViewPortfolioInputBoundary interactor) {
        this.viewPortfolioUseCaseInteractor = interactor;
    }
    public void execute() {
        viewPortfolioUseCaseInteractor.execute();
    }
    public void switchBack() {
        viewPortfolioUseCaseInteractor.switchBack();
    }
}
