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
import usecases.add_stock.*;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.predict_models.PredictAvgModel;
import usecases.predict_models.PredictModel;
import usecases.remove_stock.*;
import usecases.reset_portfolio.*;
import usecases.revenue_prediction.*;
import usecases.view_portfolio.*;
import views.*;

import javax.swing.*;
import java.awt.*;

/**
 * Builder class for constructing the application's UI and wiring together its components.
 * Implements the Builder pattern to create and configure the application step by step.
 */
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

    /**
     * Constructs a new AppBuilder and initializes the core components.
     */
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

    /**
     * Adds the portfolio viewing functionality to the application.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addViewPortfolioView() {
        viewPortfolioViewModel = new ViewPortfolioViewModel();
        viewPortfolioView = new ViewPortfolioView(viewPortfolioViewModel);
        cardPanel.add(viewPortfolioView, viewPortfolioView.getViewName());
        return this;
    }

    /**
     * Adds the stock addition functionality to the application.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addAddStockView() {
        addStockViewModel = new AddStockViewModel();
        addStockView = new AddStockView(addStockViewModel);
        cardPanel.add(addStockView, addStockView.getViewName());
        return this;
    }

    /**
     * Adds the stock removal functionality to the application.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addRemoveStockView() {
        removeStockViewModel = new RemoveStockViewModel();
        removeStockView = new RemoveStockView(removeStockViewModel);
        cardPanel.add(removeStockView, removeStockView.getViewName());
        return this;
    }

    /**
     * Adds the revenue prediction functionality to the application.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addRevenuePredictionView() {
        revenuePredictionViewModel = new RevenuePredictionViewModel();
        revenuePredictionView = new RevenuePredictionView(revenuePredictionViewModel, viewManagerModel);
        cardPanel.add(revenuePredictionView, revenuePredictionView.getViewName());
        return this;
    }

    /**
     * Wires up the portfolio viewing use case.
     *
     * @return this builder instance for method chaining
     */
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

    /**
     * Wires up the stock addition use case.
     *
     * @return this builder instance for method chaining
     */
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

    /**
     * Wires up the stock removal use case.
     *
     * @return this builder instance for method chaining
     */
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

    /**
     * Wires up the revenue prediction use case.
     *
     * @return this builder instance for method chaining
     */
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

    /**
     * Wires up the portfolio clearing use case.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addClearAllUseCase() {
        ClearAllOutputBoundary clearAllPresenter = new ClearAllPresenter(
                clearAllViewModel,
                addStockViewModel,
                removeStockViewModel,
                revenuePredictionViewModel,  // Added RevenuePredictionViewModel
                viewManagerModel);
        ClearAllDataAccessInterface clearAllDataAccessObject = new ClearAllDataAccessObject(localDataAccessObject);
        ClearAllInputBoundary clearAllInteractor = new ClearAllInteractor(clearAllPresenter,
                clearAllDataAccessObject);
        ClearAllController clearAllController = new ClearAllController(clearAllInteractor);
        homePageView.setClearAllController(clearAllController);
        return this;
    }

    /**
     * Builds and returns the complete application frame.
     *
     * @return the configured JFrame containing the application
     */
    public JFrame build() {
        JFrame app = new JFrame("Portfolio Management System");
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.add(cardPanel);
        viewManagerModel.getState().setCurViewName(homePageView.getViewName());
        viewManagerModel.firePropertyChange();
        return app;
    }
}