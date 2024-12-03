package data_access;

import java.util.HashSet;
import java.util.Set;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.reset_portfolio.ClearAllDataAccessInterface;

/**
 * The data access object for the reset portfolio usecase.
 */
public class ClearAllDataAccessObject implements ClearAllDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessObject;

    public ClearAllDataAccessObject(LocalDataAccessInterface localDataAccess) {
        this.localDataAccessObject = localDataAccess;
    }

    @Override
    public void ClearPortfolioData() {
        final Portfolio portfolio = localDataAccessObject.getCurrentPortfolio();
        final Set<String> symbols = new HashSet<>(portfolio.getStockSymbols());
        for (String symbol: symbols) {
            portfolio.removeStock(symbol, portfolio.getShares(symbol));
        }
        localDataAccessObject.writeCurrentPortfolio();
    }
}
