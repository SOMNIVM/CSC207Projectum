package usecases.model_evaluation;

/**
 * Output boundary interface for model evaluation operations.
 * This interface defines methods to handle both successful and failed model evaluation results.
 */
public interface ModelEvaluationOutputBoundary {
    /**
     * Prepare the view for a successful evaluation.
     * @param modelEvaluationOutputData the output data of the evaluation.
     */
    void prepareSuccessView(ModelEvaluationOutputData modelEvaluationOutputData);

    /**
     * Prepare the view for a failed evaluation.
     * @param errorDescription the description of the error that caused the failure.
     */
    void prepareFailView(String errorDescription);

    /**
     * Switch to the viewing result view.
     */
    void switchToModelResult();

    /**
     * Switch back to the homepage.
     */
    void switchBack();
}
