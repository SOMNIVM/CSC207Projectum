package usecases.remove_stock;

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
