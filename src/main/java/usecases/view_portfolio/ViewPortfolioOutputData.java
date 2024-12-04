package usecases.view_portfolio;

import java.util.ArrayList;
import java.util.List;

/**
 * The output data for displaying portfolio.
 */
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

    /**
     * Get a list total values of the stocks with the same ordering of stocks as in valuesPerShare.
     * @return the list of total values of the stocks.
     */
    public List<Double> getValues() {
        final List<Double> result = new ArrayList<>();
        for (int i = 0; i < valuesPerShare.size(); i++) {
            result.add(shares.get(i) * valuesPerShare.get(i));
        }
        return result;
    }

    /**
     * Get the total value of the portfolio.
     * @return the total value of the portfolio.
     */
    public double getTotalValue() {
        double result = 0.0;
        for (int i = 0; i < valuesPerShare.size(); i++) {
            result += shares.get(i) * valuesPerShare.get(i);
        }
        return result;
    }
}
