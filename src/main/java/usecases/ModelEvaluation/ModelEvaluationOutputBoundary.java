package usecases.ModelEvaluation;

/**
 * Output boundary interface for model evaluation operations.
 * This interface defines methods to handle both successful and failed model evaluation results.
 */
public interface ModelEvaluationOutputBoundary {
    void prepareSuccessView(ModelEvaluationOutputData modelEvaluationOutputData);
    void prepareFailView(String errorDescription);
    void switchToModelResult();
    void switchBack();
}
