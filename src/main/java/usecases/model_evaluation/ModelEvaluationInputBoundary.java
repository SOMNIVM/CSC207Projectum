package usecases.model_evaluation;

/**
 * Interface for evaluating ML models. Although we only have naive model right now.
 * This interface defines the contract for classes that handle model evaluation operations.
 * 
 * @since 1.0
 */
public interface ModelEvaluationInputBoundary {

    /**
     * Execute the model evaluation use case.
     * @param modelEvaluationInputData The input data for the model evaluation use case.
     */
    void execute(ModelEvaluationInputData modelEvaluationInputData);

    /**
     * Switch back to the homepage.
     */
    void switchBack();
}
