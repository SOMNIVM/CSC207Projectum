package interface_adapters.ModelEvaluation;

public class ModelResultState {
    private String modelName = "Invalid Model";
    private String frequency= "Invalid Frequency";
    private int length;
    private double meanSquaredError;
    private double meanAbsoluteError;
    private double sharpeRatio;
    private double predictedPrice;
    private double actualPrice;
    private boolean isValid = true;
    private String error = "Error not found";

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
        this.error = error;
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
        return error;
    }

}

    
