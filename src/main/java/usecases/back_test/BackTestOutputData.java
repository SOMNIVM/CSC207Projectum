package usecases.back_test;


import java.util.List;

public class BackTestOutputData {

    private double totalProfitLoss;
    private List<Double> trades; // Using standard types
    private List<Double> equityCurve;

    public BackTestOutputData() {
    }

    public BackTestOutputData(double totalProfitLoss, List<Double> trades, List<Double> equityCurve) {
        this.totalProfitLoss = totalProfitLoss;
        this.trades = trades;
        this.equityCurve = equityCurve;
    }

    public double getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(double totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public List<Double> getTrades() {
        return trades;
    }

    public void setTrades(List<Double> trades) {
        this.trades = trades;
    }

    public List<Double> getEquityCurve() {
        return equityCurve;
    }

    public void setEquityCurve(List<Double> equityCurve) {
        this.equityCurve = equityCurve;
    }
}