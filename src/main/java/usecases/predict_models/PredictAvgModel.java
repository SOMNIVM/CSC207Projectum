package usecases.predict_models;

import java.util.List;
import java.util.Map;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.OnlineDataAccessInterface;

/**
 * Implementation of the PredictModel interface using an average-based prediction approach.
 * This model calculates predictions and confidence intervals based on historical price data.
 * It uses statistical methods including sample mean, standard deviation, and t-distribution
 * for confidence interval calculations.
 */
public class PredictAvgModel implements PredictModel {
    private static final double CONFIDENCE_LEVEL = 0.95;
    // 95% confidence level
    private static final double TSCORE_BOUND = 1.96;
    private static final String INTRADAY_LABEL = "intraday";
    private static final String DAILY_LABEL = "day";
    private static final String WEEKLY_LABEL = "week";
    private OnlineDataAccessInterface onlineDataAccess;

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
    public double predictValue(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        // Get historical observations
        final double[] observations = getHistoricalObservations(portfolio, intervalName);

        // Calculate prediction (simple average in this implementation)
        return calculateAverage(observations);
    }

    @Override
    public double predictRevenue(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        // Get historical observations
        final double[] observations = getHistoricalObservations(portfolio, intervalName);
        double curValue = observations[getNumObservations(intervalName) - 1];
        if (!intervalName.equals(INTRADAY_LABEL)) {
            curValue = getCurValue(portfolio);
        }

        // Calculate prediction (simple average in this implementation)
        return calculateAverage(observations) - curValue;
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
    public double[] predictValueWithInterval(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        final double[] observations = getHistoricalObservations(portfolio, intervalName);
        final double mean = calculateAverage(observations);
        final double stdDev = calculateStandardDeviation(observations, mean);

        // Calculate confidence interval
        final double marginOfError = calculateMarginOfError(stdDev, observations.length);

        return new double[] {
            mean,
            mean - marginOfError,
            mean + marginOfError};
        // Point estimate, Lower bound, Upper bound
    }

    /**
     * Calculates predicted revenue with confidence intervals.
     * Uses historical data to compute point estimate and confidence bounds.
     *
     * @param portfolio the portfolio to analyze
     * @param intervalLength the prediction interval length
     * @param intervalName the type of interval
     * @return array containing [pointEstimate, lowerBound, upperBound]
     */
    public double[] predictRevenueWithInterval(Portfolio portfolio, int intervalLength, String intervalName) {
        validatePredictionInputs(portfolio, intervalLength);

        final double[] observations = getHistoricalObservations(portfolio, intervalName);
        double curValue = observations[getNumObservations(intervalName) - 1];
        if (!intervalName.equals(INTRADAY_LABEL)) {
            curValue = getCurValue(portfolio);
        }
        final double mean = calculateAverage(observations);
        final double stdDev = calculateStandardDeviation(observations, mean);

        // Calculate confidence interval
        final double marginOfError = calculateMarginOfError(stdDev, observations.length);

        return new double[] {
            mean - curValue,
            mean - marginOfError - curValue,
            mean + marginOfError - curValue};
        // Point estimate, Lower bound, Upper bound
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
        final int numObservations = getNumObservations(intervalName);
        final Map<String, List<Pair<String, Double>>> historicalData = getHistoricalData(
                portfolio,
                intervalName,
                numObservations);

        final double[] observations = new double[numObservations];
        for (int i = 0; i < numObservations; i++) {
            observations[i] = calculatePortfolioValue(portfolio, historicalData, i);
        }
        return observations;
    }

    /**
     * Determines the number of observations based on interval type.
     * @param intervalName the type of interval
     * @return number of observations to use
     * @throws IllegalArgumentException if the type input is not one of "intraday", "day", or "week".
     */
    private int getNumObservations(String intervalName) {
        return switch (intervalName.toLowerCase()) {
            case INTRADAY_LABEL -> Config.INTRADAY_SAMPLE_SIZE;
            case DAILY_LABEL -> Config.DAILY_SAMPLE_SIZE;
            case WEEKLY_LABEL -> Config.WEEKLY_SAMPLE_SIZE;
            default -> throw new IllegalArgumentException(
                    String.format(
                            "Invalid interval type. Use '%s', '%s', or '%s'.",
                            INTRADAY_LABEL,
                            DAILY_LABEL,
                            WEEKLY_LABEL));
        };
    }

    /**
     * Retrieves historical data for the portfolio.
     *
     * @param portfolio the portfolio to get data for
     * @param intervalName the type of interval
     * @param numObservations number of observations to retrieve
     * @return map of historical data by symbol
     * @throws IllegalArgumentException if the interval type is not one of "intraday", "day", or "week".
     */
    private Map<String, List<Pair<String, Double>>> getHistoricalData(Portfolio portfolio,
                                                                      String intervalName,
                                                                      int numObservations) {
        return switch (intervalName.toLowerCase()) {
            case INTRADAY_LABEL -> onlineDataAccess.getBulkTimeSeriesIntraDay(
                    portfolio, numObservations, Config.INTRADAY_PREDICT_INTERVAL);
            case DAILY_LABEL -> onlineDataAccess.getBulkTimeSeriesDaily(
                    portfolio, numObservations);
            case WEEKLY_LABEL -> onlineDataAccess.getBulkTimeSeriesWeekly(
                    portfolio, numObservations);
            default -> throw new IllegalArgumentException(
                    String.format(
                            "Invalid interval type. Use '%s', '%s', or '%s'.",
                            INTRADAY_LABEL,
                            DAILY_LABEL,
                            WEEKLY_LABEL));
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
            final List<Pair<String, Double>> priceData = historicalData.get(symbol);
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
            final double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        return Math.sqrt(sumSquaredDiff / (values.length - 1));
    }

    /**
     * Calculates the margin of error for confidence interval.
     *
     * @param stdDev standard deviation of the sample
     * @param sampleSize sample size
     * @return margin of error
     */
    private double calculateMarginOfError(double stdDev, int sampleSize) {
        // Using 1.96 for 95% confidence level (assumes large sample size)
        return TSCORE_BOUND * (stdDev / Math.sqrt(sampleSize));
    }

    private double getCurValue(Portfolio portfolio) {
        double curValue = 0;
        final Map<String, List<Pair<String, Double>>> timeSeries = onlineDataAccess.getBulkTimeSeriesIntraDay(
                portfolio,
                1,
                Config.INTRADAY_PREDICT_INTERVAL);
        for (String symbol: timeSeries.keySet()) {
            curValue += portfolio.getShares(symbol) * timeSeries.get(symbol).get(0).getSecond();
        }
        return curValue;
    }
}
