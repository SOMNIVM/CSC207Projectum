package usecases.AddStock;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;

import java.util.Map;

public class BuyStockInteractor implements BuyStockInputBoundary{
    private final BuyStockDataAccessInterface buyStockDataAccessInterface;
    private final BuyStockOutputBoundary buyStockPresenter;
    public BuyStockInteractor(BuyStockOutputBoundary presenter, BuyStockDataAccessInterface dataAccessInterface){
        this.buyStockPresenter = presenter;
        this.buyStockDataAccessInterface = dataAccessInterface;
    }

    @Override
    public void execute(BuyStockInputData buyStockInputData) {
        String stockName = buyStockInputData.getStockName();
        Map<String, String> stockNameToSymbolMap = buyStockDataAccessInterface.getNameToSymbolMap();
        if (stockNameToSymbolMap.containsKey(stockName)) {
            Portfolio curPortfolio = buyStockDataAccessInterface.getCurrentPortfolio();
            String symbol = stockNameToSymbolMap.get(buyStockInputData.getStockName());
            int shareToBuy = buyStockInputData.getSharesToBuy();
            double buyingPrice = buyStockDataAccessInterface.queryPrice(symbol);
            curPortfolio.addStock(symbol, shareToBuy, buyingPrice);
            buyStockDataAccessInterface.writeCurrentPortfolio();
            buyStockPresenter.prepareSuccessView(new BuyStockOutputData(stockName, shareToBuy, buyingPrice));
        }
        else {
            buyStockPresenter.prepareFailView("The stock can't be found. Please try again.");
        }
    }
}
