package usecases.predict_models;

import entities.Portfolio;
import usecases.OnlineDataAccessInterface;

/**
 * Interface for prediction models that forecast portfolio values.
 */
public interface PredictModel {
    /**
     * Sets the online data access interface for the model.
     *
     * @param onlineDataAccess The interface to use for market data.
     */
    void setOnlineDataAccess(OnlineDataAccessInterface onlineDataAccess);

    /**
     * Predicts the portfolio's value based on the given interval parameters.
     *
     * @param portfolio The portfolio to predict for
     * @param intervalLength The interval length (e.g., number of hours for "intraday")
     * @param intervalName The type of interval: "day", "week", or "intraday"
     * @return The predicted value of the portfolio
     */
    double predictValue(Portfolio portfolio, int intervalLength, String intervalName);

    /**
     * Predict the portfolio's revenue based on the given interval parameters.
     * @param portfolio The portfolio to predict for
     * @param intervaLength The interval length (e.g., number of hours for "intraday")
     * @param intervalName The type of interval: "day", "week", or "intraday"
     * @return The predicted value of the portfolio
     */
    double predictRevenue(Portfolio portfolio, int intervaLength, String intervalName);
}
