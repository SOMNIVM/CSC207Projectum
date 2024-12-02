package usecases.revenue_prediction;

/**
 * Output boundary for the revenue prediction use case.
 * Defines methods for handling successful and failed prediction results.
 */
public interface RevenuePredictionOutputBoundary {
    /**
     * Prepares the success view with the prediction results.
     *
     * @param outputData contains the prediction results and interval information
     */
    void prepareSuccessView(RevenuePredictionOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param error description of what went wrong
     */
    void prepareFailView(String error);

    void switchBack();
}