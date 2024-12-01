package interface_adapters.buy_stock;

import usecases.add_stock.AddStockInputBoundary;
import usecases.add_stock.AddStockInputData;

public class BuyStockController {
    private final AddStockInputBoundary buyStockUseCaseInteractor;
    public BuyStockController(AddStockInputBoundary interactor) {
        this.buyStockUseCaseInteractor = interactor;
    }
    public void execute(String stockName, int shares) {
        buyStockUseCaseInteractor.execute(new AddStockInputData(stockName, shares));
    }
    public void switchBack() {
        buyStockUseCaseInteractor.switchBack();
    }
}
