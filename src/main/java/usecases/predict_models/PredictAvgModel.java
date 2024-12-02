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
 * Implementation of PredictModel that uses AvgModel for predictions
 */
public class PredictAvgModel implements PredictModel {
    private OnlineDataAccessInterface onlineDataAccess;

    @Override
    public void setOnlineDataAccess(OnlineDataAccessInterface onlineDataAccess) {
        if (onlineDataAccess == null) {
            throw new IllegalArgumentException("OnlineDataAccessInterface cannot be null");
        }
        this.onlineDataAccess = onlineDataAccess;
    }

    @Override
    public double predict(Portfolio portfolio, int intervalLength, String intervalName) {
        if (onlineDataAccess == null) {
            throw new IllegalStateException("OnlineDataAccessInterface must be set before making predictions");
        }
        if (portfolio == null || portfolio.getStockSymbols().isEmpty()) {
            throw new IllegalArgumentException("Portfolio cannot be null or empty");
        }
        if (intervalLength <= 0) {
            throw new IllegalArgumentException("Interval length must be positive");
        }

        // Get number of observations based on interval type
        int numObservations = switch (intervalName.toLowerCase()) {
            case "intraday" -> Config.INTRADAY_SAMPLE_SIZE;
            case "day" -> Config.DAILY_SAMPLE_SIZE;
            case "week" -> Config.WEEKLY_SAMPLE_SIZE;
            default -> throw new IllegalArgumentException(
                    "Invalid interval type. Use 'intraday', 'day', or 'week'.");
        };

        // Get historical observations
        double[] observations = getHistoricalObservations(portfolio, intervalName, numObservations);

        // Create AvgModel with observations
        AvgModel avgModel = new AvgModel(numObservations, observations);

        // Use AvgModel's built-in prediction
        return avgModel.getPredictedPrice();
    }

    private double[] getHistoricalObservations(Portfolio portfolio, String intervalName, int numObservations) {
        Map<String, List<Pair<String, Double>>> historicalData;

        historicalData = switch (intervalName.toLowerCase()) {
            case "intraday" -> onlineDataAccess.getBulkTimeSeriesIntraDay(
                    portfolio, numObservations, Config.INTRADAY_PREDICT_INTERVAL);
            case "day" -> onlineDataAccess.getBulkTimeSeriesDaily(
                    portfolio, numObservations);
            case "week" -> onlineDataAccess.getBulkTimeSeriesWeekly(
                    portfolio, numObservations);
            default -> throw new IllegalArgumentException(
                    "Invalid interval type. Use 'intraday', 'day', or 'week'.");
        };

        double[] observations = new double[numObservations];

        for (int i = 0; i < numObservations; i++) {
            double portfolioValue = 0.0;
            for (String symbol : portfolio.getStockSymbols()) {
                List<Pair<String, Double>> priceData = historicalData.get(symbol);
                if (priceData != null && i < priceData.size()) {
                    portfolioValue += priceData.get(i).getSecond() * portfolio.getShares(symbol);
                }
            }
            observations[i] = portfolioValue;
        }

        return observations;
    }
}