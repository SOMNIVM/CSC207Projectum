
package data_access;

import app.Config;
import kotlin.Pair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.json.JSONObject;

import usecases.OnlineDataAccessInterface;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

import org.jetbrains.annotations.NotNull;

public class AlphaVantageDataAccessObject implements OnlineDataAccessInterface {
    private final String apiKey;
    private static final String baseURL = "https://www.alphavantage.com/query?";
    private static final String INTRADAY_FUNC_NAME = "TIME_SERIES_INTRADAY";
    private static final String DAILY_FUNC_NAME = "TIME_SERIES_DAILY";
    private static final String WEEKLY_FUNC_NAME = "TIME_SERIES_WEEKLY";
    private static final String CLOSING_PRICE_LABEL = "4. close";

    private final OkHttpClient client;
    public AlphaVantageDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient().newBuilder().build();
    }

//    /**
//     * @param interval The size of interval in minutes.
//     * @param symbol The symbol representing the stock being queried.
//     * @param isCompact If true, only the most recent 100 datapoints are returned. If false, return the data
//     *                  over the last 30 days.
//     * @return a JSONObject containing the intra-day time series data of the stock represented by symbol
//     *         at an interval of 1, 5, 15, 30, or 60 minutes.
//     */
//    public JSONObject getTimeSeriesIntraDay(int interval, String symbol, boolean isCompact) {
//        String outputSize = "compact";
//        if (!isCompact) {
//            outputSize = "full";
//        }
//        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="
//                + symbol
//                + "&interval="
//                + interval
//                + "min&outputsize="
//                + outputSize
//                + "&apikey="
//                + apiKey;
//        return requestJSON(url).getJSONObject("Time Series (" + interval + "min)");
//    }
//
//    /**
//     * @param symbol The symbol that encodes both the stock and the stock exchange from which the data is extracted.
//     * @param isCompact If true, return the last 100 datapoints.
//     *                  If false, return the historical data in the past 20+ years.
//     * @return the JSONObject containing the time series data of the stock represented by the given symbol.
//     */
//    public JSONObject getTimeSeriesDaily(String symbol, boolean isCompact) {
//        String outputSize = "compact";
//        if (!isCompact) {
//            outputSize = "full";
//        }
//        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
//                + symbol
//                + "&outputsize="
//                + outputSize
//                + "&apikey="
//                + apiKey;
//        return requestJSON(url).getJSONObject("Time Series (Daily)");
//    }

//    public SortedMap<String, Double> getSingleTimeSeriesIntraday(String symbol, int sampleSize, int interval) {
//        List<Pair<String, String>> parameters = new ArrayList<>();
//        parameters.add(new Pair<>("function", INTRADAY_FUNC_NAME));
//        parameters.add(new Pair<>("symbol", symbol));
//        parameters.add(new Pair<>("interval", String.format("%dmin", interval)));
//        parameters.add(new Pair<>("apiKey", apiKey));
//        JSONObject dataObject = requestJSON(getQueryURL(parameters)).getJSONObject(String.format("Time Series (%dmin)", interval));
//
//    }

//    private SortedMap<String, Double> getTimeSeriesFromJSONArray(JSONObject timeSeriesObject, int sampleSize) {
//        SortedMap<String, Double> result = new TreeMap<>();
//        List<String> timeStamps = new ArrayList<>(timeSeriesObject.keySet());
//        Collections.sort(timeStamps);
//        for (int i = timeStamps.size() - sampleSize; i < timeStamps.size(); i++) {
//            String timeStamp = timeStamps.get(i);
//            result.put(timeStamp, timeSeriesObject.getJSONObject(timeStamp).getDouble(CLOSING_PRICE_LABEL));
//        }
//        return result;
//    }

    public List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval) {
        JSONObject dataObject = requestJSON(getQueryURL(setParamsIntraDay(symbol, interval)))
                .getJSONObject(String.format("Time Series (%dmin)", interval));
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    public List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize) {
        JSONObject dataObject = requestJSON(getQueryURL(setParameters(symbol, DAILY_FUNC_NAME)))
                .getJSONObject("Time Series (Daily)");
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    public List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize) {
        JSONObject dataObject = requestJSON(getQueryURL(setParameters(symbol, WEEKLY_FUNC_NAME)))
                .getJSONObject("Time Series (Weekly)");
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(List<String> symbols,
                                                                             int sampleSize,
                                                                             int interval) {
        List<String> urls = new ArrayList<>();
        for (String symbol: symbols) {
            urls.add(getQueryURL(setParamsIntraDay(symbol, interval)));
        }
        return JSONToMap(symbols, sampleSize, urls);
    }

    @NotNull
    private Map<String, List<Pair<String, Double>>> JSONToMap(List<String> symbols, int sampleSize, List<String> urls) {
        List<JSONObject> dataList = getBulkTimeSeriesData(urls);
        Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (int i = 0; i < urls.size(); i++) {
            result.put(symbols.get(i), getTimeSeriesFromJSONObject(dataList.get(i), sampleSize));
        }
        return result;
    }

    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(List<String> symbols,
                                                                          int sampleSize) {
        return getBulkTimeSeries(symbols, sampleSize, DAILY_FUNC_NAME);
    }

    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(List<String> symbols,
                                                                           int sampleSize) {
        return getBulkTimeSeries(symbols, sampleSize, WEEKLY_FUNC_NAME);
    }

    @NotNull
    private Map<String, List<Pair<String, Double>>> getBulkTimeSeries(List<String> symbols,
                                                                      int sampleSize,
                                                                      String funcName) {
        List<String> urls = new ArrayList<>();
        for (String symbol: symbols) {
            urls.add(getQueryURL(setParameters(symbol, funcName)));
        }
        return JSONToMap(symbols, sampleSize, urls);
    }

    private List<Pair<String, String>> setParameters(String symbol, String funcName) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("function", funcName));
        parameters.add(new Pair<>("symbol", symbol));
        parameters.add(new Pair<>("apikey", apiKey));
        return parameters;
    }

    private List<Pair<String, String>> setParamsIntraDay(String symbol, int interval) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("function", INTRADAY_FUNC_NAME));
        parameters.add(new Pair<>("symbol", symbol));
        parameters.add(new Pair<>("interval", String.format("%dmin", interval)));
        parameters.add(new Pair<>("apikey", apiKey));
        return parameters;
    }

    private List<Pair<String, Double>> getTimeSeriesFromJSONObject(JSONObject timeSeriesObject, int sampleSize) {
        List<Pair<String, Double>> result = new ArrayList<>();
        List<String> timeStamps = new ArrayList<>(timeSeriesObject.keySet());
        Collections.sort(timeStamps);
        for (int i = timeStamps.size() - 1; i >= timeStamps.size() - sampleSize; i--) {
            String timeStamp = timeStamps.get(i);
            result.add(new Pair<>(timeStamp, timeSeriesObject.getJSONObject(timeStamp).getDouble(CLOSING_PRICE_LABEL)));
        }
        return result;
    }

    private String getQueryURL(List<Pair<String, String>> parameters) {
        List<String> paramList = new ArrayList<>();
        for (Pair<String, String> parameter: parameters) {
            paramList.add(String.format("%s=%s", parameter.getFirst(), parameter.getSecond()));
        }
        return baseURL + String.join("&", paramList);
    }

    private JSONObject requestJSON(String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()){
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new RuntimeException("The content of the requested JSON file is empty.");
                }
                return new JSONObject(responseBody.string());
            } else {
                throw new RuntimeException("Response failed: " + response.code());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to get a response.");
        }
    }

    private Callable<String> getSingleJSONObject(int index, List<String> urls, List<JSONObject> result) {
        return () -> {
            try {
                result.set(index, requestJSON(urls.get(index)));
                return null;
            }
            catch (RuntimeException e) {
                return e.getMessage();
            }
        };
    }

    private List<JSONObject> getBulkTimeSeriesData(List<String> urls) {
        final List<String> synchronizedURLs = Collections.synchronizedList(urls);
        final List<JSONObject> bulkResult = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < urls.size(); i++) {
            bulkResult.add(null);
        }
        boolean exceptionOccurred = false;
        String message = "";
        try (ExecutorService executor = Executors.newFixedThreadPool(Config.CONCURRENCY_THREAD_COUNT)) {
            for (int i = 0; i < urls.size(); i++) {
                message = executor.submit(getSingleJSONObject(i, synchronizedURLs, bulkResult)).get();
                if (message != null) {
                    exceptionOccurred = true;
                    break;
                }
            }
            if (exceptionOccurred) {
                throw new RuntimeException(message);
            }
            return new ArrayList<>(bulkResult);
        }
        catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
