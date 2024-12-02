package usecases.add_stock;

import usecases.LocalDataAccessInterface;

/**
 * Interface for adding stock data access.
 *
 * @null
 */
public interface AddStockDataAccessInterface extends LocalDataAccessInterface {
    /**
     * Queries the current price for the given stock symbol.
     *
     * @param symbol the stock symbol to query the price for
     * @return the current price of the specified stock symbol, or null if the symbol is not found
     */
    double queryPrice(String symbol);
}
