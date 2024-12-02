package entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a portfolio containing stocks with their number of shares and average price.
 */
public class Portfolio {
    private final Map<String, Integer> share;

    /**
     * Constructs an empty portfolio.
     */
    public Portfolio() {
        this.share = new HashMap<>();
    }

    public Set<String> getStockSymbols() {
        return share.keySet();
    }

    /**
     * Gets the number of shares for a given stock symbol.
     *
     * @param symbol the stock symbol
     * @return the number of shares for the specified stock symbol, or 0 if the symbol is not in the portfolio
     */
    public int getShares(String symbol) {
        return share.get(symbol);
    }


    /**
     * Adds or updates a stock in the portfolio.
     *
     * @param symbol       the stock symbol
     * @param shares       the number of shares to add
     * @param avgPrice     the average price at which the shares were bought
     */
    public void addStock(String symbol, int shares, double avgPrice) {
        int existingShares = share.getOrDefault(symbol, 0);
        int totalShares = existingShares + shares;
        share.put(symbol, totalShares);
    }

    /**
     * Removes a specified number of shares of a stock from the portfolio.
     * If the shares to remove are greater than or equal to the current shares, the stock is fully removed.
     *
     * @param symbol the stock symbol
     * @param shares the number of shares to remove
     */
    public boolean removeStock(String symbol, int shares) {
        if (!share.containsKey(symbol)) {
            return false;
        }

        int existingShares = share.get(symbol);

        if (shares >= existingShares) {
            // Remove the stock entirely if removing equal or more shares than existing
            share.remove(symbol);
        } else {
            // Reduce the shares count without changing the average price
            share.put(symbol, existingShares - shares);
        }
        return true;
    }

    /**
     * Provides a string representation of the portfolio, listing each stock's details.
     *
     * @return a string summary of the portfolio
     */
    @Override
    public String toString() {
        StringBuilder portfolioSummary = new StringBuilder("Your portfolio contains the following assets:\n");

        for (Map.Entry<String, Integer> entry : share.entrySet()) {
            String symbol = entry.getKey();
            int shares = entry.getValue();

            portfolioSummary.append(String.format("%d shares of %s", shares, symbol));
        }

        return portfolioSummary.toString();
    }
}