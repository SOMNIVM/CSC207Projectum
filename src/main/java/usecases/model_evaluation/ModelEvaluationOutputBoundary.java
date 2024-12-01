package usecases.model_evaluation;

public interface ModelEvaluationOutputBoundary {
    void prepareSuccessView(ModelEvaluationOutputData modelEvaluationOutputData);
    void prepareFailView(String errorDescription);

}
