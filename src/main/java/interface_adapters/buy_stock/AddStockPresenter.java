
package interface_adapters.buy_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.add_stock.AddStockOutputBoundary;
import usecases.add_stock.AddStockOutputData;

/**
 * Presenter class for the Add Stock use case.
 * Implements the AddStockOutputBoundary interface.
 */
public class AddStockPresenter implements AddStockOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final BuyStockViewModel buyStockViewModel;
    private final ClearAllViewModel clearAllViewModel;

    /**
     * Constructs an AddStockPresenter with the specified view models.
     *
     * @param buyStockModel the view model for buying stock
     * @param clearAllModel the view model for clearing all data
     * @param managerModel the view manager model
     */
    public AddStockPresenter(BuyStockViewModel buyStockModel,
                             ClearAllViewModel clearAllModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.buyStockViewModel = buyStockModel;
        this.clearAllViewModel = clearAllModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param buyStockOutputData the output data for adding a stock
     */
    @Override
    public void prepareSuccessView(AddStockOutputData buyStockOutputData) {
        clearAllViewModel.getState().unclear();
        final BuyStockState state = buyStockViewModel.getState();
        state.setAsValid();
        state.setStockName(buyStockOutputData.getStockName());
        state.setSharesChanged(buyStockOutputData.getSharesPurchased());
        state.setBuyingPrice(buyStockOutputData.getBuyingPrice());
        buyStockViewModel.firePropertyChange();
    }

    /**
     * Prepares the failure view with the provided error description.
     *
     * @param errorDescription the description of the error
     */
    @Override
    public void prepareFailView(String errorDescription) {
        buyStockViewModel.getState().setAsInvalid(errorDescription);
        buyStockViewModel.firePropertyChange();
    }

    /**
     * Switches back to the previous state or view.
     */
    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
