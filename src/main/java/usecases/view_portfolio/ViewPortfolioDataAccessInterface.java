package usecases.view_portfolio;

import entities.Portfolio;

import java.util.List;
import java.util.Map;

public interface ViewPortfolioDataAccessInterface {
    Portfolio getCurrentPortfolio();
    Map<String, Double> getSymbolToCurrentPrice();
    Map<String, String> getNameToSymbolMap();
    Map<String, String> getSymbolToNameMap();
}
