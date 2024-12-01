package interface_adapters.buy_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllPresenter;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.buy_stock.BuyStockOutputBoundary;
import usecases.buy_stock.BuyStockOutputData;

public class BuyStockPresenter implements BuyStockOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final BuyStockViewModel buyStockViewModel;
    private final ClearAllViewModel clearAllViewModel;
    public BuyStockPresenter(BuyStockViewModel viewModel,
                             ClearAllViewModel clearAllModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.buyStockViewModel = viewModel;
        this.clearAllViewModel = clearAllModel;
    }

    @Override
    public void prepareSuccessView(BuyStockOutputData buyStockOutputData) {
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
