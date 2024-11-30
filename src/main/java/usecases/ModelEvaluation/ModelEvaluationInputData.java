package usecases.ModelEvaluation;

public class ModelEvaluationInputData {
    private final String modelType;
    private final String frequency;
    private final String length;
    private final String stockSymbol;
    private final String endDate;

    public ModelEvaluationInputData(String modelType, String frequency, String length, String stockSymbol, String endDate) {
        this.modelType = modelType;
        this.frequency = frequency;
        this.length = length;
        this.stockSymbol = stockSymbol;
        this.endDate = endDate;
    }

    public String getModelType() {
        return modelType;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getLength() {
        return length;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getEndDate() {
        return endDate;
    }
}
