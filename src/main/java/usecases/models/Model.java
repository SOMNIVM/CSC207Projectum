package usecases.models;

import entities.Portfolio;

public interface Model {
    /**
     * Predicts the portfolio's value based on the given interval name and length.
     *
     * @param portfolio     The portfolio to predict for.
     * @param intervalLength The interval length (e.g., number of hours for "intraday").
     *                       Ignored for "day" and "week".
     * @param intervalName   The type of interval: "day", "week", or "intraday".
     * @return The predicted value of the portfolio after the specified interval.
     * @throws IllegalArgumentException If the intervalName is not "day", "week", or "intraday".
     */
    double predict(Portfolio portfolio, int intervalLength, String intervalName);


}
