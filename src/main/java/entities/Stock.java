package entities;

/**
 * Represents a stock, which is a type of asset, with a stock symbol.
 */
public class Stock extends Asset{
    private final String stockSymbol;

    /**
     * Constructs a Stock with the specified name and stock symbol.
     *
     * @param name        the name of the stock
     * @param symbol the stock symbol of the stock
     */
    public Stock(String name, String symbol) {
        super(name);
        this.stockSymbol = symbol;
    }

    public String getCompanyName() {
        return this.getName();
    }

    /**
     * Gets the stock symbol of the stock.
     *
     * @return the stock symbol of the stock
     */
    public String getStockSymbol() {
        return stockSymbol;
    }
}
