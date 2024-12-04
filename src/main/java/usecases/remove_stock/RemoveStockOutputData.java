package usecases.remove_stock;

/**
 * The output data of the remove stock use case..
 */
public class RemoveStockOutputData {
    private final String stockName;
    private final int sharesRemoved;
    private final boolean cleared;

    public RemoveStockOutputData(String stockName, int sharesRemoved, boolean cleared) {
        this.stockName = stockName;
        this.sharesRemoved = sharesRemoved;
        this.cleared = cleared;
    }

    public String getStockName() {
        return stockName;
    }

    public int getSharesRemoved() {
        return sharesRemoved;
    }

    /**
     * Check if the portfolio have been completely cleared.
     * @return true if the portfolio contains no stock at all.
     */
    public boolean checkIfCleared() {
        return cleared;
    }
}
