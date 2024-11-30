package usecases.back_test;

import java.util.Map;

public class BackTestInputData {
    private final String model;
    private final String stockSymbol;
    private final int startDate;
    private final int endDate;
    private final Map<String, Integer> share; // Using standard library Map
    private final Map<String, Double> averagePrices;
    private final String frequency;

    public BackTestInputData(
            String model, 
            String stockSymbol, 
            int startDate, 
            int endDate, 
            Map<String, Integer> share, 
            Map<String, Double> averagePrices,
            String frequency) {
        this.model = model;
        this.stockSymbol = stockSymbol;
        this.startDate = startDate;
        this.endDate = endDate;
        this.share = share;
        this.averagePrices = averagePrices;
        this.frequency = frequency;
    }



    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public Map<String, Integer> getShare() {
        return share;
    }

    public Map<String, Double> getAveragePrices() {
        return averagePrices;
    }
    public String getFrequency() {
        return frequency;
    }
}