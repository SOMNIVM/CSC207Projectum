package usecases.view_portfolio;

import java.util.ArrayList;
import java.util.List;

public class ViewPortfolioOutputData {
    private final List<String> stocks;
    private final List<Integer> shares;
    private final List<Double> valuesPerShare;
    public ViewPortfolioOutputData(List<String> stocks,
                                   List<Integer> shares,
                                   List<Double> valuesPerShare) {
        this.stocks = stocks;
        this.shares = shares;
        this.valuesPerShare = valuesPerShare;
    }
    public List<String> getStocks() {
        return stocks;
    }
    public List<Integer> getShares() {
        return shares;
    }
    public List<Double> getValuesPerShare() {
        return valuesPerShare;
    }
    public List<Double> getValues() {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < valuesPerShare.size(); i++) {
            result.add(shares.get(i) * valuesPerShare.get(i));
        }
        return result;
    }
    public double getTotalValue() {
        double result = 0.0;
        for (int i = 0; i < valuesPerShare.size(); i++) {
            result += shares.get(i) * valuesPerShare.get(i);
        }
        return result;
    }
}
