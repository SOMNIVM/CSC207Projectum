package usecases.revenue_prediction;

/**
 * Data class containing the results of a revenue prediction calculation.
 */
public class RevenuePredictionOutputData {
    private final double predictedRevenue;
    private final int intervalLength;
    private final String intervalName;

    /**
     * Constructs a RevenuePredictionOutputData with the prediction results.
     *
     * @param predictedRevenue the predicted revenue formatted as a string with 2 decimal places
     * @param intervalLength the length of the prediction interval
     * @param intervalName the type of interval used (day, week, or intraday)
     */
    public RevenuePredictionOutputData(double predictedRevenue, int intervalLength, String intervalName) {
        this.predictedRevenue = predictedRevenue;
        this.intervalLength = intervalLength;
        this.intervalName = intervalName;
    }

    /**
     * Gets the predicted revenue value.
     *
     * @return the predicted revenue as a formatted string
     */
    public double getPredictedRevenue() {
        return predictedRevenue;
    }
    /**
     * Gets the interval length used for the prediction.
     *
     * @return the interval length
     */
    public int getIntervalLength() {
        return intervalLength;
    }
    /**
     * Gets the type of interval used for the prediction.
     *
     * @return the interval name
     */
    public String getIntervalName() {
        return intervalName;
    }

    /**
     * Creates a formatted message describing the prediction result.
     *
     * @return a user-friendly message with the prediction details
     */
    public String getFormattedMessage() {
        return String.format("Predicted revenue after %d %s(s): $%s",
                intervalLength,
                intervalName,
                predictedRevenue);
    }
}
