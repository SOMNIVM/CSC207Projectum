package usecases.model_evaluation;

public class ModelEvaluationOutputData {
    private final String modelName;
    private final String frequency;
    private final int length;
    private final double meanSquaredError;
    private final double meanAbsoluteError;
    private final double sharpRatio;
    private final double predictedPrice;
    private final double actualPrice;

    public ModelEvaluationOutputData(String modelName, String frequency, int length, double meanSquaredError, double meanAbsoluteError, double sharpRatio, double predictedPrice, double actualPrice) {
        this.modelName = modelName;
        this.frequency = frequency;
        this.length = length;
        this.meanSquaredError = meanSquaredError;
        this.meanAbsoluteError = meanAbsoluteError;
        this.sharpRatio = sharpRatio;
        this.predictedPrice = predictedPrice;
        this.actualPrice = actualPrice;
    }

    public double getMeanSquaredError() {
        return meanSquaredError;
    }

    public double getMeanAbsoluteError() {
        return meanAbsoluteError;
    }

    public double getSharpRatio() {
        return sharpRatio;
    }

    public double getPredictedPrice() {
        return predictedPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }
    public String getModelName() {
        return modelName;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getLength() {
        return length;
    }



}
