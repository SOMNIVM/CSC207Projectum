package interface_adapters.buy_stock;

import usecases.buy_stock.BuyStockInputBoundary;
import usecases.buy_stock.BuyStockInputData;

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
