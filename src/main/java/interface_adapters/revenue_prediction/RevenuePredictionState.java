package interface_adapters.revenue_prediction;

/**
 * State class for revenue prediction view model.
 * Maintains the state information needed to display revenue predictions including
 * selected model, prediction results, interval information, and validation status.
 */
public class RevenuePredictionState {
    private String selectedModel;
    private double predictedRevenue;
    private String predictionInterval;
    private int intervalLength;
    private boolean isValidInput;
    private String errorMessage;

    /**
     * Constructs a new RevenuePredictionState with default values.
     * Initializes all fields to their default states.
     */
    public RevenuePredictionState() {
        this.selectedModel = "";
        this.predictedRevenue = 0.0;
        this.predictionInterval = "";
        this.intervalLength = 0;
        this.isValidInput = true;
        this.errorMessage = "";
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
}