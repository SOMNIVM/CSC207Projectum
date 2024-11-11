package usecases.price_prediction;
import entities.Stock;

public interface Model {
    /**
     *
     * @param stock The stock whose price needs to be created.
     * @param interval The interval at which datapoints are taken.
     * @return The 95% confidence interval of the predicted price at the next interval.
     */
    double[] intraDayPredict(Stock stock, int interval);

    /**
     *
     * @param stock The stock whose price needs to be created.
     * @return The 95% confidence interval of the predicted price on the next day.
     */
    double[] nextDayPredict(Stock stock);
}
