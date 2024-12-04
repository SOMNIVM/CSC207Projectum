package interface_adapters.view_portfolio;

import java.util.List;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.view_portfolio.ViewPortfolioOutputBoundary;
import usecases.view_portfolio.ViewPortfolioOutputData;

/**
 * The presenter for viewing portfolio.
 */
public class ViewPortfolioPresenter implements ViewPortfolioOutputBoundary {
    private final ViewPortfolioViewModel viewPortfolioViewModel;
    private final ClearAllViewModel clearAllViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewPortfolioPresenter(ViewPortfolioViewModel viewPortfolioModel,
                                  ClearAllViewModel clearAllModel,
                                  ViewManagerModel managerModel) {
        this.viewPortfolioViewModel = viewPortfolioModel;
        this.clearAllViewModel = clearAllModel;
        this.viewManagerModel = managerModel;
    }

    @Override
    public void prepareView(ViewPortfolioOutputData viewPortfolioOutputData) {
        final List<String> stockList = viewPortfolioOutputData.getStocks();
        final List<Integer> shares = viewPortfolioOutputData.getShares();
        final List<Double> valuesPerShare = viewPortfolioOutputData.getValuesPerShare();
        final List<Double> values = viewPortfolioOutputData.getValues();
        final String[][] displayedArray = new String[stockList.size()][ViewPortfolioViewModel.COLUMNS.length];
        for (int i = 0; i < displayedArray.length; i++) {
            displayedArray[i][0] = stockList.get(i);
            displayedArray[i][1] = Integer.toString(shares.get(i));
            displayedArray[i][2] = String.format("%.2f", valuesPerShare.get(i));
            displayedArray[i][ViewPortfolioViewModel.COLUMNS.length - 1] = String.format("%.2f", values.get(i));
        }
        final ViewPortfolioState state = viewPortfolioViewModel.getState();
        state.setPortfolioData(displayedArray);
        state.setTotalValue(viewPortfolioOutputData.getTotalValue());
        viewPortfolioViewModel.getState().setPortfolioData(displayedArray);
        viewPortfolioViewModel.firePropertyChange("data");
        viewManagerModel.getState().setCurViewName(viewPortfolioViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
