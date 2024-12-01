package interface_adapters.ModelEvaluation;

import usecases.ModelEvaluation.ModelEvaluationInputBoundary;
import usecases.ModelEvaluation.ModelEvaluationInputData;
import app.Config;

public class ModelEvaluationController {
    private final ModelEvaluationInputBoundary modelEvaluationUseCaseInteractor;
    public ModelEvaluationController(ModelEvaluationInputBoundary interactor) {
        this.modelEvaluationUseCaseInteractor = interactor;
    }
    public void execute(String modelType, String frequency) {
        int length;
        switch (frequency) {
            case "Daily":
                length = Config.DAILY_SAMPLE_SIZE;
                modelEvaluationUseCaseInteractor.execute(new ModelEvaluationInputData(modelType, frequency, length));
                break;
            case "Weekly":
                length = Config.WEEKLY_SAMPLE_SIZE;
                modelEvaluationUseCaseInteractor.execute(new ModelEvaluationInputData(modelType, frequency, length));
                break;
            case "Intraday":
                length = Config.INTRADAY_SAMPLE_SIZE;
                modelEvaluationUseCaseInteractor.execute(new ModelEvaluationInputData(modelType, frequency, length));
                break;
            default:
                throw new IllegalArgumentException("Invalid frequency");
        }
}
}
