package usecases.models;

import app.Config;

    public class AvgModel extends Model {
        private final int numOfInterval;
        private final double[] observations;

        public AvgModel(int numOfInterval, double[] observations) {
            super(numOfInterval, observations, "avgModel");
            this.numOfInterval = numOfInterval;
            this.observations = observations;
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

    @Override
    public double getMeanSquaredError() {
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
    @Override
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
    @Override
    public double getVariance() {
        double sum = 0;
        double avg = getAvg();
        for (int i = 0; i < numOfInterval; i++) {
            double difference = observations[i] - avg;
            sum += difference * difference;
        }
        return numOfInterval > 0 ? sum / numOfInterval : 0;
    }
    @Override
    public double getStandardDeviation() {
        return Math.sqrt(getVariance());
    }
    @Override
    public double getSharpeRatio() {
        double stdDev = getStandardDeviation();
        if (stdDev == 0) {
            return 0.0;
        }
        return (getReturn() - Config.INTEREST_RATE) / stdDev;
    }
    @Override
    private double getReturn() {
        return (observations[numOfInterval - 1] - observations[0]) / observations[0];
    }
    @Override
    public double[] getObservations() {
        return observations.clone();
    }

}