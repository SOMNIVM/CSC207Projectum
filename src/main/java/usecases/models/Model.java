package usecases.models;

import entities.Portfolio;

public abstract class Model {
    private final int numOfInterval;
    private final double[] observations;
    private String type;

    protected Model(int numOfInterval, double[] observations,
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
    public static Model createModel(String type, int numOfInterval, double[] observations) {
         switch (type) {
            case "avgModel":
                return new AvgModel(numOfInterval, observations);
            case "linearRegressionModel":
                throw new IllegalArgumentException("Model not implemented yet: " + type);
            default:
                        throw new IllegalArgumentException("Invalid model type: " + type);
                }
    }
    
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
    public abstract double predict(Portfolio portfolio, int intervalLength, String intervalName);



    public abstract double getMeanSquaredError();
    public abstract double getMeanAbsoluteError();
    public abstract double getSharpeRatio();
    public abstract double getPredictedPrice();
}
