package usecases.revenue_prediction;

/**
 * Data class containing the results of a revenue prediction calculation including confidence intervals.
 * This class encapsulates all output data related to revenue predictions, including point estimates,
 * confidence intervals, and interval metadata.
 */
public class RevenuePredictionOutputData {
    private final double predictedRevenue;
    private final double lowerBound;
    private final double upperBound;
    private final int intervalLength;
    private final String intervalName;
    private final double confidenceLevel;

    /**
     * Constructs a RevenuePredictionOutputData with prediction results and confidence interval information.
     *
     * @param predictedRevenue the point estimate for predicted revenue
     * @param lowerBound the lower bound of the confidence interval
     * @param upperBound the upper bound of the confidence interval
     * @param intervalLength the length of the prediction interval
     * @param intervalName the type of interval used (e.g., "day", "week", "intraday")
     * @param confidenceLevel the confidence level (e.g., 0.95 for 95% confidence)
     */
    public RevenuePredictionOutputData(double predictedRevenue, double lowerBound, double upperBound,
                                       int intervalLength, String intervalName, double confidenceLevel) {
        this.predictedRevenue = predictedRevenue;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.intervalLength = intervalLength;
        this.intervalName = intervalName;
        this.confidenceLevel = confidenceLevel;
    }

    /**
     * Gets the point estimate of predicted revenue.
     *
     * @return the predicted revenue value
     */
    public double getPredictedRevenue() {
        return predictedRevenue;
    }

    /**
     * Gets the lower bound of the confidence interval.
     *
     * @return the lower bound value
     */
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     * Gets the upper bound of the confidence interval.
     *
     * @return the upper bound value
     */
    public double getUpperBound() {
        return upperBound;
    }

    /**
     * Gets the length of the prediction interval.
     *
     * @return the interval length
     */
    public int getIntervalLength() {
        return intervalLength;
    }

    /**
     * Gets the name/type of the prediction interval.
     *
     * @return the interval name
     */
    public String getIntervalName() {
        return intervalName;
    }

    /**
     * Gets the confidence level of the prediction interval.
     *
     * @return the confidence level as a decimal (e.g., 0.95 for 95%)
     */
    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * Creates a formatted message describing the prediction results.
     * Includes the point estimate and confidence interval information.
     *
     * @return a formatted string containing prediction details
     */
    public String getFormattedMessage() {
        return String.format("Predicted revenue after %d %s(s):%n" +
                        "Point estimate: $%.2f%n" +
                        "%.0f%% Confidence Interval: [$%.2f, $%.2f]",
                intervalLength,
                intervalName,
                predictedRevenue,
                confidenceLevel * 100,
                lowerBound,
                upperBound);
    }
}