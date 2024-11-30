package usecases.ModelEvaluation;

public class ModelEvaluationOutputData {
    private final double meanSquaredError;
    private final double meanAbsoluteError;
    private final double sharpRatio;
    private final double predictedPrice;
    private final double actualPrice;

    public ModelEvaluationOutputData(double meanSquaredError, double meanAbsoluteError, double sharpRatio, double predictedValue
    , double actualPrice) {
        this.meanSquaredError = meanSquaredError;
        this.meanAbsoluteError = meanAbsoluteError;
        this.sharpRatio = sharpRatio;
        this.predictedPrice = predictedValue;
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

}
