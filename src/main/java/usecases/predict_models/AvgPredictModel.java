package usecases.predict_models;

import app.Config;
import entities.Portfolio;
import usecases.OnlineDataAccessInterface;
import usecases.models.AvgModel;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the PredictModel interface that uses AvgModel to make predictions
 * based on historical portfolio values. This model leverages existing average calculation
 * logic to predict future portfolio values.
 */
public class PredictAvgModel implements PredictModel {
    private final OnlineDataAccessInterface onlineDataAccess;

    /**
     * Constructs a new PredictAvgPredictModel with the specified data access interface.
     *
     * @param onlineDataAccess The interface used to fetch historical price data
     * @throws IllegalArgumentException if onlineDataAccess is null
     */
    public PredictAvgPredictModel(OnlineDataAccessInterface onlineDataAccess) {
        if (onlineDataAccess == null) {
            throw new IllegalArgumentException("OnlineDataAccessInterface cannot be null");
        }
        this.onlineDataAccess = onlineDataAccess;
    }

    /**
     * Predicts the future value of a portfolio using the AvgModel for calculations.
     * The prediction uses historical portfolio values to estimate future value.
     *
     * @param portfolio The portfolio to predict values for
     * @param intervalLength The number of intervals to predict forward
     * @param intervalName The type of interval ("intraday", "day", or "week")
     * @return The predicted total value of the portfolio
     * @throws IllegalArgumentException if:
     *         - portfolio is null or empty
     *         - intervalLength is non-positive
     *         - intervalName is not one of: "intraday", "day", "week"
     */
    @Override
    public double predict(Portfolio portfolio, int intervalLength, String intervalName) {
        if (portfolio == null || portfolio.getStockSymbols().isEmpty()) {
            throw new IllegalArgumentException("Portfolio cannot be null or empty");
        }
        if (intervalLength <= 0) {
            throw new IllegalArgumentException("Interval length must be positive");
        }

        // Get historical data based on interval type
        Map<String, List<Pair<String, Double>>> historicalData = getHistoricalData(portfolio, intervalName);

        // Calculate historical portfolio values
        double[] historicalValues = calculateHistoricalPortfolioValues(portfolio, historicalData);

        // Create AvgModel with historical values
        AvgModel avgModel = new AvgModel(historicalValues.length, historicalValues);

        // Use AvgModel to predict future value
        double predictedBaseValue = avgModel.getPredictedPrice();

        // Calculate growth rate based on historical data
        double growthRate = calculateGrowthRate(historicalValues);

        // Apply growth rate over the interval length
        return predictedBaseValue * Math.pow(1 + growthRate, intervalLength);
    }

    /**
     * Retrieves appropriate historical data based on the interval type.
     */
    private Map<String, List<Pair<String, Double>>> getHistoricalData(Portfolio portfolio, String intervalName) {
        List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());

        return switch (intervalName.toLowerCase()) {
            case "intraday" -> onlineDataAccess.getBulkTimeSeriesIntraDay(
                    symbols, Config.INTRADAY_SAMPLE_SIZE, Config.INTRADAY_PREDICT_INTERVAL);
            case "day" -> onlineDataAccess.getBulkTimeSeriesDaily(
                    symbols, Config.DAILY_SAMPLE_SIZE);
            case "week" -> onlineDataAccess.getBulkTimeSeriesWeekly(
                    symbols, Config.WEEKLY_SAMPLE_SIZE);
            default -> throw new IllegalArgumentException(
                    "Invalid interval type. Use 'intraday', 'day', or 'week'.");
        };
    }

    /**
     * Calculates historical portfolio values from price data.
     */
    private double[] calculateHistoricalPortfolioValues(
            Portfolio portfolio,
            Map<String, List<Pair<String, Double>>> historicalData) {

        int maxLength = historicalData.values().stream()
                .mapToInt(List::size)
                .min()
                .orElse(0);

        double[] portfolioValues = new double[maxLength];

        for (int i = 0; i < maxLength; i++) {
            double totalValue = 0.0;
            for (String symbol : portfolio.getStockSymbols()) {
                List<Pair<String, Double>> stockData = historicalData.get(symbol);
                if (stockData != null && i < stockData.size()) {
                    totalValue += stockData.get(i).getSecond() * portfolio.getShares(symbol);
                }
            }
            portfolioValues[i] = totalValue;
        }

        return portfolioValues;
    }

    /**
     * Calculates the average growth rate from historical values.
     */
    private double calculateGrowthRate(double[] historicalValues) {
        if (historicalValues.length < 2) {
            return 0.0;
        }

        double totalGrowthRate = 0.0;
        int validPeriods = 0;

        for (int i = 1; i < historicalValues.length; i++) {
            if (historicalValues[i - 1] > 0) {
                totalGrowthRate += (historicalValues[i] - historicalValues[i - 1]) / historicalValues[i - 1];
                validPeriods++;
            }
        }

        return validPeriods > 0 ? totalGrowthRate / validPeriods : 0.0;
    }
}
