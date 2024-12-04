package usecases;

import java.util.List;
import java.util.Map;

import entities.Portfolio;
import kotlin.Pair;

/**
 * The data access object for fetching online data from API.
 */
public interface OnlineDataAccessInterface {
    /**
     * Get the time series data of the price of a single stock within a day.
     * @param symbol the symbol of the stock to be queried.
     * @param sampleSize the number of data points to get.
     * @param interval the time interval in minutes between each data point.
     *                 Only 1min, 5min, 30min, and 60min are available.
     * @return a list of pairs of (timestamp, stock price) of the stock in chronological order.
     */
    List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval);

    /**
     * Get the daily time series data of the price of a single stock.
     * @param symbol the symbol of the stock to be queried.
     * @param sampleSize the number of data points to get.
     * @return a list of pairs of (timestamp, stock price) of the stock in chronological order.
     */
    List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize);

    /**
     * Get the weekly time series data of the price of a single stock.
     * @param symbol the symbol of the stock to be queried.
     * @param sampleSize the number of datapoints to get.
     * @return a list of pairs of (timestamp, stock price) of the stock in chronological order.
     */
    List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize);

    /**
     * Bulk query the time series data of the prices of all stocks in the portfolio within a day.
     * @param portfolio the user's current portfolio.
     * @param sampleSize the number of datapoints to get for each stock.
     * @param interval the time interval between datapoints in minutes. Only 1min, 5min, 30min, and 60min are available.
     * @return a map from each symbol to the list of (timestamp, stock price) of the stock represented
     *      by the symbol in chronological order.
     */
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(Portfolio portfolio,
                                                                      int sampleSize,
                                                                      int interval);

    /**
     * Bulk query the daily time series data of the prices of all stocks.
     * @param portfolio the user's current portfolio.
     * @param sampleSize the number of datapoints to get for each stock.
     * @return a map from each symbol to the list of (timestamp, stock price) of the stock represented
     *      by the symbol in chronological order.
     */
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize);

    /**
     * Bulk query the weekly time series data of the prices of all stocks.
     * @param portfolio the user's current portfolio.
     * @param sampleSize the number of datapoints to get for each stock.
     * @return a map from each symbol to the list of (timestamp, stock price) of the stock represented
     *      by the symbol in chronological order.
     */
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize);
}
