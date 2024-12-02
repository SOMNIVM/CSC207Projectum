package usecases.remove_stock;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;

import java.util.Map;

public class RemoveStockInteractor implements RemoveStockInputBoundary {
    private final RemoveStockOutputBoundary removeStockPresenter;
    private final LocalDataAccessInterface removeStockDataAccessInterface;
    public RemoveStockInteractor(RemoveStockOutputBoundary presenter, LocalDataAccessInterface dataAccessInterface) {
        this.removeStockPresenter = presenter;
        this.removeStockDataAccessInterface = dataAccessInterface;
    }

    @Override
    public void execute(RemoveStockInputData removeStockInputData) {
        Portfolio portfolio = removeStockDataAccessInterface.getCurrentPortfolio();
        String stockName = removeStockInputData.getStockName();
        String symbol = removeStockDataAccessInterface.getNameToSymbolMap().get(stockName);
        if (portfolio.getStockSymbols().contains(symbol)) {
            int sharesToRemove = removeStockInputData.getSharesToRemove();
            int existingShares = portfolio.getShares(symbol);
            if (sharesToRemove <= existingShares) {
                portfolio.removeStock(symbol, sharesToRemove);
                removeStockDataAccessInterface.writeCurrentPortfolio();
                removeStockPresenter.prepareSuccessView(new RemoveStockOutputData(stockName,
                        sharesToRemove,
                        portfolio.getStockSymbols().isEmpty()));
            }
            else {
                removeStockPresenter.prepareFailView(
                        String.format("You only have %d shares of %s stock.", existingShares, stockName));
            }
        }
        else {
            removeStockPresenter.prepareFailView("The stock to be removed is not in your portfolio.");
        }
    }

    @Override
    public void switchBack() {
        removeStockPresenter.switchBack();
    }
}
