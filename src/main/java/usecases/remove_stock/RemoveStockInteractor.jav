package usecases.remove_stock;

import entities.Portfolio;
import entities.Stock;
import entities.StockInPortfolio;


public class RemoveStockInteractor implements RemoveStockInputBoundary{
    private final RemoveStockDataAccessInterface removeStockDataAccessInterface;
    private final RemoveStockOutputBoundary removeStockOutputBoundary;
    private final Portfolio portfolio;

    public RemoveStockInteractor(RemoveStockDataAccessInterface removeStockDataAccessInterface, 
                                RemoveStockOutputBoundary removeStockOutputBoundary,
                                Portfolio portfolio
                                ) {
        this.removeStockDataAccessInterface = removeStockDataAccessInterface;
        this.removeStockOutputBoundary = removeStockOutputBoundary;
        this.portfolio = portfolio;
    }
    @Override
    public void execute(RemoveStockInputData removeStockInputData) {
        String stockName = removeStockInputData.getStockName();
        int sharesToRemove = removeStockInputData.getSharesToRemove();
        
        if (portfolio.removeStock(stockName, sharesToRemove)) {
            removeStockDataAccessInterface.removeStock(portfolio);
            RemoveStockOutputData outputData = new RemoveStockOutputData(stockName, sharesToRemove);
            removeStockOutputBoundary.prepareSuccessView(outputData);
        } else {
            removeStockOutputBoundary.prepareFailView("Failed to remove stock: " + stockName);
        }
    }
}