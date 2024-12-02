package interface_adapters.remove_stock;

import interface_adapters.ModifyPortfolioState;
import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.remove_stock.RemoveStockOutputBoundary;
import usecases.remove_stock.RemoveStockOutputData;

public class RemoveStockPresenter implements RemoveStockOutputBoundary {
    private final RemoveStockViewModel removeStockViewModel;
    private final ClearAllViewModel clearAllViewModel;
    private final ViewManagerModel viewManagerModel;
    public RemoveStockPresenter(RemoveStockViewModel removeStockModel,
                                ClearAllViewModel clearAllModel,
                                ViewManagerModel managerModel) {
        this.removeStockViewModel = removeStockModel;
        this.clearAllViewModel = clearAllModel;
        this.viewManagerModel = managerModel;
    }

    @Override
    public void prepareSuccessView(RemoveStockOutputData removeStockOutputData) {
        if (removeStockOutputData.checkIfCleared()) {
            clearAllViewModel.getState().clear();
        }
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

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
