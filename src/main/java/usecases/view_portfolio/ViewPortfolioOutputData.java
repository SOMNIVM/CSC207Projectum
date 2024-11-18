package usecases.view_portfolio;

import entities.Portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewPortfolioOutputData {
    private final List<String> stockNames;
    private final List<Integer> shares;
    private final List<Double> averagePrices;
    private final List<Double> totalValues;
    public ViewPortfolioOutputData(List<String> stockNames,
                                   List<Integer> shares,
                                   List<Double> averagePrices,
                                   List<Double> totalValues) {
        this.stockNames = stockNames;
        this.shares = shares;
        this.averagePrices = averagePrices;
        this.totalValues = totalValues;
    }
    public List<String> getStockNames() {
        return stockNames;
    }
    public List<Integer> getShares() {
        return shares;
    }
    public List<Double> getAveragePrices() {
        return averagePrices;
    }
    public List<Double> getTotalValues() {
        return totalValues;
    }
}
