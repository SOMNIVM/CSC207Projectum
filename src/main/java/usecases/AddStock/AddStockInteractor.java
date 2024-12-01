package usecases.AddStock;

import entities.Portfolio;

import java.util.Map;

public class AddStockInteractor implements AddStockInputBoundary {
    private final AddStockDataAccessInterface addStockDataAccessInterface;
    private final AddStockOutputBoundary buyStockPresenter;
    public AddStockInteractor(AddStockOutputBoundary presenter, AddStockDataAccessInterface dataAccessInterface){
        this.buyStockPresenter = presenter;
        this.addStockDataAccessInterface = dataAccessInterface;
    }

    @Override
    public void execute(AddStockInputData addStockInputData) {
        String stockName = addStockInputData.getStockName();
        Map<String, String> stockNameToSymbolMap = addStockDataAccessInterface.getNameToSymbolMap();
        if (stockNameToSymbolMap.containsKey(stockName)) {
            Portfolio curPortfolio = addStockDataAccessInterface.getCurrentPortfolio();
            String symbol = stockNameToSymbolMap.get(addStockInputData.getStockName());
            int shareToBuy = addStockInputData.getSharesToBuy();
            double buyingPrice = addStockDataAccessInterface.queryPrice(symbol);
            curPortfolio.addStock(symbol, shareToBuy, buyingPrice);
            addStockDataAccessInterface.writeCurrentPortfolio();
            buyStockPresenter.prepareSuccessView(new AddStockOutputData(stockName, shareToBuy, buyingPrice));
        }
        else {
            buyStockPresenter.prepareFailView("The stock can't be found. Please try again.");
        }
    }

    @Override
    public void switchBack() {
        buyStockPresenter.switchBack();
    }
}
