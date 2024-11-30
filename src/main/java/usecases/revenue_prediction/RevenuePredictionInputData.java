package usecases.revenue_prediction;

/**
 * Data class that encapsulates the input parameters for revenue prediction.
 * Contains the length of the prediction interval and the name/type of interval being used.
 */
public class RevenuePredictionInputData {
    private final int intervalLength;
    private final String intervalName;

    /**
     * Constructs a RevenuePredictionInputData with specified interval parameters.
     *
     * @param intervalLength The length of the prediction interval (e.g., 5.0 for 5 days)
     * @param intervalName The type/name of the interval (e.g., "days", "months")
     */
    public RevenuePredictionInputData(int intervalLength, String intervalName) {
        this.intervalLength = intervalLength;
        this.intervalName = intervalName;
    }

    /**
     * Gets the length of the prediction interval.
     *
     * @return the interval length as a Double
     */
    public int getIntervalLength() {
        return intervalLength;
    }

    /**
     * Gets the name/type of the prediction interval.
     *
     * @return the interval name as a String
     */
    public String getIntervalName() {
        return intervalName;
    }
}