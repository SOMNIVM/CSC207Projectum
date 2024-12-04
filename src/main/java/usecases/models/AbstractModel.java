package usecases.models;

/**
 * A superclass of all models.
 */
public abstract class AbstractModel {
    private final int numOfInterval;
    private final double[] observations;
    private final String type;

    protected AbstractModel(int numOfInterval, double[] observations,
                            String type) {
        if (numOfInterval <= 0) {
            throw new IllegalArgumentException("Number of intervals must be greater than 0.");
        }
        if (observations == null || observations.length < numOfInterval) {
            throw new IllegalArgumentException("Observations array is null or does not match the number of intervals.");
        }
        this.numOfInterval = numOfInterval;
        this.observations = observations;
        this.type = type;

    }

    /**
     * Factory method for a new model.
     * @param type the type of model to create.
     * @param numOfInterval the number of intervals to include in evaluation.
     * @param observations the observed portfolio values over time.
     * @return A concrete model with the given type, number of interval, and observations.
     * @throws IllegalArgumentException if any exception occurred.
     */
    public static AbstractModel createModel(String type, int numOfInterval, double[] observations) {
        // "Average model, Moving average model(Not implemented yet), ML model(Not implemented yet)"
        if ("average model".equalsIgnoreCase(type)) {
            return new AvgModel(numOfInterval, observations);
        }
        throw new IllegalArgumentException("Invalid model type: " + type);
    }

    /**
     * Get the mean squared error (MSE) of the prediction on historical data.
     * @return MSE
     */
    public abstract double getMeanSquaredError();

    /**
     * Get the mean absolute error (MAE) of the prediction on historical data.
     * @return MAE
     */
    public abstract double getMeanAbsoluteError();

    /**
     * Get the predicted Sharpe ratio on historical data.
     * @return the predicted Sharpe ratio.
     */
    public abstract double getSharpeRatio();

    /**
     * Get the predicted price of the portfolio given the observations of the total price of portfolio.
     * @return the predicted price.
     */
    public abstract double getPredictedPrice();

    /**
     * Get the actual total price of the portfolio.
     * @return the actual total price.
     */
    public abstract double getActualPrice();

    /**
     * Get the variance of the observations.
     * @return the variance of the observations.
     */
    public abstract double getVariance();

    /**
     * Get the standard deviation of the observations.
     * @return the standard deviation of the observations.
     */
    public abstract double getStandardDeviation();

    /**
     * Get a copy of the observations.
     * @return a copy of the observations array.
     */
    public double[] getObservations() {
        return observations.clone();
    }

    public int getNumOfInterval() {
        return numOfInterval;
    }

    public String getType() {
        return type;
    }
}
