package usecases.models.AvgModel;
import app.Config;

public class AvgModel {
    private final int numOfInterval;
    private final double[] observations;

    public AvgModel(int numOfInterval
    , double[] observations) {
        this.numOfInterval = numOfInterval;
        this.observations = observations;
    }
    public double getAvg() {
        double sum = 0;
        for (int i = 0; i < numOfInterval; i++) {
            sum += observations[i];
        }
        return sum / numOfInterval;
    }
    private double getAvgForFirstN(int n) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += observations[i];
        }
        return sum / n;
    }
    public double meanSquareError() {
        double sum = 0;
        for (int i = 1; i <= numOfInterval; i++) {
            double predicted = getAvgForFirstN(i - 1);
            double actual = observations[i - 1];
            double difference = predicted - actual;
            sum += difference * difference;
        }
        return sum / numOfInterval;
    }
    public double variance() {
        double sum = 0;
        double avg = getAvg();
        for (int i = 0; i < numOfInterval; i++) {
            double difference = observations[i] - avg;
            sum += difference * difference;
        }
        return sum / numOfInterval;
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    public double sharpeRatio() {
        return (getAvg() - Config.INTEREST_RATE) / standardDeviation();
    }

    public double[] getObservations() {
        return observations;
    }
    
}
