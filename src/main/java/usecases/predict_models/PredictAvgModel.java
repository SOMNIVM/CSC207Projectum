package usecases.predict_models;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.OnlineDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the PredictModel interface using an average-based prediction approach.
 * This model calculates predictions and confidence intervals based on historical price data.
 * It uses statistical methods including sample mean, standard deviation, and t-distribution
 * for confidence interval calculations.
 */
public class PredictAvgModel implements PredictModel {
    private OnlineDataAccessInterface onlineDataAccess;
    private static final double CONFIDENCE_LEVEL = 0.95; // 95% confidence level

    /**
     * Sets the online data access interface for retrieving market data.
     *
     * @param onlineDataAccess the interface for accessing market data
     * @throws IllegalArgumentException if onlineDataAccess is null
     */
    @Override
    public void setOnlineDataAccess(OnlineDataAccessInterface onlineDataAccess) {
        if (onlineDataAccess == null) {
            throw new IllegalArgumentException("OnlineDataAccessInterface cannot be null");
        }
        this.onlineDataAccess = onlineDataAccess;
    }

    /**
     * Predicts portfolio value and calculates confidence intervals based on historical data.
     * Uses time series data appropriate to the specified interval type and length.
     *
     * @param portfolio the portfolio to predict values for
     * @param intervalLength the length of the prediction interval
     * @param intervalName the type of interval ("intraday", "day", or "week")
     * @return predicted portfolio value
     * @throws IllegalArgumentException if parameters are invalid or if portfolio is empty
     * @throws IllegalStateException if onlineDataAccess is not set
     */
    @Override
    public double predict(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        // Get historical observations
        double[] observations = getHistoricalObservations(portfolio, intervalName);

        // Calculate prediction (simple average in this implementation)
        return calculateAverage(observations);
    }

    /**
     * Calculates prediction with confidence intervals.
     * Uses historical data to compute point estimate and confidence bounds.
     *
     * @param portfolio the portfolio to analyze
     * @param intervalLength the prediction interval length
     * @param intervalName the type of interval
     * @return array containing [pointEstimate, lowerBound, upperBound]
     */
    public double[] predictWithInterval(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        double[] observations = getHistoricalObservations(portfolio, intervalName);
        double mean = calculateAverage(observations);
        double stdDev = calculateStandardDeviation(observations, mean);

        // Calculate confidence interval
        double marginOfError = calculateMarginOfError(stdDev, observations.length);

        return new double[] {
                mean,                   // Point estimate
                mean - marginOfError,   // Lower bound
                mean + marginOfError    // Upper bound
        };
    }

    /**
     * Validates prediction input parameters.
     *
     * @param portfolio the portfolio to validate
     * @param intervalLength the interval length to validate
     * @throws IllegalStateException if onlineDataAccess is not set
     * @throws IllegalArgumentException if inputs are invalid
     */
    private void validatePredictionInputs(Portfolio portfolio, int intervalLength) {
        if (onlineDataAccess == null) {
            throw new IllegalStateException("OnlineDataAccessInterface must be set before making predictions");
        }
        if (portfolio == null || portfolio.getStockSymbols().isEmpty()) {
            throw new IllegalArgumentException("Portfolio cannot be null or empty");
        }
        if (intervalLength <= 0) {
            throw new IllegalArgumentException("Interval length must be positive");
        }
    }

    /**
     * Gets historical observations based on interval type.
     *
     * @param portfolio the portfolio to get data for
     * @param intervalName the type of interval
     * @return array of historical portfolio values
     */
    private double[] getHistoricalObservations(Portfolio portfolio, String intervalName) {
        int numObservations = getNumObservations(intervalName);
        Map<String, List<Pair<String, Double>>> historicalData = getHistoricalData(portfolio, intervalName, numObservations);

        double[] observations = new double[numObservations];
        for (int i = 0; i < numObservations; i++) {
            observations[i] = calculatePortfolioValue(portfolio, historicalData, i);
        }
        return observations;
    }

    /**
     * Determines the number of observations based on interval type.
     *
     * @param intervalName the type of interval
     * @return number of observations to use
     */
    private int getNumObservations(String intervalName) {
        return switch (intervalName.toLowerCase()) {
            case "intraday" -> Config.INTRADAY_SAMPLE_SIZE;
            case "day" -> Config.DAILY_SAMPLE_SIZE;
            case "week" -> Config.WEEKLY_SAMPLE_SIZE;
            default -> throw new IllegalArgumentException(
                    "Invalid interval type. Use 'intraday', 'day', or 'week'.");
        };
    }

    /**
     * Retrieves historical data for the portfolio.
     *
     * @param portfolio the portfolio to get data for
     * @param intervalName the type of interval
     * @param numObservations number of observations to retrieve
     * @return map of historical data by symbol
     */
    private Map<String, List<Pair<String, Double>>> getHistoricalData(Portfolio portfolio,
                                                                      String intervalName,
                                                                      int numObservations) {
        return switch (intervalName.toLowerCase()) {
            case "intraday" -> onlineDataAccess.getBulkTimeSeriesIntraDay(
                    portfolio, numObservations, Config.INTRADAY_PREDICT_INTERVAL);
            case "day" -> onlineDataAccess.getBulkTimeSeriesDaily(
                    portfolio, numObservations);
            case "week" -> onlineDataAccess.getBulkTimeSeriesWeekly(
                    portfolio, numObservations);
            default -> throw new IllegalArgumentException(
                    "Invalid interval type. Use 'intraday', 'day', or 'week'.");
        };
    }

    /**
     * Calculates portfolio value at a specific time index.
     *
     * @param portfolio the portfolio to calculate value for
     * @param historicalData the historical price data
     * @param timeIndex the time index to calculate at
     * @return portfolio value at the specified time
     */
    private double calculatePortfolioValue(Portfolio portfolio,
                                           Map<String, List<Pair<String, Double>>> historicalData,
                                           int timeIndex) {
        double portfolioValue = 0.0;
        for (String symbol : portfolio.getStockSymbols()) {
            List<Pair<String, Double>> priceData = historicalData.get(symbol);
            if (priceData != null && timeIndex < priceData.size()) {
                portfolioValue += priceData.get(timeIndex).getSecond() * portfolio.getShares(symbol);
            }
        }
        return portfolioValue;
    }

    /**
     * Calculates the average of an array of values.
     *
     * @param values array of values to average
     * @return the arithmetic mean
     */
    private double calculateAverage(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    /**
     * Calculates the standard deviation of an array of values.
     *
     * @param values array of values
     * @param mean the mean of the values
     * @return the standard deviation
     */
    private double calculateStandardDeviation(double[] values, double mean) {
        double sumSquaredDiff = 0.0;
        for (double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        return Math.sqrt(sumSquaredDiff / (values.length - 1));
    }

    /**
     * Calculates the margin of error for confidence interval.
     *
     * @param stdDev standard deviation of the sample
     * @param n sample size
     * @return margin of error
     */
    private double calculateMarginOfError(double stdDev, int n) {
        // Using 1.96 for 95% confidence level (assumes large sample size)
        double tValue = 1.96;
        return tValue * (stdDev / Math.sqrt(n));
    }
}