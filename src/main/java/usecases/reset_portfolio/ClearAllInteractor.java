package usecases.reset_portfolio;

/**
 * The use case interactor for clearing the portfolio.
 * It also has the role of redirecting the user to other views from the homepage.
 * Since clearing portfolio is a simple use case with minimal information to display,
 * there is no dedicated view for clear all, and thus the homepage acts as the de-facto "clear all view".
 */
public class ClearAllInteractor implements ClearAllInputBoundary {
    private final ClearAllDataAccessInterface clearAllDataAccessObject;
    private final ClearAllOutputBoundary clearAllOutputPresenter;

    public ClearAllInteractor(ClearAllOutputBoundary presenter, ClearAllDataAccessInterface dataAccessObject) {
        this.clearAllOutputPresenter = presenter;
        this.clearAllDataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {
        clearAllDataAccessObject.clearPortfolioData();
        clearAllOutputPresenter.prepareClearedView();
    }

    @Override
    public void switchToBuyStock() {
        clearAllOutputPresenter.switchToBuyStock();
    }

    @Override
    public void switchToRemoveStock() {
        clearAllOutputPresenter.switchToRemoveStock();
    }

    @Override
    public void switchToPredictRevenue() {
        clearAllOutputPresenter.switchToPredictRevenue();
    }

    @Override
    public void switchToBacktest() {
        clearAllOutputPresenter.switchToBacktest();
    }
}
