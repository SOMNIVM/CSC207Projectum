package usecases;

import entities.Portfolio;
import kotlin.Pair;

import java.util.List;
import java.util.Map;

public interface OnlineDataAccessInterface {
    List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval);
    List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize);
    List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize);
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(Portfolio portfolio,
                                                                      int sampleSize,
                                                                      int interval);
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize);
    Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize);
}