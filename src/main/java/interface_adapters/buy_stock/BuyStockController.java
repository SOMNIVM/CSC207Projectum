package interface_adapters.buy_stock;

import usecases.AddStock.BuyStockInputBoundary;
import usecases.AddStock.BuyStockInputData;

public class BuyStockController {
    private final BuyStockInputBoundary buyStockUseCaseInteractor;
    public BuyStockController(BuyStockInputBoundary interactor) {
        this.buyStockUseCaseInteractor = interactor;
    }
    public void execute(String stockName, int shares) {
        buyStockUseCaseInteractor.execute(new BuyStockInputData(stockName, shares));
    }
    public void switchBack() {
        buyStockUseCaseInteractor.switchBack();
    }
}
