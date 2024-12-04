package usecases.models;

import app.Config;

/**
 * The model that predicts the next value as the arithmetic mean of the previous N values.
 */
public class AvgModel extends AbstractModel {
    private final int numOfInterval;
    private final double[] observations;

    public AvgModel(int numOfInterval, double[] observations) {
        super(numOfInterval, observations, "avgModel");
        this.numOfInterval = super.getNumOfInterval();
        this.observations = super.getObservations();
    }

    public double getPredictedPrice() {
        return getAvg();
    }

    public double getActualPrice() {
        return observations[numOfInterval - 1];
    }

    private double getAvg() {
        double sum = 0;
        for (int i = 0; i < numOfInterval; i++) {
            sum += observations[i];
        }
        return sum / numOfInterval;
    }

    private double getAvgForFirstN(int subSampleSize) {
        if (subSampleSize <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        double sum = 0;
        for (int i = 0; i < subSampleSize; i++) {
            sum += observations[i];
        }
        return sum / subSampleSize;
    }

    @Override
    public double getMeanSquaredError() {
        double sum = 0;
        int validIntervals = 0;
        for (int i = 1; i < numOfInterval; i++) {
            final double predicted = getAvgForFirstN(i);
            final double actual = observations[i];
            final double difference = predicted - actual;
            sum += difference * difference;
            validIntervals++;
        }
        double mse = 0;
        if (validIntervals > 0) {
            mse = sum / validIntervals;
        }
        return mse;
    }

    @Override
    public double getMeanAbsoluteError() {
        double sum = 0;
        int validIntervals = 0;
        for (int i = 1; i < numOfInterval; i++) {
            final double predicted = getAvgForFirstN(i);
            final double actual = observations[i];
            final double difference = predicted - actual;
            sum += Math.abs(difference);
            validIntervals++;
        }
        double mae = 0;
        if (validIntervals > 0) {
            mae = sum / validIntervals;
        }
        return mae;
    }

    @Override
    public double getVariance() {
        double sum = 0;
        final double avg = getAvg();
        for (int i = 0; i < numOfInterval; i++) {
            final double difference = observations[i] - avg;
            sum += difference * difference;
        }
        double variance = 0;
        if (numOfInterval > 0) {
            variance = sum / numOfInterval;
        }
        return variance;
    }

    @Override
    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }

    @Override
    public double getSharpeRatio() {
        final double stdDev = getStandardDeviation();
        if (stdDev == 0) {
            return 0.0;
        }
        return (getReturn() - Config.INTEREST_RATE) / stdDev;
    }
    private double getReturn() {
        return (observations[numOfInterval - 1] - observations[0]) / observations[0];
    }
}
