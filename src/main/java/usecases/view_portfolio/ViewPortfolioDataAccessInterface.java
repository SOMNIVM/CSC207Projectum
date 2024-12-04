package usecases.view_portfolio;

import java.util.Map;

import entities.Portfolio;

/**
 * The data access interface for view portfolio.
 */
public interface ViewPortfolioDataAccessInterface {
    /**
     * Get the current portfolio.
     * @return the current portfolio.
     */
    Portfolio getCurrentPortfolio();

    /**
     * Get the map from the symbols of the stocks in the current portfolio to the current stock prices.
     * @return the map from symbol to price.
     */
    Map<String, Double> getSymbolToCurrentPrice();

    /**
     * Get a bijection from stock names to stock symbols.
     * @return the map.
     */
    Map<String, String> getNameToSymbolMap();

    /**
     * Get a bijection from stock names to stock symbols.
     * @return the map.
     */
    Map<String, String> getSymbolToNameMap();
}
