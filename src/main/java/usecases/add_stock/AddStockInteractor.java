
package usecases.add_stock;

import java.util.Map;

import entities.Portfolio;

/**
 * Interactor class for the Add Stock use case.
 * Implements the AddStockInputBoundary interface.
 */
public class AddStockInteractor implements AddStockInputBoundary {
    private final AddStockDataAccessInterface addStockDataAccessInterface;
    private final AddStockOutputBoundary buyStockPresenter;

    /**
     * Constructs an AddStockInteractor with the specified presenter and data access interface.
     *
     * @param presenter the output boundary for presenting the result
     * @param dataAccessInterface the data access interface for interacting with stock data
     */
    public AddStockInteractor(AddStockOutputBoundary presenter, AddStockDataAccessInterface dataAccessInterface) {
        this.buyStockPresenter = presenter;
        this.addStockDataAccessInterface = dataAccessInterface;
    }

    /**
     * Executes the add stock use case with the provided input data.
     *
     * @param addStockInputData the input data for adding a stock
     */
    @Override
    public void execute(AddStockInputData addStockInputData) {
        final String stockName = addStockInputData.getStockName();
        final Map<String, String> stockNameToSymbolMap = addStockDataAccessInterface.getNameToSymbolMap();
        if (stockNameToSymbolMap.containsKey(stockName)) {
            final Portfolio curPortfolio = addStockDataAccessInterface.getCurrentPortfolio();
            final String symbol = stockNameToSymbolMap.get(addStockInputData.getStockName());
            final int shareToBuy = addStockInputData.getSharesToBuy();
            final double buyingPrice = addStockDataAccessInterface.queryPrice(symbol);
            curPortfolio.addStock(symbol, shareToBuy, buyingPrice);
            addStockDataAccessInterface.writeCurrentPortfolio();
            buyStockPresenter.prepareSuccessView(new AddStockOutputData(stockName, shareToBuy, buyingPrice));
        }
        else {
            buyStockPresenter.prepareFailView("The stock can't be found. Please try again.");
        }
    }

    /**
     * Switches back to the previous state or view.
     */
    @Override
    public void switchBack() {
        buyStockPresenter.switchBack();
    }
}
