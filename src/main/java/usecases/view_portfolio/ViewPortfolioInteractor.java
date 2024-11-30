package usecases.view_portfolio;

import entities.Portfolio;

import java.util.*;

public class ViewPortfolioInteractor implements ViewPortfolioInputBoundary{
    private final ViewPortfolioOutputBoundary viewPortfolioPresenter;
    private final ViewPortfolioDataAccessInterface viewPortfolioDataAccessObject;
    public ViewPortfolioInteractor(ViewPortfolioOutputBoundary presenter,
                                   ViewPortfolioDataAccessInterface dataAccessObject) {
        this.viewPortfolioPresenter = presenter;
        this.viewPortfolioDataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {
        Portfolio currentPortfolio = viewPortfolioDataAccessObject.getCurrentPortfolio();
        Set<String> symbols = currentPortfolio.getStockSymbols();
        Map<String, String> symbolToName = viewPortfolioDataAccessObject.getSymbolToNameMap();
        Map<String, String> nameToSymbol = viewPortfolioDataAccessObject.getNameToSymbolMap();
        Map<String, Double> symbolToCurPrice = viewPortfolioDataAccessObject.getSymbolToCurrentPrice();
        Set<String> names = new HashSet<>();
        for (String sym: symbols) {
            names.add(symbolToName.get(sym));
        }
        List<String> nameList = new ArrayList<>(names);
        Collections.sort(nameList);
        List<String> stockList = new ArrayList<>();
        List<Integer> sharesList = new ArrayList<>();
        List<Double> averageBuyingPriceList = new ArrayList<>();
        List<Double> valuePerShareList = new ArrayList<>();
        for (String name: nameList) {
            stockList.add(name + " (" + nameToSymbol.get(name) + ")");
            sharesList.add(currentPortfolio.getShares(nameToSymbol.get(name)));
            averageBuyingPriceList.add(currentPortfolio.getAveragePrice(nameToSymbol.get(name)));
            valuePerShareList.add(symbolToCurPrice.get(nameToSymbol.get(name)));
        }
        viewPortfolioPresenter.prepareView(new ViewPortfolioOutputData(stockList,
                sharesList,
                averageBuyingPriceList,
                valuePerShareList));
    }
}
