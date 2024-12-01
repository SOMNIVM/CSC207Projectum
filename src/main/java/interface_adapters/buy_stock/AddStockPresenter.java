package interface_adapters.buy_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.add_stock.AddStockOutputBoundary;
import usecases.add_stock.AddStockOutputData;

public class AddStockPresenter implements AddStockOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final BuyStockViewModel buyStockViewModel;
    private final ClearAllViewModel clearAllViewModel;
    public AddStockPresenter(BuyStockViewModel buyStockModel,
                             ClearAllViewModel clearAllModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.buyStockViewModel = buyStockModel;
        this.clearAllViewModel = clearAllModel;
    }

    @Override
    public void prepareSuccessView(AddStockOutputData buyStockOutputData) {
        clearAllViewModel.getState().unclear();
        BuyStockState state = buyStockViewModel.getState();
        state.setAsValid();
        state.setStockName(buyStockOutputData.getStockName());
        state.setSharesChanged(buyStockOutputData.getSharesPurchased());
        state.setBuyingPrice(buyStockOutputData.getBuyingPrice());
        buyStockViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorDescription) {
        buyStockViewModel.getState().setAsInvalid(errorDescription);
        buyStockViewModel.firePropertyChange();
    }

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
