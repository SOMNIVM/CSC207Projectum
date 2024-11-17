package usecases.models;

import entities.Portfolio;

public interface Model {
    /**
     * @param portfolio The portfolio
     * @param interval The interval at which datapoints are taken.
     * @return The predicted value of the portfolio after interval.
     */
    double intraDayPredict(Portfolio portfolio, int interval);

    /**
     *
     * @param portfolio The stock whose price needs to be created.
     * @return The predicted value of the portfolio on the next day.
     */
    double nextDayPredict(Portfolio portfolio);
}
