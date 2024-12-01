package data_access;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.reset_portfolio.ClearAllDataAccessInterface;

public class ClearAllDataAccessObject implements ClearAllDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessObject;
    public ClearAllDataAccessObject(LocalDataAccessInterface localDataAccess) {
        this.localDataAccessObject = localDataAccess;
    }
    @Override
    public void ClearPortfolioData() {
        Portfolio portfolio = localDataAccessObject.getCurrentPortfolio();
        for (String symbol: portfolio.getStockSymbols()) {
            portfolio.removeStock(symbol, portfolio.getShares(symbol));
        }
        localDataAccessObject.writeCurrentPortfolio();
    }
}
