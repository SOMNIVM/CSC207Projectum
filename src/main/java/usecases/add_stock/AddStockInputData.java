package usecases.add_stock;

public class AddStockInputData {
    private final String stockName;
    private final int sharesToBuy;
    public AddStockInputData(String stockName, int sharesToBuy) {
        this.stockName = stockName;
        this.sharesToBuy = sharesToBuy;
    }
    public String getStockName() {
        return stockName;
    }
    public int getSharesToBuy() {
        return sharesToBuy;
    }
}
