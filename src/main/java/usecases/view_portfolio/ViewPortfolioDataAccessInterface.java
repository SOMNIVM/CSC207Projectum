package usecases.view_portfolio;

import java.util.List;

public interface ViewPortfolioDataAccessInterface {
    List<String> getStockNames();
    List<Integer> getShares();
    List<Double> getAveragePrices();
    List<Double> getValues();
}
