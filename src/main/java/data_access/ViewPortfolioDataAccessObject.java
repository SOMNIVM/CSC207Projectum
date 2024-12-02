package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.view_portfolio.ViewPortfolioDataAccessInterface;

/**
 * The data access object for viewing portfolio data.
 */
public class ViewPortfolioDataAccessObject implements ViewPortfolioDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessInterface;
    private final OnlineDataAccessInterface onlineDataAccessInterface;

    public ViewPortfolioDataAccessObject(LocalDataAccessInterface localDataAccess,
                                         OnlineDataAccessInterface onlineDataAccess) {
        this.localDataAccessInterface = localDataAccess;
        this.onlineDataAccessInterface = onlineDataAccess;
    }

    @Override
    public Portfolio getCurrentPortfolio() {
        return localDataAccessInterface.getCurrentPortfolio();
    }

    @Override
    public Map<String, Double> getSymbolToCurrentPrice() {
        final Portfolio portfolio = localDataAccessInterface.getCurrentPortfolio();
        final Map<String, List<Pair<String, Double>>> timeSeries = onlineDataAccessInterface
                .getBulkTimeSeriesIntraDay(portfolio, 1, Config.INTRADAY_PREDICT_INTERVAL);
        final Map<String, Double> result = new HashMap<>();
        for (String symbol: portfolio.getStockSymbols()) {
            result.put(symbol, timeSeries.get(symbol).get(0).getSecond());
        }
        return result;
    }

    @Override
    public Map<String, String> getNameToSymbolMap() {
        return localDataAccessInterface.getNameToSymbolMap();
    }

    @Override
    public Map<String, String> getSymbolToNameMap() {
        return localDataAccessInterface.getSymbolToNameMap();
    }
}
