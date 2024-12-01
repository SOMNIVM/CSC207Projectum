package app;

import data_access.*;
import interface_adapters.ViewManagerModel;
import interface_adapters.buy_stock.BuyStockController;
import interface_adapters.buy_stock.AddStockPresenter;
import interface_adapters.buy_stock.BuyStockViewModel;
import interface_adapters.remove_stock.RemoveStockController;
import interface_adapters.remove_stock.RemoveStockPresenter;
import interface_adapters.remove_stock.RemoveStockViewModel;
import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllPresenter;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.view_portfolio.ViewPortfolioController;
import interface_adapters.view_portfolio.ViewPortfolioPresenter;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;
import usecases.AddStock.AddStockInputBoundary;
import usecases.AddStock.AddStockInteractor;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.AddStock.AddStockDataAccessInterface;
import usecases.AddStock.AddStockOutputBoundary;
import usecases.remove_stock.RemoveStockInputBoundary;
import usecases.remove_stock.RemoveStockInteractor;
import usecases.remove_stock.RemoveStockOutputBoundary;
import usecases.reset_portfolio.ClearAllDataAccessInterface;
import usecases.reset_portfolio.ClearAllInputBoundary;
import usecases.reset_portfolio.ClearAllInteractor;
import usecases.reset_portfolio.ClearAllOutputBoundary;
import usecases.view_portfolio.ViewPortfolioDataAccessInterface;
import usecases.view_portfolio.ViewPortfolioInputBoundary;
import usecases.view_portfolio.ViewPortfolioInteractor;
import usecases.view_portfolio.ViewPortfolioOutputBoundary;
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
    private BuyStockViewModel AddStockViewModel;
    private AddStockView addStockView;
    private RemoveStockViewModel removeStockViewModel;
    private RemoveStockView removeStockView;
    public AppBuilder addViewPortfolioView() {
        viewPortfolioViewModel = new ViewPortfolioViewModel();
        viewPortfolioView = new ViewPortfolioView(viewPortfolioViewModel);
        cardPanel.add(viewPortfolioView, viewPortfolioView.getViewName());
        return this;
    }
    public AppBuilder addBuyStockView() {
        AddStockViewModel = new BuyStockViewModel();
        addStockView = new AddStockView(AddStockViewModel);
        cardPanel.add(addStockView, addStockView.getViewName());
        return this;
    }
    public AppBuilder addRemoveStockView() {
        removeStockViewModel = new RemoveStockViewModel();
        removeStockView = new RemoveStockView(removeStockViewModel);
        cardPanel.add(removeStockView, removeStockView.getViewName());
        return this;
    }
    public AppBuilder addViewPortfolioUseCase() {
        ViewPortfolioOutputBoundary viewPortfolioPresenter = new ViewPortfolioPresenter(
                viewPortfolioViewModel,
                clearAllViewModel,
                viewManagerModel);
        ViewPortfolioDataAccessInterface viewPortfolioDataAccessObject = new ViewPortfolioDataAccessObject(
                localDataAccessObject,
                onlineDataAccessObject);
        ViewPortfolioInputBoundary viewPortfolioInteractor = new ViewPortfolioInteractor(
                viewPortfolioPresenter,
                viewPortfolioDataAccessObject);
        ViewPortfolioController viewPortfolioController = new ViewPortfolioController(viewPortfolioInteractor);
        homePageView.setViewPortfolioController(viewPortfolioController);
        viewPortfolioView.setViewPortfolioController(viewPortfolioController);
        return this;
    }
    public AppBuilder addBuyStockUseCase() {
        AddStockOutputBoundary buyStockPresenter = new AddStockPresenter(
                AddStockViewModel,
                clearAllViewModel,
                viewManagerModel);
        AddStockDataAccessInterface buyStockDataAccessObject = new AddStockDataAccessObject(
                localDataAccessObject,
                onlineDataAccessObject);
        AddStockInputBoundary buyStockInteractor = new AddStockInteractor(
                buyStockPresenter,
                buyStockDataAccessObject);
        BuyStockController buyStockController = new BuyStockController(buyStockInteractor);
        addStockView.setBuyStockController(buyStockController);
        return this;
    }
    public AppBuilder addRemoveStockUseCase() {
        RemoveStockOutputBoundary removeStockPresenter = new RemoveStockPresenter(
                removeStockViewModel,
                clearAllViewModel,
                viewManagerModel);
        RemoveStockInputBoundary removeStockInteractor = new RemoveStockInteractor(
                removeStockPresenter,
                localDataAccessObject);
        RemoveStockController removeStockController = new RemoveStockController(removeStockInteractor);
        removeStockView.setRemoveStockController(removeStockController);
        return this;
    }
    public AppBuilder addClearAllUseCase() {
        ClearAllOutputBoundary clearAllPresenter = new ClearAllPresenter(
                clearAllViewModel,
                AddStockViewModel,
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
