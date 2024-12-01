package usecases.remove_stock;

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
    public boolean checkIfCleared() {
        return cleared;
    }
}
