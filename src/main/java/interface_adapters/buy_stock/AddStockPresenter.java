package interface_adapters.buy_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.buy_stock.AddStockOutputBoundary;
import usecases.buy_stock.AddStockOutputData;

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
        AddStockState state = addStockViewModel.getState();
        state.setAsValid();
        state.setStockName(addStockOutputData.getStockName());
        state.setSharesChanged(addStockOutputData.getSharesPurchased());
        state.setBuyingPrice(addStockOutputData.getBuyingPrice());
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
