package interface_adapters.view_portfolio;

import interface_adapters.ViewManagerModel;
import usecases.view_portfolio.ViewPortfolioOutputBoundary;
import usecases.view_portfolio.ViewPortfolioOutputData;

import java.util.List;

public class ViewPortfolioPresenter implements ViewPortfolioOutputBoundary {
    private final ViewPortfolioViewModel viewPortfolioViewModel;
    private final ViewManagerModel viewManagerModel;
    public ViewPortfolioPresenter(ViewPortfolioViewModel viewPortfolioModel, ViewManagerModel managerModel) {
        this.viewPortfolioViewModel = viewPortfolioModel;
        this.viewManagerModel = managerModel;
    }

    @Override
    public void prepareView(ViewPortfolioOutputData viewPortfolioOutputData) {
        List<String> stockNames = viewPortfolioOutputData.getStockNames();
        List<Integer> shares = viewPortfolioOutputData.getShares();
        List<Double> averagePrices = viewPortfolioOutputData.getAveragePrices();
        List<Double> values = viewPortfolioOutputData.getValues();
        String[][] displayedDataArray = new String[stockNames.size()][4];
        for (int i = 0; i < stockNames.size(); i++) {
            displayedDataArray[i][0] = stockNames.get(i);
            displayedDataArray[i][1] = Integer.toString(shares.get(i));
            displayedDataArray[i][2] = String.format("%.2f", averagePrices.get(i));
            displayedDataArray[i][3] = String.format("%.2f", values.get(i));
        }
        viewPortfolioViewModel.getState().setPortfolioData(displayedDataArray);
        viewPortfolioViewModel.getState().setTotalValue(viewPortfolioOutputData.getTotalValue());
        viewManagerModel.getState().setCurViewName("view portfolio");
        viewManagerModel.firePropertyChange();
    }
}
