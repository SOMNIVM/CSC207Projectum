package app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.json.JSONArray;

/**
 * A class containing the configurations.
 */
public class Config {
    public static final String API_KEY = initializeApiKey();
    public static final int INTRADAY_SAMPLE_SIZE = 24;
    public static final int DAILY_SAMPLE_SIZE = 60;
    public static final int WEEKLY_SAMPLE_SIZE = 48;
    public static final int INTRADAY_PREDICT_INTERVAL = 5;
    public static final int CONCURRENCY_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    public static final JSONArray STOCK_LIST = initializeStockList();
    public static final double INTEREST_RATE = 0.05;

    private static String initializeApiKey() {
        try {
            return Files.readString(Paths.get(Objects.requireNonNull(Config
                    .class
                    .getClassLoader()
                    .getResource("config/api_key.txt"))
                    .toURI()))
                    .trim();
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static JSONArray initializeStockList() {
        try {
            final String jsonString = Files.readString(Paths.get(Objects.requireNonNull(Config
                    .class
                    .getClassLoader()
                    .getResource("config/stock_list.json"))
                    .toURI()))
                    .trim();
            return new JSONArray(jsonString);
        }
        catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
