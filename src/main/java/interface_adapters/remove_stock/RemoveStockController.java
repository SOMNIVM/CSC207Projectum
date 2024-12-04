package interface_adapters.remove_stock;

import usecases.remove_stock.RemoveStockInputBoundary;
import usecases.remove_stock.RemoveStockInputData;

/**
 * The controller for the remove stock use case.
 */
public class RemoveStockController {
    private final RemoveStockInputBoundary removeStockUseCaseInteractor;

    public RemoveStockController(RemoveStockInputBoundary interactor) {
        this.removeStockUseCaseInteractor = interactor;
    }

    /**
     * Execute the remove stock use case.
     * @param stockName the name of the stock to be removed.
     * @param sharesToRemove the number of shares to remove.
     */
    public void execute(String stockName, int sharesToRemove) {
        removeStockUseCaseInteractor.execute(new RemoveStockInputData(stockName, sharesToRemove));
    }

    /**
     * Switch back to the homepage.
     */
    public void switchBack() {
        removeStockUseCaseInteractor.switchBack();
    }
}
