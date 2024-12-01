package interface_adapters.view_portfolio;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import usecases.view_portfolio.ViewPortfolioOutputBoundary;
import usecases.view_portfolio.ViewPortfolioOutputData;

import java.util.List;

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
       List<String> stockList = viewPortfolioOutputData.getStocks();
       List<Integer> shares = viewPortfolioOutputData.getShares();
       List<Double> averageBuyingPrices = viewPortfolioOutputData.getAveragePrices();
       List<Double> valuesPerShare = viewPortfolioOutputData.getValuesPerShare();
       List<Double> values = viewPortfolioOutputData.getValues();
       String[][] displayedArray = new String[stockList.size()][5];
       for (int i = 0; i < displayedArray.length; i++) {
           displayedArray[i][0] = stockList.get(i);
           displayedArray[i][1] = Integer.toString(shares.get(i));
           displayedArray[i][2] = String.format("%.2f", averageBuyingPrices.get(i));
           displayedArray[i][3] = String.format("%.2f", valuesPerShare.get(i));
           displayedArray[i][4] = String.format("%.2f", values.get(i));
       }
       ViewPortfolioState state = viewPortfolioViewModel.getState();
       state.setPortfolioData(displayedArray);
       state.setTotalValue(viewPortfolioOutputData.getTotalValue());
       viewManagerModel.getState().setCurViewName(viewPortfolioViewModel.getViewName());
       viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(clearAllViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
