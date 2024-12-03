package data_access;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import app.Config;
import entities.Portfolio;
import usecases.LocalDataAccessInterface;

/**
 * The data access object that uses local files only.
 */
public class LocalDataAccessObject implements LocalDataAccessInterface {
    private static final Path PORTFOLIO_DATA_FILE_PATH = Paths.get("app_data/portfolio_data.json");
    private static final String SYMBOL_LABEL = "symbol";
    private static final String SHARES_LABEL = "shares";
    private static final int JSON_INDENTATION = 4;
    private final Portfolio portfolio;
    private final Map<String, String> nameToSymbolMap;
    private final Map<String, String> symbolToNameMap;

    public LocalDataAccessObject() {
        this.portfolio = new Portfolio();
        this.nameToSymbolMap = new HashMap<>();
        this.symbolToNameMap = new HashMap<>();
        for (Object obj: Config.STOCK_LIST) {
            final JSONObject jsonObject = (JSONObject) obj;
            final String curName = jsonObject.getString("name");
            final String curSymbol = jsonObject.getString("symbol");
            this.nameToSymbolMap.put(curName, curSymbol);
            this.symbolToNameMap.put(curSymbol, curName);
        }
        try {
            if (Files.exists(PORTFOLIO_DATA_FILE_PATH)) {
                final JSONArray portfolioData = new JSONArray(Files.readString(PORTFOLIO_DATA_FILE_PATH));
                for (Object obj: portfolioData) {
                    final JSONObject portfolioDataObj = (JSONObject) obj;
                    this.portfolio.addStock(portfolioDataObj.getString(SYMBOL_LABEL),
                            portfolioDataObj.getInt(SHARES_LABEL), 0.0);
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Portfolio getCurrentPortfolio() {
        return portfolio;
    }

    @Override
    public void writeCurrentPortfolio() {
        final JSONArray curPortfolioData = new JSONArray();
        for (String symbol: portfolio.getStockSymbols()) {
            final JSONObject portfolioDataObj = new JSONObject();
            portfolioDataObj.put(SYMBOL_LABEL, symbol);
            portfolioDataObj.put(SHARES_LABEL, portfolio.getShares(symbol));
            curPortfolioData.put(portfolioDataObj);
        }
        try {
            Files.writeString(PORTFOLIO_DATA_FILE_PATH, curPortfolioData.toString(JSON_INDENTATION));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Map<String, String> getNameToSymbolMap() {
        return nameToSymbolMap;
    }

    @Override
    public Map<String, String> getSymbolToNameMap() {
        return symbolToNameMap;
    }
}
