package interface_adapters.ModelEvaluation;

public class ModelResultState {
    private String modelName;
    private String frequency;
    private int length;
    private double meanSquaredError;
    private double meanAbsoluteError;
    private double sharpRatio;
    private double predictedPrice;
    private double actualPrice;
    private boolean isValid;
    private String error;

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

    public void setSharpRatio(double sharpRatio) {
        this.sharpRatio = sharpRatio;
    }

    public void setPredictedPrice(double predictedPrice) {
        this.predictedPrice = predictedPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public void setAsValid() {
        this.isValid = true;
        this.error = "";
    }

    public void setAsInvalid(String error) {
        this.isValid = false;
        this.error = error;
    }
}

    
