package data_access;

import app.Config;
import entities.Portfolio;
import org.json.JSONArray;
import org.json.JSONObject;
import usecases.LocalDataAccessInterface;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LocalDataAccessObject implements LocalDataAccessInterface {
    private static final Path portfolioDataFilePath = Paths.get("app_data/portfolio_data.json");
    private static final String symbolLabel = "symbol";
    private static final String sharesLabel = "shares";
    private static final String averagePriceLabel = "average_price";
    private final Portfolio portfolio;
    private final Map<String, String> nameToSymbolMap;
    private final Map<String, String> symbolToNameMap;
    public LocalDataAccessObject() {
        this.portfolio = new Portfolio();
        this.nameToSymbolMap = new HashMap<>();
        this.symbolToNameMap = new HashMap<>();
        for (Object obj: Config.STOCK_LIST) {
            JSONObject jsonObject = (JSONObject) obj;
            String curName = jsonObject.getString("name");
            String curSymbol = jsonObject.getString("symbol");
            this.nameToSymbolMap.put(curName, curSymbol);
            this.symbolToNameMap.put(curSymbol, curName);
        }
        try {
            if (Files.exists(portfolioDataFilePath)) {
                JSONArray portfolioData = new JSONArray(Files.readString(portfolioDataFilePath));
                for (Object obj: portfolioData) {
                    JSONObject portfolioDataObj = (JSONObject) obj;
                    this.portfolio.addStock(portfolioDataObj.getString(symbolLabel),
                            portfolioDataObj.getInt(sharesLabel), 0.0);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Portfolio getCurrentPortfolio() {
        return portfolio;
    }

    @Override
    public void writeCurrentPortfolio() {
        JSONArray curPortfolioData = new JSONArray();
        for (String symbol: portfolio.getStockSymbols()) {
            JSONObject portfolioDataObj = new JSONObject();
            portfolioDataObj.put(symbolLabel, symbol);
            portfolioDataObj.put(sharesLabel, portfolio.getShares(symbol));
            curPortfolioData.put(portfolioDataObj);
        }
        try {
            Files.writeString(portfolioDataFilePath, curPortfolioData.toString(4));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
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
