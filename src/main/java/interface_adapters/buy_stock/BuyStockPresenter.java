package interface_adapters.buy_stock;

import usecases.buy_stock.BuyStockOutputBoundary;
import usecases.buy_stock.BuyStockOutputData;

public class BuyStockPresenter implements BuyStockOutputBoundary {
    private final BuyStockViewModel buyStockViewModel;
    public BuyStockPresenter(BuyStockViewModel viewModel) {
        this.buyStockViewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(BuyStockOutputData buyStockOutputData) {
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
}
