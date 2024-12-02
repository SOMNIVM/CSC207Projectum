package usecases.models;

public abstract class Model {
    private final int numOfInterval;
    private final double[] observations;
    private String type;

    protected Model(int numOfInterval, double[] observations,
                    String type) {
        if (numOfInterval <= 0) {
            throw new IllegalArgumentException("Number of intervals must be greater than 0.");
        }
        if (observations == null || observations.length < numOfInterval) {
            throw new IllegalArgumentException("Observations array is null or does not match the number of intervals.");
        }
        this.numOfInterval = numOfInterval;
        this.observations = observations;
        this.type = type;
    }
    public static Model createModel(String type, int numOfInterval, double[] observations) {
         switch (type) {
            case "avgModel":
                return new AvgModel(numOfInterval, observations);
            case "linearRegressionModel":
                throw new IllegalArgumentException("Model not implemented yet: " + type);
            default:
                        throw new IllegalArgumentException("Invalid model type: " + type);
                }
    }

    public abstract double getMeanSquaredError();
    public abstract double getMeanAbsoluteError();
    public abstract double getSharpeRatio();
    public abstract double getPredictedPrice();
    public abstract double getActualPrice();
    public abstract double getVariance();
    public abstract double getStandardDeviation();
    public abstract double[] getObservations();
}
