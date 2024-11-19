package usecases.view_portfolio;

import entities.Portfolio;

import java.util.*;

public class ViewPortfolioInteractor implements ViewPortfolioInputBoundary{
    private final ViewPortfolioOutputBoundary viewPortfolioPresenter;
    private final ViewPortfolioDataAccessInterface viewPortfolioDataAccessObject;
    public ViewPortfolioInteractor(ViewPortfolioOutputBoundary presenter,
                                   ViewPortfolioDataAccessInterface dataAccessObject) {
        this.viewPortfolioPresenter = presenter;
        this.viewPortfolioDataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {
        ViewPortfolioOutputData outputData = new ViewPortfolioOutputData(viewPortfolioDataAccessObject.getStockNames(),
                viewPortfolioDataAccessObject.getShares(),
                viewPortfolioDataAccessObject.getAveragePrices(),
                viewPortfolioDataAccessObject.getValues());
        viewPortfolioPresenter.prepareView(outputData);
    }
}
