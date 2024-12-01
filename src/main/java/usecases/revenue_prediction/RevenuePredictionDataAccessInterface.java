package usecases.revenue_prediction;

import usecases.LocalDataAccessInterface;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedMap;

/**
 * Interface for accessing data required for revenue prediction.
 * Extends LocalDataAccessInterface to include access to portfolio data
 * and adds methods specific to revenue prediction functionality.
 */
public interface RevenuePredictionDataAccessInterface extends LocalDataAccessInterface {

    /**
     * Query historical price data for a given stock symbol and interval.
     *
     * @param symbol The stock symbol to query
     * @param intervalLength The length of the interval (e.g., number of hours for intraday)
     * @param intervalName The type of interval ("day", "week", or "intraday")
     * @return The map between stock symbols and a sorted map whose keys are the historical time and values are the
     * corresponding prices.
     * @throws IllegalArgumentException if intervalName is not "day", "week", or "intraday"
     */
    Map<String, SortedMap<LocalDateTime, Double>> getHistoricalPrices(String symbol, int intervalLength, String intervalName);

    /**
     * Get the current market price for a stock.
     *
     * @param symbol The stock symbol to query
     * @return The current market price of the stock
     */
    double getCurrentPrice(String symbol);
}