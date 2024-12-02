package interface_adapters.model_evaluation;

public class ModelEvaluationState {
    private String modelName;
    private String frequency;
    private int length;
    private double meanSquaredError;
    private double meanAbsoluteError;
    private double sharpeRatio;
    private double predictedPrice;
    private double actualPrice;
    private boolean isValid = true;
    private String errorMessage;

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setMeanSquaredError(double meanSquaredError) {
        this.meanSquaredError = meanSquaredError;
    }

    public void setMeanAbsoluteError(double meanAbsoluteError) {
        this.meanAbsoluteError = meanAbsoluteError;
    }

    public void setSharpeRatio(double sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public void setPredictedPrice(double predictedPrice) {
        this.predictedPrice = predictedPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public void setAsInvalid(String error) {
        this.isValid = false;
        this.errorMessage = error;
    }

    public void setAsValid() {
        this.isValid = true;
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

    public double getMeanSquaredError() {
        return meanSquaredError;
    }

    public double getMeanAbsoluteError() {
        return meanAbsoluteError;
    }

    public double getSharpeRatio() {
        return sharpeRatio;
    }

    public double getPredictedPrice() {
        return predictedPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getError() {
        return errorMessage;
    }
    public void reset() {
        this.modelName = null;
        this.frequency = null;
        this.length = 0;
        this.meanSquaredError = 0.0;
        this.meanAbsoluteError = 0.0;
        this.sharpeRatio = 0.0;
        this.predictedPrice = 0.0;
        this.actualPrice = 0.0;
        this.isValid = true;
        this.errorMessage ="";
    }

}

    
