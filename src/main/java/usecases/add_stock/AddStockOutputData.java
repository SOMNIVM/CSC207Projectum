
package usecases.add_stock;

/**
 * Data class representing the output data for adding a stock.
 */
public class AddStockOutputData {
    private final String stockName;
    private final int sharesPurchased;
    private final double buyingPrice;

    /**
     * Constructs an AddStockOutputData object with the specified stock name, number of shares purchased,
     * and buying price.
     *
     * @param stockName the name of the stock
     * @param sharesPurchased the number of shares purchased
     * @param buyingPrice the price at which the shares were bought
     */
    public AddStockOutputData(String stockName, int sharesPurchased, double buyingPrice) {
        this.stockName = stockName;
        this.sharesPurchased = sharesPurchased;
        this.buyingPrice = buyingPrice;
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
     * Returns the number of shares purchased.
     *
     * @return the number of shares purchased
     */
    public int getSharesPurchased() {
        return sharesPurchased;
    }

    /**
     * Returns the buying price of the shares.
     *
     * @return the buying price
     */
    public double getBuyingPrice() {
        return buyingPrice;
    }
}

