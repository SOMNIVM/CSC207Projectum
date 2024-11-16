package entities;

/**
 * Represents a stock that exists in a portfolio, including information
 * about the average purchase price and number of shares.
 */
public class StockInPortfolio extends Stock {
    private double averagePrice;
    private int numberOfShares;

    /**
     * Constructs a StockInPortfolio with the specified name, stock symbol, average price, and number of shares.
     *
     * @param name          the name of the stock
     * @param stockSymbol   the stock symbol of the stock
     * @param averagePrice  the average price paid for the stock
     * @param numberOfShares the number of shares of the stock
     */
    public StockInPortfolio(String name, String stockSymbol, double averagePrice, int numberOfShares) {
        super(name, stockSymbol);
        this.averagePrice = averagePrice;
        this.numberOfShares = numberOfShares;
    }

    /**
     * Gets the average price paid for the stock.
     *
     * @return the average price paid for the stock
     */
    public double getAveragePrice() {
        return averagePrice;
    }

    /**
     * Sets the average price paid for the stock.
     *
     * @param averagePrice the new average price
     */
    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    /**
     * Gets the number of shares of the stock.
     *
     * @return the number of shares of the stock
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Sets the number of shares of the stock.
     *
     * @param numberOfShares the new number of shares
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    /**
     * Looks up the current price of the stock.
     *
     * @return the current price of the stock (placeholder value)
     */
    public double lookForPrice() {
        return 0; // Have not implemented the use case yet.
    }
}
