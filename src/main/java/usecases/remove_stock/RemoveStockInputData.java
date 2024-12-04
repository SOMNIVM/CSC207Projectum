package usecases.remove_stock;

/**
 * The input data to the remove stock use case.
 */
public class RemoveStockInputData {
    private final String stockName;
    private final int sharesToRemove;

    public RemoveStockInputData(String stockName, int sharesToRemove) {
        this.stockName = stockName;
        this.sharesToRemove = sharesToRemove;
    }

    public String getStockName() {
        return stockName;
    }

    public int getSharesToRemove() {
        return sharesToRemove;
    }
}
