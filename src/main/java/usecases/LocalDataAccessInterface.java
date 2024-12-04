package usecases;

import java.util.Map;

import entities.Portfolio;

/**
 * Data access interface for local data.
 */
public interface LocalDataAccessInterface {
    /**
     * Get the current portfolio.
     * @return the portfolio object representing the current state of portfolio.
     */
    Portfolio getCurrentPortfolio();

    /**
     * Write the current portfolio from memory to storage.
     */
    void writeCurrentPortfolio();

    /**
     * Get the bijection from the set of available stock names to the set of their symbols.
     * @return a map from every stock name to every symbol.
     */
    Map<String, String> getNameToSymbolMap();

    /**
     * Get the bijection from the set of available stock symbols to the set of available stock names.
     * @return a map from every stock symbol to every stock name.
     */
    Map<String, String> getSymbolToNameMap();
}
