package interface_adapters.add_stock;

import usecases.add_stock.AddStockInputBoundary;
import usecases.add_stock.AddStockInputData;

/**
 * The controller for the add stock use case.
 */
public class AddStockController {
    private final AddStockInputBoundary buyStockUseCaseInteractor;

    public AddStockController(AddStockInputBoundary interactor) {
        this.buyStockUseCaseInteractor = interactor;
    }

    /**
     * Execute the use case.
     * @param stockName The name of the stock to add.
     * @param shares The number of shares to add.
     */
    public void execute(String stockName, int shares) {
        buyStockUseCaseInteractor.execute(new AddStockInputData(stockName, shares));
    }

    /**
     * Switch back to homepage.
     */
    public void switchBack() {
        buyStockUseCaseInteractor.switchBack();
    }
}
