package interface_adapters.model_evaluation;

import app.Config;
import usecases.model_evaluation.ModelEvaluationInputBoundary;
import usecases.model_evaluation.ModelEvaluationInputData;

/**
 * The controller for the model evaluation use case.
 */
public class ModelEvaluationController {
    private final ModelEvaluationInputBoundary modelEvaluationUseCaseInteractor;

    public ModelEvaluationController(ModelEvaluationInputBoundary interactor) {
        this.modelEvaluationUseCaseInteractor = interactor;
    }

    /**
     * Execute the model evaluation action.
     * @param modelType The type of the model to use.
     * @param frequency The frequency at which data are sampled over time.
     * @throws IllegalArgumentException if frequencey is not a valid string input.
     */
    public void execute(String modelType, String frequency) {
        final int length;
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

    /**
     * Switch back to the homepage.
     */
    public void switchBack() {
        modelEvaluationUseCaseInteractor.switchBack();
    }
}
