//package data_access;
//
//import app.Config;
//import entities.Portfolio;
//import kotlin.Pair;
//import org.json.JSONObject;
//import usecases.OnlineDataAccessInterface;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class MockOnlineDataAccessObject implements OnlineDataAccessInterface {
//    private final DateTimeFormatter dateTimeFormatter;
//    private final DateTimeFormatter dateFormatter;
//    private final Map<String, List<Double>> mockTimeSeriesIntraDay;
//    private final Map<String, List<Double>> mockTimeSeriesDaily;
//    private final Map<String,List<Double>> mockTimeSeriesWeekly;
//    public MockOnlineDataAccessObject() {
//        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        this.mockTimeSeriesIntraDay = new HashMap<>();
//        this.mockTimeSeriesDaily = new HashMap<>();
//        this.mockTimeSeriesWeekly = new HashMap<>();
//        Random random = new Random(8);
//        List<String> stockList = new ArrayList<>();
//        for (Object obj: Config.STOCK_LIST) {
//            JSONObject dataObject = (JSONObject) obj;
//            stockList.add(dataObject.getString("symbol"));
//        }
//        for (String symbol: stockList) {
//            double basePrice = 350 + 100 * random.nextDouble();
//            List<Double> curTimeSeriesIntraDay = new ArrayList<>();
//            List<Double> curTimeSeriesDaily = new ArrayList<>();
//            List<Double> curTimeSeriesWeekly = new ArrayList<>();
//            double curPrice = basePrice;
//            for (int i = 0; i < 100; i++) {
//                curPrice += random.nextDouble() - 0.5;
//                curTimeSeriesIntraDay.add(curPrice);
//            }
//            curPrice = basePrice + 18 * (random.nextDouble() - 0.5);
//            for (int i = 0; i < 100; i++) {
//                double tempCurPrice = curPrice + 18 * (random.nextDouble() - 0.5);
//                if (tempCurPrice > 0) {
//                    curPrice = tempCurPrice;
//                    curTimeSeriesDaily.add(curPrice);
//                    if (i % 7 == 0) {
//                        curTimeSeriesWeekly.add(curPrice);
//                    }
//                }
//                else {
//                    i -= 1;
//                }
//            }
//            int m = curTimeSeriesWeekly.size();
//            curPrice = curTimeSeriesWeekly.get(m - 1);
//            for (int i = m; i < 100; i++) {
//                double tempCurPrice = curPrice + 48 * (random.nextDouble() - 0.5);
//                if (tempCurPrice > 0) {
//                    curPrice = tempCurPrice;
//                    curTimeSeriesWeekly.add(curPrice);
//                }
//                else {
//                    i -= 1;
//                }
//            }
//            this.mockTimeSeriesIntraDay.put(symbol, curTimeSeriesIntraDay);
//            this.mockTimeSeriesDaily.put(symbol, curTimeSeriesDaily);
//            this.mockTimeSeriesWeekly.put(symbol, curTimeSeriesWeekly);
//        }
//    }
//    @Override
//    public List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval) {
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime timeStamp = LocalDateTime.of(
//                currentTime.getYear(),
//                currentTime.getMonth(),
//                currentTime.getDayOfMonth(),
//                currentTime.getHour(),
//                (currentTime.getMinute() / interval) * interval,
//                0);
//        List<Pair<String, Double>> result = new ArrayList<>();
//        List<Double> timeSeries = mockTimeSeriesIntraDay.get(symbol);
//        for (int i = 0; i < sampleSize; i++) {
//            result.add(new Pair<>(timeStamp.format(dateTimeFormatter), timeSeries.get(i)));
//            timeStamp = timeStamp.minusMinutes(5);
//        }
//        Collections.reverse(result);
//        return result;
//    }
//
//    @Override
//    public List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize) {
//        LocalDate timeStamp = LocalDate.now().minusDays(1);
//        List<Pair<String, Double>> result = new ArrayList<>();
//        List<Double> timeSeries = mockTimeSeriesDaily.get(symbol);
//        for (int i = 0; i < sampleSize; i++) {
//            result.add(new Pair<>(timeStamp.format(dateFormatter), timeSeries.get(i)));
//            timeStamp = timeStamp.minusDays(1);
//        }
//        Collections.reverse(result);
//        return result;
//    }
//
//    @Override
//    public List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize) {
//        LocalDate timeStamp = LocalDate.now().minusDays(1);
//        List<Pair<String, Double>> result = new ArrayList<>();
//        List<Double> timeSeries = mockTimeSeriesWeekly.get(symbol);
//        for (int i = 0; i < sampleSize; i++) {
//            result.add(new Pair<>(timeStamp.format(dateFormatter), timeSeries.get(i)));
//            timeStamp = timeStamp.minusDays(7);
//        }
//        Collections.reverse(result);
//        return result;
//    }
//
//    @Override
//    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(
//            Portfolio portfolio,
//            int sampleSize,
//            int interval) {
//        List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
//        Map<String, List<Pair<String, Double>>> result = new HashMap<>();
//        for (String symbol: symbols) {
//            result.put(symbol, getSingleTimeSeriesIntraDay(symbol, sampleSize, interval));
//        }
//        return result;
//    }
//
//    @Override
//    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize) {
//        List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
//        Map<String, List<Pair<String, Double>>> result = new HashMap<>();
//        for (String symbol: symbols) {
//            result.put(symbol, getSingleTimeSeriesDaily(symbol, sampleSize));
//        }
//        return result;
//    }
//
//    @Override
//    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize) {
//        List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
//        Map<String, List<Pair<String, Double>>> result = new HashMap<>();
//        for (String symbol: symbols) {
//            result.put(symbol, getSingleTimeSeriesWeekly(symbol, sampleSize));
//        }
//        return result;
//    }
//}