package interface_adapters.remove_stock;

import interface_adapters.ModifyPortfolioState;
import usecases.remove_stock.RemoveStockOutputBoundary;
import usecases.remove_stock.RemoveStockOutputData;

public class RemoveStockPresenter implements RemoveStockOutputBoundary {
    private final RemoveStockViewModel removeStockViewModel;
    public RemoveStockPresenter(RemoveStockViewModel viewModel) {
        this.removeStockViewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RemoveStockOutputData removeStockOutputData) {
        ModifyPortfolioState state = removeStockViewModel.getState();
        state.setAsValid();
        state.setStockName(removeStockOutputData.getStockName());
        state.setSharesChanged(-removeStockOutputData.getSharesRemoved());
        removeStockViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorDescription) {
        removeStockViewModel.getState().setAsInvalid(errorDescription);
        removeStockViewModel.firePropertyChange();
    }
}
