package usecases.price_prediction;

import app.Main;
import entities.Stock;
import org.json.JSONObject;
import data_access.AlphaVantageDataAccess;
import app.Config;
import java.util.*;

public class AverageValueModel implements Model{
    private final int windowSize;
    public AverageValueModel(int windowSize) {
        this.windowSize = windowSize;
    }

    private double[] getMeanSd(JSONObject timeSeries) {
        List<String> keys = new ArrayList<>(timeSeries.keySet());
        Collections.sort(keys);
        double sum = 0.0;
        double sumOfSquare = 0.0;
        for (int i = keys.size() - 1; i >= keys.size() - windowSize; i--) {
            double closingPrice = Double.parseDouble(timeSeries.getJSONObject(keys.get(i)).getString("4. close"));
            sum += closingPrice;
            sumOfSquare += Math.pow(closingPrice, 2);
        }
        double mean = sum / windowSize;
        double secondMoment = sumOfSquare / windowSize;
        double standardDeviation = Math.sqrt(secondMoment - Math.pow(mean, 2));
        return new double[]{mean - 2 * standardDeviation, mean + 2 * standardDeviation};
    }

    @Override
    public double[] intraDayPredict(Stock stock, int interval) {
        AlphaVantageDataAccess dataAccess = new AlphaVantageDataAccess(Config.API_KEY);
        JSONObject timeSeries = dataAccess.getTimeSeriesIntraDay(interval, stock.getSymbol(), true);
        return getMeanSd(timeSeries);
    }

    public double[] nextDayPredict(Stock stock) {
        AlphaVantageDataAccess dataAccess = new AlphaVantageDataAccess(Config.API_KEY);
        JSONObject timeSeries = dataAccess.getTimeSeriesDaily(stock.getSymbol(), true);
        return getMeanSd(timeSeries);
    }
}
