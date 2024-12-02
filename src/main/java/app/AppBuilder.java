
package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.AddStockDataAccessObject;
import data_access.ClearAllDataAccessObject;
import data_access.LocalDataAccessObject;
import data_access.MockOnlineDataAccessObject;
import data_access.ViewPortfolioDataAccessObject;
import interface_adapters.ViewManagerModel;
import interface_adapters.add_stock.AddStockController;
import interface_adapters.add_stock.AddStockPresenter;
import interface_adapters.add_stock.AddStockViewModel;
import interface_adapters.model_evaluation.ModelEvaluationController;
import interface_adapters.model_evaluation.ModelEvaluationPresenter;
import interface_adapters.model_evaluation.ModelEvaluationViewModel;
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
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.add_stock.AddStockDataAccessInterface;
import usecases.add_stock.AddStockInputBoundary;
import usecases.add_stock.AddStockInteractor;
import usecases.add_stock.AddStockOutputBoundary;
import usecases.model_evaluation.ModelEvaluationInputBoundary;
import usecases.model_evaluation.ModelEvaluationInteractor;
import usecases.model_evaluation.ModelEvaluationOutputBoundary;
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
import views.AddStockView;
import views.HomePageView;
import views.ModelEvaluationView;
import views.RemoveStockView;
import views.RevenuePredictionView;
import views.ViewManager;
import views.ViewPortfolioView;

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
    private ModelEvaluationViewModel modelEvaluationViewModel;
    private ModelEvaluationView modelEvaluationView;

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

    public AppBuilder addModelEvaluationView() {
        modelEvaluationViewModel = new ModelEvaluationViewModel();
        modelEvaluationView = new ModelEvaluationView(modelEvaluationViewModel, viewManagerModel);
        cardPanel.add(modelEvaluationView, modelEvaluationView.getViewName());
        return this;
    }


    /**
     * Wires up the portfolio viewing use case.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addViewPortfolioUseCase() {
        final ViewPortfolioOutputBoundary viewPortfolioPresenter = new ViewPortfolioPresenter(
                viewPortfolioViewModel,
                clearAllViewModel,
                viewManagerModel);
        final ViewPortfolioDataAccessInterface viewPortfolioDataAccessObject = new ViewPortfolioDataAccessObject(
                localDataAccessObject,
                onlineDataAccessObject);
        final ViewPortfolioInputBoundary viewPortfolioInteractor = new ViewPortfolioInteractor(
                viewPortfolioPresenter,
                viewPortfolioDataAccessObject);
        final ViewPortfolioController viewPortfolioController = new ViewPortfolioController(viewPortfolioInteractor);
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
        final AddStockOutputBoundary buyStockPresenter = new AddStockPresenter(
                addStockViewModel,
                clearAllViewModel,
                viewManagerModel);
        final AddStockDataAccessInterface buyStockDataAccessObject = new AddStockDataAccessObject(
                localDataAccessObject,
                onlineDataAccessObject);
        final AddStockInputBoundary buyStockInteractor = new AddStockInteractor(
                buyStockPresenter,
                buyStockDataAccessObject);
        final AddStockController addStockController = new AddStockController(buyStockInteractor);
        addStockView.setBuyStockController(addStockController);
        return this;
    }

    /**
     * Wires up the stock removal use case.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addRemoveStockUseCase() {
        final RemoveStockOutputBoundary removeStockPresenter = new RemoveStockPresenter(
                removeStockViewModel,
                clearAllViewModel,
                viewManagerModel);
        final RemoveStockInputBoundary removeStockInteractor = new RemoveStockInteractor(
                removeStockPresenter,
                localDataAccessObject);
        final RemoveStockController removeStockController = new RemoveStockController(removeStockInteractor);
        removeStockView.setRemoveStockController(removeStockController);
        return this;
    }

    /**
     * Wires up the revenue prediction use case.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addRevenuePredictionUseCase() {
        final RevenuePredictionOutputBoundary revenuePredictionPresenter = new RevenuePredictionPresenter(
                revenuePredictionViewModel,
                viewManagerModel);
        final PredictModel predictModel = new PredictAvgModel();
        predictModel.setOnlineDataAccess(onlineDataAccessObject);
        final RevenuePredictionInputBoundary revenuePredictionInteractor = new RevenuePredictionInteractor(
                revenuePredictionPresenter,
                localDataAccessObject,
                onlineDataAccessObject,
                predictModel);
        final RevenuePredictionController revenuePredictionController =
                new RevenuePredictionController(revenuePredictionInteractor);
        revenuePredictionView.setRevenuePredictionController(revenuePredictionController);
        return this;
    }

    /**
     * Adds a model evaluation case to the application.
     * This method initializes the necessary components for model evaluation,
     * including the presenter, interactor, and controller, and sets the controller
     * for the model evaluation view.
     *
     * @return the current instance of AppBuilder for method chaining
     */
    public AppBuilder addModelEvaluationCase() {
        final ModelEvaluationOutputBoundary modelEvaluationPresenter = new ModelEvaluationPresenter(
                modelEvaluationViewModel,
                viewManagerModel);
        final ModelEvaluationInputBoundary modelEvaluationInteractor = new ModelEvaluationInteractor(
                onlineDataAccessObject,
                localDataAccessObject,
                modelEvaluationPresenter);

        final ModelEvaluationController modelEvaluationController =
                new ModelEvaluationController(modelEvaluationInteractor);
        modelEvaluationView.setModelEvaluationController(modelEvaluationController);
        return this;
    }

    /**
     * Wires up the portfolio clearing use case.
     *
     * @return this builder instance for method chaining
     */
    public AppBuilder addClearAllUseCase() {
        final ClearAllOutputBoundary clearAllPresenter = new ClearAllPresenter(
                clearAllViewModel,
                addStockViewModel,
                removeStockViewModel,
                revenuePredictionViewModel,
                modelEvaluationViewModel,
                viewManagerModel);
        final ClearAllDataAccessInterface clearAllDataAccessObject =
                new ClearAllDataAccessObject(localDataAccessObject);
        final ClearAllInputBoundary clearAllInteractor = new ClearAllInteractor(clearAllPresenter,
                clearAllDataAccessObject);
        final ClearAllController clearAllController = new ClearAllController(clearAllInteractor);
        homePageView.setClearAllController(clearAllController);
        return this;
    }

    /**
     * Builds and returns the complete application frame.
     *
     * @return the configured JFrame containing the application
     */
    public JFrame build() {
        final JFrame app = new JFrame("Portfolio Management System");
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.add(cardPanel);
        viewManagerModel.getState().setCurViewName(homePageView.getViewName());
        viewManagerModel.firePropertyChange();
        return app;
    }
}
