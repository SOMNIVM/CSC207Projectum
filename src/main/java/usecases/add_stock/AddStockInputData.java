package usecases.add_stock;

/**
 * Data class representing the input data for adding a stock.
 */
public class AddStockInputData {
    private final String stockName;
    private final int sharesToBuy;

    /**
     * Constructs an AddStockInputData object with the specified stock name and number of shares to buy.
     *
     * @param stockName the name of the stock
     * @param sharesToBuy the number of shares to buy
     */
    public AddStockInputData(String stockName, int sharesToBuy) {
        this.stockName = stockName;
        this.sharesToBuy = sharesToBuy;
    }

    /**
     * Returns the name of the stock.
     *
     * @return the stock name
     */
    public String getStockName() {
        return stockName;
    }

    /**
     * Returns the number of shares to buy.
     *
     * @return the number of shares to buy
     */
    public int getSharesToBuy() {
        return sharesToBuy;
    }
}
