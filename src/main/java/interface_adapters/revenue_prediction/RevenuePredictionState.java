package interface_adapters.revenue_prediction;

/**
 * State class for the revenue prediction view model.
 * Maintains the state information needed to display revenue predictions including
 * predicted values, confidence intervals, and validation status.
 */
public class RevenuePredictionState {
    private static final double DEFAULT_CONFIDENCE_LEVEL = 0.95;
    private String selectedModel;
    private double predictedRevenue;
    private double lowerBound;
    private double upperBound;
    private double confidenceLevel;
    private String predictionInterval;
    private int intervalLength;
    private boolean isValidInput;
    private String errorMessage;

    /**
     * Constructs a new RevenuePredictionState with default values.
     * Initializes all numeric fields to zero and sets default confidence level to 95%.
     */
    public RevenuePredictionState() {
        this.selectedModel = "";
        this.predictedRevenue = 0.0;
        this.lowerBound = 0.0;
        this.upperBound = 0.0;
        this.confidenceLevel = DEFAULT_CONFIDENCE_LEVEL;
        // Default 95% confidence level
        this.predictionInterval = "";
        this.intervalLength = 0;
        this.isValidInput = true;
        this.errorMessage = "";
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
     * Sets the lower bound of the confidence interval.
     *
     * @param lowerBound the new lower bound value
     */
    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
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
     * Sets the upper bound of the confidence interval.
     *
     * @param upperBound the new upper bound value
     */
    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
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
     * Sets the confidence level for the prediction interval.
     *
     * @param confidenceLevel the new confidence level (e.g., 0.95 for 95%)
     */
    public void setConfidenceLevel(double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    /**
     * Gets the currently selected prediction model.
     *
     * @return The name of the selected model
     */
    public String getSelectedModel() {
        return selectedModel;
    }

    /**
     * Sets the prediction model to be used.
     *
     * @param model The name of the model to use
     */
    public void setSelectedModel(String model) {
        this.selectedModel = model;
    }

    /**
     * Gets the predicted revenue value.
     *
     * @return The predicted revenue
     */
    public double getPredictedRevenue() {
        return predictedRevenue;
    }

    /**
     * Sets the predicted revenue value.
     *
     * @param revenue The predicted revenue value
     */
    public void setPredictedRevenue(double revenue) {
        this.predictedRevenue = revenue;
    }

    /**
     * Gets the prediction interval type.
     *
     * @return The type of interval used for prediction
     */
    public String getPredictionInterval() {
        return predictionInterval;
    }

    /**
     * Sets the prediction interval type.
     *
     * @param interval The type of interval to use
     */
    public void setPredictionInterval(String interval) {
        this.predictionInterval = interval;
    }

    /**
     * Gets the length of the prediction interval.
     *
     * @return The interval length
     */
    public int getIntervalLength() {
        return intervalLength;
    }

    /**
     * Sets the length of the prediction interval.
     *
     * @param length The length of the interval
     */
    public void setIntervalLength(int length) {
        this.intervalLength = length;
    }

    /**
     * Marks the state as valid (no errors).
     */
    public void setAsValid() {
        this.isValidInput = true;
        this.errorMessage = "";
    }

    /**
     * Marks the state as invalid with an error message.
     *
     * @param error Description of what went wrong
     */
    public void setAsInvalid(String error) {
        this.isValidInput = false;
        this.errorMessage = error;
    }

    /**
     * Checks if the current state is valid.
     *
     * @return true if the state is valid, false otherwise
     */
    public boolean checkIfValid() {
        return isValidInput;
    }

    /**
     * Gets the error message if the state is invalid.
     *
     * @return The error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    private void reset() {
        this.selectedModel = "";
        this.predictedRevenue = 0.0;
        this.lowerBound = 0.0;
        this.upperBound = 0.0;
        this.confidenceLevel = DEFAULT_CONFIDENCE_LEVEL;
        // Default 95% confidence level
        this.predictionInterval = "";
        this.intervalLength = 0;
        this.isValidInput = true;
        this.errorMessage = "";
        System.out.println("State reset");
    }
}
