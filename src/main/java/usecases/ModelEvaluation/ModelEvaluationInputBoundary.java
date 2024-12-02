package usecases.ModelEvaluation;

/**
 * Interface for evaluating ML models. Although we only have naive model right now.
 * This interface defines the contract for classes that handle model evaluation operations.
 * 
 * @since 1.0
 */
public interface ModelEvaluationInputBoundary {
    void execute(ModelEvaluationInputData modelEvaluationInputData);

    void switchBack();
}
