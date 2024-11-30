package interface_adapters.remove_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.modify_portfolio.ModifyPortfolioState;
import usecases.remove_stock.RemoveStockOutputBoundary;
import usecases.remove_stock.RemoveStockOutputData;

public class RemoveStockPresenter implements RemoveStockOutputBoundary {
    private final RemoveStockViewModel removeStockViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomePageViewModel homePageViewModel;

    public RemoveStockPresenter(ViewManagerModel viewManagerModel,
                              RemoveStockViewModel removeStockViewModel,
                              HomePageViewModel homePageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.removeStockViewModel = removeStockViewModel;
        this.homePageViewModel = homePageViewModel;
    }

    @Override
    public void prepareSuccessView(RemoveStockOutputData removeStockOutputData) {
        // Update remove stock view state
        ModifyPortfolioState removeStockState = removeStockViewModel.getState();
        removeStockState.setStockName(removeStockOutputData.getStockName());
        removeStockState.setSharesChanged(removeStockOutputData.getSharesRemoved());
        removeStockState.setAsValid();
        removeStockViewModel.setState(removeStockState);
        removeStockViewModel.firePropertyChange();

        // Update home page state with removal confirmation
        HomePageState homePageState = homePageViewModel.getState();
        String confirmationMessage = String.format("Removed %d shares of %s", 
            removeStockOutputData.getSharesRemoved(),
            removeStockOutputData.getStockName());
        homePageState.setLastAction(confirmationMessage);
        homePageViewModel.setState(homePageState);
        homePageViewModel.firePropertyChange();

        // Return to home page view
        viewManagerModel.setActiveView(homePageViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override 
    public void prepareFailView(String error) {
        ModifyPortfolioState removeStockState = removeStockViewModel.getState();
        removeStockState.setAsInvalid(error);
        removeStockViewModel.setState(removeStockState);
        removeStockViewModel.firePropertyChange();
    }
}