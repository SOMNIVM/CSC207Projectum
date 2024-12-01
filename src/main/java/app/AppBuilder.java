package app;

import data_access.AlphaVantageDataAccessObject;
import data_access.ClearAllDataAccessObject;
import data_access.LocalDataAccessObject;
import interface_adapters.ViewManagerModel;
import interface_adapters.buy_stock.BuyStockViewModel;
import interface_adapters.remove_stock.RemoveStockViewModel;
import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllPresenter;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.reset_portfolio.ClearAllDataAccessInterface;
import usecases.reset_portfolio.ClearAllInputBoundary;
import usecases.reset_portfolio.ClearAllInteractor;
import usecases.reset_portfolio.ClearAllOutputBoundary;
import views.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel,
            (CardLayout) cardPanel.getLayout(),
            viewManagerModel);
    private final LocalDataAccessInterface localDataAccessObject = new LocalDataAccessObject();
    private final OnlineDataAccessInterface onlineDataAccessObject = new AlphaVantageDataAccessObject(Config.API_KEY);
    private final ClearAllViewModel clearAllViewModel = new ClearAllViewModel();
    private final HomePageView homePageView = new HomePageView(clearAllViewModel);
    private ViewPortfolioViewModel viewPortfolioViewModel;
    private ViewPortfolioView viewPortfolioView;
    private BuyStockViewModel buyStockViewModel;
    private BuyStockView buyStockView;
    private RemoveStockViewModel removeStockViewModel;
    private RemoveStockView removeStockView;

    /**
     * This should be called after all other use cases have been added.
     * @return this AppBuilder.
     */
    public AppBuilder addClearAllUseCase() {
        ClearAllOutputBoundary clearAllPresenter = new ClearAllPresenter(clearAllViewModel,
                buyStockViewModel,
                removeStockViewModel,
                viewManagerModel);
        ClearAllDataAccessInterface clearAllDataAccessObject = new ClearAllDataAccessObject(localDataAccessObject);
        ClearAllInputBoundary clearAllInteractor = new ClearAllInteractor(clearAllPresenter,
                clearAllDataAccessObject);
        ClearAllController clearAllController = new ClearAllController(clearAllInteractor);
        homePageView.setClearAllController(clearAllController);
        return this;
    }
    public JFrame build() {
        JFrame app = new JFrame("app");
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.add(cardPanel);
        viewManagerModel.getState().setCurViewName(homePageView.getViewName());
        viewManagerModel.firePropertyChange();
        return app;
    }
}
