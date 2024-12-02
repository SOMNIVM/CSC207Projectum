package usecases.model_evaluation;

/**
 * A data class representing input parameters for model evaluation.
 * This class encapsulates the necessary parameters required to evaluate a prediction model.
 *
 * @param modelType  The type of the prediction model to be evaluated
 * @param frequency The frequency parameter for the model evaluation
 * @param length    The length parameter for the model evaluation
 */
public class ModelEvaluationInputData {
    private final String modelType;
    private final String frequency;
    private final int length;


    public ModelEvaluationInputData(String modelType, String frequency, int length) {
        this.modelType = modelType;
        this.length = length;
        this.frequency = frequency;

    }

    public String getModelType() {
        return modelType;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getLength() {
        return length;
    }
}
