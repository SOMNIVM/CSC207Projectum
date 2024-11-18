package usecases.buy_stock;

public class BuyStockInputData {
    private final String stockName;
    private final int shares;
    public BuyStockInputData(String stockName, int shares) {
        this.stockName = stockName;
        this.shares = shares;
    }
    public String getStockName() {
        return stockName;
    }
    public int getShares() {
        return shares;
    }
}
