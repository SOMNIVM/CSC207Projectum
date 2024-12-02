package data_access;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.view_portfolio.ViewPortfolioDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Portfolio portfolio = localDataAccessInterface.getCurrentPortfolio();
        List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        Map<String, List<Pair<String, Double>>> timeSeries = onlineDataAccessInterface
                .getBulkTimeSeriesIntraDay(symbols, 1, Config.INTRADAY_PREDICT_INTERVAL);
        Map<String, Double> result = new HashMap<>();
        for (String symbol: symbols) {
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
