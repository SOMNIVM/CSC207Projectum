package data_access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.OnlineDataAccessInterface;

/**
 * Mock data access object for accessing the AlphaVantage API.
 */
public class MockOnlineDataAccessObject implements OnlineDataAccessInterface {
    private static final int MAX_LEN = 100;
    private static final int WEEK_LEN = 7;
    private static final double RAND_MEAN = 0.5;
    private static final double DAILY_SD_MULTIPLIER = 18.0;
    private static final double WEEKLY_SD_MULTIPLIER = 48.0;
    private final DateTimeFormatter dateTimeFormatter;
    private final DateTimeFormatter dateFormatter;
    private final Map<String, List<Double>> mockTimeSeriesIntraDay;
    private final Map<String, List<Double>> mockTimeSeriesDaily;
    private final Map<String, List<Double>> mockTimeSeriesWeekly;

    public MockOnlineDataAccessObject() {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.mockTimeSeriesIntraDay = new HashMap<>();
        this.mockTimeSeriesDaily = new HashMap<>();
        this.mockTimeSeriesWeekly = new HashMap<>();
        initializeTimeSeries();
    }

    private void initializeTimeSeries() {
        final Random random = new Random(8);
        final List<String> stockList = new ArrayList<>();
        for (Object obj: Config.STOCK_LIST) {
            final JSONObject dataObject = (JSONObject) obj;
            stockList.add(dataObject.getString("symbol"));
        }
        initializeTimeSeriesForStockList(stockList, random);
    }

    private void initializeTimeSeriesForStockList(List<String> stockList, Random random) {
        for (String symbol: stockList) {
            final double basePrice = 350 + 100 * random.nextDouble();
            final List<Double> curTimeSeriesIntraDay = new ArrayList<>();
            final List<Double> curTimeSeriesDaily = new ArrayList<>();
            final List<Double> curTimeSeriesWeekly = new ArrayList<>();
            double curPrice = basePrice;
            for (int i = 0; i < MAX_LEN; i++) {
                curPrice += random.nextDouble() - RAND_MEAN;
                curTimeSeriesIntraDay.add(curPrice);
            }
            curPrice = basePrice + DAILY_SD_MULTIPLIER * (random.nextDouble() - RAND_MEAN);
            int i = 0;
            while (i < MAX_LEN) {
                final double tempCurPrice = curPrice + DAILY_SD_MULTIPLIER * (random.nextDouble() - RAND_MEAN);
                if (tempCurPrice > 0) {
                    curPrice = tempCurPrice;
                    curTimeSeriesDaily.add(curPrice);
                    if (i % WEEK_LEN == 0) {
                        curTimeSeriesWeekly.add(curPrice);
                    }
                    i += 1;
                }
            }
            final int m = curTimeSeriesWeekly.size();
            curPrice = curTimeSeriesWeekly.get(m - 1);
            int j = m;
            while (j < MAX_LEN) {
                final double tempCurPrice = curPrice + WEEKLY_SD_MULTIPLIER * (random.nextDouble() - RAND_MEAN);
                if (tempCurPrice > 0) {
                    curPrice = tempCurPrice;
                    curTimeSeriesWeekly.add(curPrice);
                    j += 1;
                }
            }
            this.mockTimeSeriesIntraDay.put(symbol, curTimeSeriesIntraDay);
            this.mockTimeSeriesDaily.put(symbol, curTimeSeriesDaily);
            this.mockTimeSeriesWeekly.put(symbol, curTimeSeriesWeekly);
        }
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval) {
        final LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime timeStamp = LocalDateTime.of(
                currentTime.getYear(),
                currentTime.getMonth(),
                currentTime.getDayOfMonth(),
                currentTime.getHour(),
                (currentTime.getMinute() / interval) * interval,
                0);
        final List<Pair<String, Double>> result = new ArrayList<>();
        final List<Double> timeSeries = mockTimeSeriesIntraDay.get(symbol);
        for (int i = 0; i < sampleSize; i++) {
            result.add(new Pair<>(timeStamp.format(dateTimeFormatter), timeSeries.get(i)));
            timeStamp = timeStamp.minusMinutes(interval);
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize) {
        LocalDate timeStamp = LocalDate.now().minusDays(1);
        final List<Pair<String, Double>> result = new ArrayList<>();
        final List<Double> timeSeries = mockTimeSeriesDaily.get(symbol);
        for (int i = 0; i < sampleSize; i++) {
            result.add(new Pair<>(timeStamp.format(dateFormatter), timeSeries.get(i)));
            timeStamp = timeStamp.minusDays(1);
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize) {
        LocalDate timeStamp = LocalDate.now().minusDays(1);
        final List<Pair<String, Double>> result = new ArrayList<>();
        final List<Double> timeSeries = mockTimeSeriesWeekly.get(symbol);
        for (int i = 0; i < sampleSize; i++) {
            result.add(new Pair<>(timeStamp.format(dateFormatter), timeSeries.get(i)));
            timeStamp = timeStamp.minusDays(WEEK_LEN);
        }
        Collections.reverse(result);
        return result;
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(
            Portfolio portfolio,
            int sampleSize,
            int interval) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (String symbol: symbols) {
            result.put(symbol, getSingleTimeSeriesIntraDay(symbol, sampleSize, interval));
        }
        return result;
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (String symbol: symbols) {
            result.put(symbol, getSingleTimeSeriesDaily(symbol, sampleSize));
        }
        return result;
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (String symbol: symbols) {
            result.put(symbol, getSingleTimeSeriesWeekly(symbol, sampleSize));
        }
        return result;
    }
}
