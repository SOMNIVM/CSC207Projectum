package app;

import org.json.JSONObject;
import usecases.LocalDataAccessInterface;

public class LocalDataAccessObject implements LocalDataAccessInterface {
    private static final String portfolioDataFilePath = "portfolio_data.json";
    private static final String stockListDataFilePath = "stock_list.json";
    private final JSONObject portfolioData;
    private final JSONObject stockData;
    public LocalDataAccessObject() {

    }
}
