package usecases.reset_portfolio;

public class ClearAllInteractor implements ClearAllInputBoundary{
    private final ClearAllDataAccessInterface clearAllDataAccessObject;
    private final ClearAllOutputBoundary clearAllOutputPresenter;
    public ClearAllInteractor(ClearAllOutputBoundary presenter, ClearAllDataAccessInterface dataAccessObject) {
        this.clearAllOutputPresenter = presenter;
        this.clearAllDataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {
        clearAllDataAccessObject.ClearPortfolioData();
        clearAllOutputPresenter.prepareClearedView();
    }
}
