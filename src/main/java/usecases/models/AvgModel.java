package usecases.models.avgmodel;

import app.Config;

public class AvgModel extends Model {
    private final int numOfInterval;
    private final double[] observations;

    public AvgModel(int numOfInterval, double[] observations) {
        super();
        if (numOfInterval <= 0) {
            throw new IllegalArgumentException("Number of intervals must be greater than 0.");
        }
        if (observations == null || observations.length < numOfInterval) {
            throw new IllegalArgumentException("Observations array is null or does not match the number of intervals.");
        }
        this.numOfInterval = numOfInterval;
        this.observations = observations;
    }

    public double predict() {
        return getAvg();
    }

    private double getAvg() {
        double sum = 0;
        for (int i = 0; i < numOfInterval; i++) {
            sum += observations[i];
        }
        return sum / numOfInterval;
    }

    private double getAvgForFirstN(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += observations[i];
        }
        return sum / n;
    }

    public double getMeanSquareError() {
        double sum = 0;
        int validIntervals = 0;
        for (int i = 1; i < numOfInterval; i++) {
            double predicted = getAvgForFirstN(i);
            double actual = observations[i];
            double difference = predicted - actual;
            sum += difference * difference;
            validIntervals++;
        }
        return validIntervals > 0 ? sum / validIntervals : 0;
    }

    public double getMeanAbsoluteError() {
        double sum = 0;
        int validIntervals = 0;
        for (int i = 1; i < numOfInterval; i++) {
            double predicted = getAvgForFirstN(i);
            double actual = observations[i];
            double difference = predicted - actual;
            sum += Math.abs(difference);
            validIntervals++;
        }
        return validIntervals > 0 ? sum / validIntervals : 0;
    }

    public double getVariance() {
        double sum = 0;
        double avg = getAvg();
        for (int i = 0; i < numOfInterval; i++) {
            double difference = observations[i] - avg;
            sum += difference * difference;
        }
        return numOfInterval > 0 ? sum / numOfInterval : 0;
    }

    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }

    public double getSharpeRatio() {
        double stdDev = getStandardDeviation();
        if (stdDev == 0) {
            throw new ArithmeticException("Standard deviation is zero, cannot compute Sharpe Ratio.");
        }
        return (getAvg() - Config.INTEREST_RATE) / stdDev;
    }

    public double[] getObservations() {
        return observations.clone();
    }

}