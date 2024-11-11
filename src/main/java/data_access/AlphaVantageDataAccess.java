package data_access;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AlphaVantageDataAccess {
    private final String apiKey;
    public AlphaVantageDataAccess(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @param interval The size of interval in minutes.
     * @param symbol The symbol representing the stock being queried.
     * @param isCompact If true, only the most recent 100 datapoints are returned. If false, return the data
     *                  over the last 30 days.
     * @return a JSONObject containing the intra-day time series data of the stock represented by symbol
     *         at an interval of 1, 5, 15, 30, or 60 minutes.
     */
    public JSONObject getTimeSeriesIntraDay(int interval, String symbol, boolean isCompact) {
        String outputSize = "compact";
        if (!isCompact) {
            outputSize = "full";
        }
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="
                + symbol
                + "&interval="
                + interval
                + "min&outputsize="
                + outputSize
                + "&apikey="
                + apiKey;
        return requestJSON(url).getJSONObject("Time Series (" + interval + "min)");
    }

    /**
     * @param symbol The symbol that encodes both the stock and the stock exchange from which the data is extracted.
     * @param isCompact If true, return the last 100 datapoints.
     *                  If false, return the historical data in the past 20+ years.
     * @return the JSONObject containing the time series data of the stock represented by the given symbol.
     */
    public JSONObject getTimeSeriesDaily(String symbol, boolean isCompact) {
        String outputSize = "compact";
        if (!isCompact) {
            outputSize = "full";
        }
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                + symbol
                + "&outputsize="
                + outputSize
                + "&apikey="
                + apiKey;
        return requestJSON(url).getJSONObject("Time Series (Daily)");
    }

    private static JSONObject requestJSON(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()){
            if (response.isSuccessful()) {
                assert response.body() != null;
                return new JSONObject(response.body().string());
            } else {
                throw new RuntimeException("Response failed: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get a response.");
        }
    }
}
