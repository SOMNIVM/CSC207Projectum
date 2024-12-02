package interface_adapters.add_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.add_stock.AddStockOutputBoundary;
import usecases.add_stock.AddStockOutputData;

/**
 * The presenter for the add stock use case.
 */
public class AddStockPresenter implements AddStockOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final AddStockViewModel addStockViewModel;
    private final ClearAllViewModel clearAllViewModel;

    public AddStockPresenter(AddStockViewModel buyStockModel,
                             ClearAllViewModel clearAllModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.addStockViewModel = buyStockModel;
        this.clearAllViewModel = clearAllModel;
    }

    @Override
    public void prepareSuccessView(AddStockOutputData addStockOutputData) {
        clearAllViewModel.getState().unclear();
        final AddStockState state = addStockViewModel.getState();
        state.setAsValid();
        state.setStockName(addStockOutputData.getStockName());
        state.setSharesChanged(addStockOutputData.getSharesPurchased());
        state.setCurrentPrice(addStockOutputData.getCurrentPrice());
        addStockViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorDescription) {
        addStockViewModel.getState().setAsInvalid(errorDescription);
        addStockViewModel.firePropertyChange();
    }

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
