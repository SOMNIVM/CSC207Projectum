package interface_adapters.reset_portfolio;

import interface_adapters.ViewManagerModel;
import interface_adapters.add_stock.AddStockViewModel;
import interface_adapters.remove_stock.RemoveStockViewModel;
import usecases.reset_portfolio.ClearAllOutputBoundary;

public class ClearAllPresenter implements ClearAllOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ClearAllViewModel clearAllViewModel;
    private final AddStockViewModel addStockViewModel;
    private final RemoveStockViewModel removeStockViewModel;
    public ClearAllPresenter(ClearAllViewModel clearAllModel,
                             AddStockViewModel buyStockModel,
                             RemoveStockViewModel removeStockModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.clearAllViewModel = clearAllModel;
        this.addStockViewModel = buyStockModel;
        this.removeStockViewModel = removeStockModel;
    }

    @Override
    public void prepareClearedView() {
        clearAllViewModel.getState().clear();
        clearAllViewModel.firePropertyChange();
    }


    @Override
    public void switchToBuyStock() {
        if (addStockViewModel != null) {
            viewManagerModel.getState().setCurViewName(addStockViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void switchToRemoveStock() {
        if (removeStockViewModel != null) {
            viewManagerModel.getState().setCurViewName(removeStockViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void switchToPredictRevenue() {
        return;
    }

    @Override
    public void switchToBacktest() {
        return;
    }
}
