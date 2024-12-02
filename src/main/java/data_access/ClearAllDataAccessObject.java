package data_access;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.reset_portfolio.ClearAllDataAccessInterface;

import java.util.HashSet;
import java.util.Set;

public class ClearAllDataAccessObject implements ClearAllDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessObject;
    public ClearAllDataAccessObject(LocalDataAccessInterface localDataAccess) {
        this.localDataAccessObject = localDataAccess;
    }
    @Override
    public void ClearPortfolioData() {
        Portfolio portfolio = localDataAccessObject.getCurrentPortfolio();
        Set<String> symbols = new HashSet<>(portfolio.getStockSymbols());
        for (String symbol: symbols) {
            portfolio.removeStock(symbol, portfolio.getShares(symbol));
        }
        localDataAccessObject.writeCurrentPortfolio();
    }
}
