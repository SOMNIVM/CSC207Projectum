
package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import usecases.OnlineDataAccessInterface;

/**
 * The data access object using the AlphaVantage API.
 */
public class AlphaVantageDataAccessObject implements OnlineDataAccessInterface {
    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private static final String INTRADAY_FUNC_NAME = "TIME_SERIES_INTRADAY";
    private static final String DAILY_FUNC_NAME = "TIME_SERIES_DAILY";
    private static final String WEEKLY_FUNC_NAME = "TIME_SERIES_WEEKLY";
    private static final String CLOSING_PRICE_LABEL = "4. close";
    private final String apiKey;
    private final OkHttpClient client;

    public AlphaVantageDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient().newBuilder().build();
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval) {
        final JSONObject dataObject;
        try {
            dataObject = requestJSON(getQueryUrl(setParamsIntraDay(symbol, interval)))
                    .getJSONObject(String.format("Time Series (%dmin)", interval));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize) {
        final JSONObject dataObject;
        try {
            dataObject = requestJSON(getQueryUrl(setParameters(symbol, DAILY_FUNC_NAME)))
                    .getJSONObject("Time Series (Daily)");
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    @Override
    public List<Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize) {
        final JSONObject dataObject;
        try {
            dataObject = requestJSON(getQueryUrl(setParameters(symbol, WEEKLY_FUNC_NAME)))
                    .getJSONObject("Time Series (Weekly)");
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return getTimeSeriesFromJSONObject(dataObject, sampleSize);
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesIntraDay(Portfolio portfolio,
                                                                             int sampleSize,
                                                                             int interval) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final List<String> urls = new ArrayList<>();
        for (String symbol: symbols) {
            urls.add(getQueryUrl(setParamsIntraDay(symbol, interval)));
        }
        final List<JSONObject> resultList = getBulkTimeSeriesData(urls);
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (int i = 0; i < symbols.size(); i++) {
            final List<Pair<String, Double>> curTimeSeries = getTimeSeriesFromJSONObject(
                    resultList.get(i).getJSONObject(String.format("Time Series (%dmin)", interval)),
                    sampleSize);
            result.put(symbols.get(i), curTimeSeries);
        }
        return result;
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final List<String> urls = new ArrayList<>();
        for (String symbol: symbols) {
            urls.add(getQueryUrl(setParameters(symbol, DAILY_FUNC_NAME)));
        }
        final List<JSONObject> resultList = getBulkTimeSeriesData(urls);
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (int i = 0; i < symbols.size(); i++) {
            final List<Pair<String, Double>> curTimeSeries = getTimeSeriesFromJSONObject(
                    resultList.get(i).getJSONObject("Time Series (Daily)"),
                    sampleSize);
            result.put(symbols.get(i), curTimeSeries);
        }
        return result;
    }

    @Override
    public Map<String, List<Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize) {
        final List<String> symbols = new ArrayList<>(portfolio.getStockSymbols());
        final List<String> urls = new ArrayList<>();
        for (String symbol: symbols) {
            urls.add(getQueryUrl(setParameters(symbol, WEEKLY_FUNC_NAME)));
        }
        final List<JSONObject> resultList = getBulkTimeSeriesData(urls);
        final Map<String, List<Pair<String, Double>>> result = new HashMap<>();
        for (int i = 0; i < symbols.size(); i++) {
            final List<Pair<String, Double>> curTimeSeries = getTimeSeriesFromJSONObject(
                    resultList.get(i).getJSONObject("Weekly Time Series"),
                    sampleSize);
            result.put(symbols.get(i), curTimeSeries);
        }
        return result;
    }

    private List<Pair<String, String>> setParameters(String symbol, String funcName) {
        final List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("function", funcName));
        parameters.add(new Pair<>("symbol", symbol));
        parameters.add(new Pair<>("apikey", apiKey));
        return parameters;
    }

    private List<Pair<String, String>> setParamsIntraDay(String symbol, int interval) {
        final List<Pair<String, String>> parameters = new ArrayList<>();
        parameters.add(new Pair<>("function", INTRADAY_FUNC_NAME));
        parameters.add(new Pair<>("symbol", symbol));
        parameters.add(new Pair<>("interval", String.format("%dmin", interval)));
        parameters.add(new Pair<>("apikey", apiKey));
        return parameters;
    }

    private List<Pair<String, Double>> getTimeSeriesFromJSONObject(JSONObject timeSeriesObject, int sampleSize) {
        final List<Pair<String, Double>> result = new ArrayList<>();
        final List<String> timeStamps = new ArrayList<>(timeSeriesObject.keySet());
        Collections.sort(timeStamps);
        for (int i = timeStamps.size() - sampleSize; i < timeStamps.size(); i++) {
            final String timeStamp = timeStamps.get(i);
            result.add(new Pair<>(timeStamp, timeSeriesObject.getJSONObject(timeStamp).getDouble(CLOSING_PRICE_LABEL)));
        }
        return result;
    }

    private String getQueryUrl(List<Pair<String, String>> parameters) {
        final List<String> paramList = new ArrayList<>();
        for (Pair<String, String> parameter: parameters) {
            paramList.add(String.format("%s=%s", parameter.getFirst(), parameter.getSecond()));
        }
        return BASE_URL + String.join("&", paramList);
    }

    private JSONObject requestJSON(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                final ResponseBody responseBody = response.body();
                if (responseBody == null) {
                    throw new IllegalArgumentException("The content of the requested JSON file is empty.");
                }
                final String jsonString = responseBody.string();
                return new JSONObject(jsonString);
            }
            else {
                throw new IllegalStateException("Response failed: " + response.code());
            }
        }
    }

    private Callable<String> getSingleJSONObject(int index, List<String> urls, List<JSONObject> result) {
        return () -> {
            String message = null;
            try {
                result.set(index, requestJSON(urls.get(index)));
            }
            catch (IOException | IllegalArgumentException | IllegalStateException ex) {
                message = ex.getMessage();
            }
            return message;
        };
    }

    private List<JSONObject> getBulkTimeSeriesData(List<String> urls) {
        final List<String> synchronizedUrls = Collections.synchronizedList(urls);
        final List<JSONObject> bulkResult = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < urls.size(); i++) {
            bulkResult.add(null);
        }
        boolean exceptionOccurred = false;
        String message = "";
        try (ExecutorService executor = Executors.newFixedThreadPool(Config.CONCURRENCY_THREAD_COUNT)) {
            for (int i = 0; i < urls.size(); i++) {
                message = executor.submit(getSingleJSONObject(i, synchronizedUrls, bulkResult)).get();
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
        catch (ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
