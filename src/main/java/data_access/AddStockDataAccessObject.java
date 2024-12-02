
package data_access;

import java.util.List;
import java.util.Map;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.add_stock.AddStockDataAccessInterface;

/**
 * The AddStockDataAccessObject class implements the AddStockDataAccessInterface
 * and provides methods to interact with local and online data access objects.
 */
public class AddStockDataAccessObject implements AddStockDataAccessInterface {
    private final LocalDataAccessInterface localDataAccessObject;
    private final OnlineDataAccessInterface onlineDataAccessObject;

    /**
     * Constructs an AddStockDataAccessObject with the specified local and online data access objects.
     *
     * @param localDataAccess the local data access object
     * @param onlineDataAccess the online data access object
     */
    public AddStockDataAccessObject(LocalDataAccessInterface localDataAccess,
                                    OnlineDataAccessInterface onlineDataAccess) {
        this.localDataAccessObject = localDataAccess;
        this.onlineDataAccessObject = onlineDataAccess;
    }

    /**
     * Retrieves the current portfolio from the local data access object.
     *
     * @return the current portfolio
     */
    @Override
    public Portfolio getCurrentPortfolio() {
        return localDataAccessObject.getCurrentPortfolio();
    }

    /**
     * Writes the current portfolio using the local data access object.
     */
    @Override
    public void writeCurrentPortfolio() {
        localDataAccessObject.writeCurrentPortfolio();
    }

    /**
     * Retrieves a map of stock names to their symbols from the local data access object.
     *
     * @return a map of stock names to symbols
     */
    @Override
    public Map<String, String> getNameToSymbolMap() {
        return localDataAccessObject.getNameToSymbolMap();
    }

    /**
     * Retrieves a map of stock symbols to their names from the local data access object.
     *
     * @return a map of stock symbols to names
     */
    @Override
    public Map<String, String> getSymbolToNameMap() {
        return localDataAccessObject.getSymbolToNameMap();
    }

    /**
     * Queries the current price for the given stock symbol using the online data access object.
     *
     * @param symbol the stock symbol to query the price for
     * @return the current price of the specified stock symbol
     */
    @Override
    public double queryPrice(String symbol) {
        final List<Pair<String, Double>> timeSeries = onlineDataAccessObject
                .getSingleTimeSeriesIntraDay(symbol, 1, Config.INTRADAY_PREDICT_INTERVAL);
        return timeSeries.get(0).getSecond();
    }
}
