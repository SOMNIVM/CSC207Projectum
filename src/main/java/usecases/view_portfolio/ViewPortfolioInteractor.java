package usecases.view_portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entities.Portfolio;

/**
 * The use case interactor for viewing portfolio.
 */
public class ViewPortfolioInteractor implements ViewPortfolioInputBoundary {
    private final ViewPortfolioOutputBoundary viewPortfolioPresenter;
    private final ViewPortfolioDataAccessInterface viewPortfolioDataAccessObject;

    public ViewPortfolioInteractor(ViewPortfolioOutputBoundary presenter,
                                   ViewPortfolioDataAccessInterface dataAccessObject) {
        this.viewPortfolioPresenter = presenter;
        this.viewPortfolioDataAccessObject = dataAccessObject;
    }

    @Override
    public void execute() {
        final Portfolio currentPortfolio = viewPortfolioDataAccessObject.getCurrentPortfolio();
        final Set<String> symbols = currentPortfolio.getStockSymbols();
        final Map<String, String> symbolToName = viewPortfolioDataAccessObject.getSymbolToNameMap();
        final Map<String, String> nameToSymbol = viewPortfolioDataAccessObject.getNameToSymbolMap();
        final Map<String, Double> symbolToCurPrice = viewPortfolioDataAccessObject.getSymbolToCurrentPrice();
        final Set<String> names = new HashSet<>();
        for (String sym: symbols) {
            names.add(symbolToName.get(sym));
        }
        final List<String> nameList = new ArrayList<>(names);
        Collections.sort(nameList);
        final List<String> stockList = new ArrayList<>();
        final List<Integer> sharesList = new ArrayList<>();
        final List<Double> valuePerShareList = new ArrayList<>();
        for (String name: nameList) {
            stockList.add(name + " (" + nameToSymbol.get(name) + ")");
            sharesList.add(currentPortfolio.getShares(nameToSymbol.get(name)));
            valuePerShareList.add(symbolToCurPrice.get(nameToSymbol.get(name)));
        }
        viewPortfolioPresenter.prepareView(new ViewPortfolioOutputData(stockList,
                sharesList,
                valuePerShareList));
    }

    @Override
    public void switchBack() {
        viewPortfolioPresenter.switchBack();
    }
}
