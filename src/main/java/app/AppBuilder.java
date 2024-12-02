package app;

import data_access.*;
import interface_adapters.ViewManagerModel;
import interface_adapters.add_stock.AddStockController;
import interface_adapters.add_stock.AddStockPresenter;
import interface_adapters.add_stock.AddStockViewModel;
import interface_adapters.remove_stock.RemoveStockController;
import interface_adapters.remove_stock.RemoveStockPresenter;
import interface_adapters.remove_stock.RemoveStockViewModel;
import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllPresenter;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.revenue_prediction.RevenuePredictionController;
import interface_adapters.revenue_prediction.RevenuePredictionPresenter;
import interface_adapters.revenue_prediction.RevenuePredictionViewModel;
import interface_adapters.view_portfolio.ViewPortfolioController;
import interface_adapters.view_portfolio.ViewPortfolioPresenter;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;
import usecases.add_stock.AddStockInputBoundary;
import usecases.add_stock.AddStockInteractor;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.add_stock.AddStockDataAccessInterface;
import usecases.add_stock.AddStockOutputBoundary;
import usecases.predict_models.PredictAvgModel;
import usecases.predict_models.PredictModel;
import usecases.remove_stock.RemoveStockInputBoundary;
import usecases.remove_stock.RemoveStockInteractor;
import usecases.remove_stock.RemoveStockOutputBoundary;
import usecases.reset_portfolio.ClearAllDataAccessInterface;
import usecases.reset_portfolio.ClearAllInputBoundary;
import usecases.reset_portfolio.ClearAllInteractor;
import usecases.reset_portfolio.ClearAllOutputBoundary;
import usecases.revenue_prediction.RevenuePredictionInputBoundary;
import usecases.revenue_prediction.RevenuePredictionInteractor;
import usecases.revenue_prediction.RevenuePredictionOutputBoundary;
import usecases.view_portfolio.ViewPortfolioDataAccessInterface;
import usecases.view_portfolio.ViewPortfolioInputBoundary;
import usecases.view_portfolio.ViewPortfolioInteractor;
import usecases.view_portfolio.ViewPortfolioOutputBoundary;
import views.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel;
    private final ViewManagerModel viewManagerModel;
    private final ViewManager viewManager;
    private final LocalDataAccessInterface localDataAccessObject;
    private final OnlineDataAccessInterface onlineDataAccessObject;
    private final ClearAllViewModel clearAllViewModel;
    private final HomePageView homePageView;
    private ViewPortfolioViewModel viewPortfolioViewModel;
    private ViewPortfolioView viewPortfolioView;
    private AddStockViewModel addStockViewModel;
    private AddStockView addStockView;
    private RemoveStockViewModel removeStockViewModel;
    private RemoveStockView removeStockView;
    private RevenuePredictionViewModel revenuePredictionViewModel;
    private RevenuePredictionView revenuePredictionView;

    public AppBuilder() {
        cardPanel = new JPanel(new CardLayout());
        viewManagerModel = new ViewManagerModel();
        viewManager = new ViewManager(
                cardPanel,
                (CardLayout) cardPanel.getLayout(),
                viewManagerModel);
        localDataAccessObject = new LocalDataAccessObject();
        onlineDataAccessObject = new MockOnlineDataAccessObject();
        clearAllViewModel = new ClearAllViewModel();
        homePageView = new HomePageView(clearAllViewModel);
        cardPanel.add(homePageView, homePageView.getViewName());
    }

    public AppBuilder addViewPortfolioView() {
        viewPortfolioViewModel = new ViewPortfolioViewModel();
        viewPortfolioView = new ViewPortfolioView(viewPortfolioViewModel);
        cardPanel.add(viewPortfolioView, viewPortfolioView.getViewName());
        return this;
    }

    public AppBuilder addAddStockView() {
        addStockViewModel = new AddStockViewModel();
        addStockView = new AddStockView(addStockViewModel);
        cardPanel.add(addStockView, addStockView.getViewName());
        return this;
    }

    public AppBuilder addRemoveStockView() {
        removeStockViewModel = new RemoveStockViewModel();
        removeStockView = new RemoveStockView(removeStockViewModel);
        cardPanel.add(removeStockView, removeStockView.getViewName());
        return this;
    }

    public AppBuilder addRevenuePredictionView() {
        revenuePredictionViewModel = new RevenuePredictionViewModel();
        revenuePredictionView = new RevenuePredictionView(revenuePredictionViewModel, viewManagerModel);
        cardPanel.add(revenuePredictionView, revenuePredictionView.getViewName());
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

    public AppBuilder addAddStockUseCase() {
        AddStockOutputBoundary buyStockPresenter = new AddStockPresenter(
                addStockViewModel,
                clearAllViewModel,
                viewManagerModel);
        AddStockDataAccessInterface buyStockDataAccessObject = new AddStockDataAccessObject(
                localDataAccessObject,
                onlineDataAccessObject);
        AddStockInputBoundary buyStockInteractor = new AddStockInteractor(
                buyStockPresenter,
                buyStockDataAccessObject);
        AddStockController addStockController = new AddStockController(buyStockInteractor);
        addStockView.setBuyStockController(addStockController);
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

    public AppBuilder addRevenuePredictionUseCase() {
        RevenuePredictionOutputBoundary revenuePredictionPresenter = new RevenuePredictionPresenter(
                revenuePredictionViewModel,
                viewManagerModel);
        PredictModel predictModel = new PredictAvgModel();
        predictModel.setOnlineDataAccess(onlineDataAccessObject);
        RevenuePredictionInputBoundary revenuePredictionInteractor = new RevenuePredictionInteractor(
                revenuePredictionPresenter,
                localDataAccessObject,
                onlineDataAccessObject,
                predictModel);
        RevenuePredictionController revenuePredictionController = new RevenuePredictionController(revenuePredictionInteractor);
        revenuePredictionView.setRevenuePredictionController(revenuePredictionController);
        return this;
    }

    public AppBuilder addClearAllUseCase() {
        ClearAllOutputBoundary clearAllPresenter = new ClearAllPresenter(
                clearAllViewModel,
                addStockViewModel,
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
