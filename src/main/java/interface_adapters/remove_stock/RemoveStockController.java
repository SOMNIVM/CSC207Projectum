package interface_adapters.remove_stock;

import usecases.remove_stock.RemoveStockInputBoundary;
import usecases.remove_stock.RemoveStockInputData;

public class RemoveStockController {
    private final RemoveStockInputBoundary removeStockUseCaseInteractor;
    public RemoveStockController(RemoveStockInputBoundary interactor) {
        this.removeStockUseCaseInteractor = interactor;
    }
    public void execute(String stockName, int sharesToRemove) {
        removeStockUseCaseInteractor.execute(new RemoveStockInputData(stockName, sharesToRemove));
    }
}
