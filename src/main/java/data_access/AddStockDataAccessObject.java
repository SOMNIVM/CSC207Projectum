package data_access;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.add_stock.AddStockDataAccessInterface;

import java.util.List;
import java.util.Map;

public class AddStockDataAccessObject implements AddStockDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessObject;
    private final OnlineDataAccessInterface onlineDataAccessObject;
    public AddStockDataAccessObject(LocalDataAccessInterface localDataAccess,
                                    OnlineDataAccessInterface onlineDataAccess) {
        this.localDataAccessObject = localDataAccess;
        this.onlineDataAccessObject = onlineDataAccess;
    }
    @Override
    public Portfolio getCurrentPortfolio() {
        return localDataAccessObject.getCurrentPortfolio();
    }

    @Override
    public void writeCurrentPortfolio() {
        localDataAccessObject.writeCurrentPortfolio();
    }

    @Override
    public Map<String, String> getNameToSymbolMap() {
        return localDataAccessObject.getNameToSymbolMap();
    }

    @Override
    public Map<String, String> getSymbolToNameMap() {
        return localDataAccessObject.getSymbolToNameMap();
    }

    @Override
    public double queryPrice(String symbol) {
        List<Pair<String, Double>> timeSeries = onlineDataAccessObject
                .getSingleTimeSeriesIntraDay(symbol, 1, Config.INTRADAY_PREDICT_INTERVAL);
        return timeSeries.get(0).getSecond();
    }
}
