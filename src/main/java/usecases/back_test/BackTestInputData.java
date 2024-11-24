package usecases.back_test;

public class BackTestInputData {
    private final String strategyName;
    private final String stockSymbol;
    private final int initialCapital;
    private final int startDate;
    private final int endDate;

    public BackTestInputData(String strategyName, String stockSymbol, int initialCapital, int startDate, int endDate) {
        this.strategyName = strategyName;
        this.stockSymbol = stockSymbol;
        this.initialCapital = initialCapital;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getInitialCapital() {
        return initialCapital;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }
}
