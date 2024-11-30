package usecases.ModelEvaluation;

public interface ModelEvaluationOutputBoundary {
    void prepareSuccessView(ModelEvaluationOutputData modelEvaluationOutputData);
    void prepareFailView(String errorDescription);

}
