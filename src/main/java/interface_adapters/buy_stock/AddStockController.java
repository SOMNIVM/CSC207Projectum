package interface_adapters.buy_stock;

import usecases.buy_stock.AddStockInputBoundary;
import usecases.buy_stock.AddStockInputData;

public class AddStockController {
    private final AddStockInputBoundary buyStockUseCaseInteractor;
    public AddStockController(AddStockInputBoundary interactor) {
        this.buyStockUseCaseInteractor = interactor;
    }
    public void execute(String stockName, int shares) {
        buyStockUseCaseInteractor.execute(new AddStockInputData(stockName, shares));
    }
    public void switchBack() {
        buyStockUseCaseInteractor.switchBack();
    }
}
