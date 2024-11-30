package usecases.remove_stock;

public class RemoveStockOutputData {
    private final String stockName;
    private final int sharesRemoved;

    public RemoveStockOutputData(String stockName, int sharesRemoved) {
        this.stockName = stockName;
        this.sharesRemoved = sharesRemoved;
    }

    public String getStockName() {
        return stockName;
    }

    public int getSharesRemoved() {
        return sharesRemoved;
    }
}
