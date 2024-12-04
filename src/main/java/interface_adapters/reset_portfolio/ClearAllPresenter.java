package interface_adapters.reset_portfolio;

import interface_adapters.ViewManagerModel;
import interface_adapters.add_stock.AddStockViewModel;
import interface_adapters.model_evaluation.ModelEvaluationViewModel;
import interface_adapters.remove_stock.RemoveStockViewModel;
import interface_adapters.revenue_prediction.RevenuePredictionViewModel;
import usecases.reset_portfolio.ClearAllOutputBoundary;

/**
 * Presenter class for the Clear All feature. Handles view transitions and updates
 * for portfolio clearing operations.
 */
public class ClearAllPresenter implements ClearAllOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ClearAllViewModel clearAllViewModel;
    private final AddStockViewModel addStockViewModel;
    private final RemoveStockViewModel removeStockViewModel;
    private final RevenuePredictionViewModel revenuePredictionViewModel;
    private final ModelEvaluationViewModel modelEvaluationViewModel;

    /**
     * Constructs a ClearAllPresenter with required view models.
     * @param clearAllModel The view model for the clear all feature
     * @param buyStockModel The view model for adding stocks
     * @param removeStockModel The view model for removing stocks
     * @param revenuePredictionModel The view model for revenue prediction
     * @param modelEvaluationViewModel The view model to evaluating model.
     * @param managerModel The view manager model
     */
    public ClearAllPresenter(ClearAllViewModel clearAllModel,
                             AddStockViewModel buyStockModel,
                             RemoveStockViewModel removeStockModel,
                             RevenuePredictionViewModel revenuePredictionModel,
                             ModelEvaluationViewModel modelEvaluationViewModel,
                             ViewManagerModel managerModel) {
        this.viewManagerModel = managerModel;
        this.clearAllViewModel = clearAllModel;
        this.addStockViewModel = buyStockModel;
        this.removeStockViewModel = removeStockModel;
        this.revenuePredictionViewModel = revenuePredictionModel;
        this.modelEvaluationViewModel = modelEvaluationViewModel;
    }

    /**
     * Prepares the view after clearing the portfolio.
     */
    @Override
    public void prepareClearedView() {
        clearAllViewModel.getState().clear();
        clearAllViewModel.firePropertyChange();
    }

    /**
     * Switches to the buy stock view.
     */
    @Override
    public void switchToBuyStock() {
        if (addStockViewModel != null) {
            viewManagerModel.getState().setCurViewName(addStockViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }

    /**
     * Switches to the remove stock view.
     */
    @Override
    public void switchToRemoveStock() {
        if (removeStockViewModel != null) {
            viewManagerModel.getState().setCurViewName(removeStockViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }

    /**
     * Switches to the revenue prediction view.
     */
    @Override
    public void switchToPredictRevenue() {
        if (revenuePredictionViewModel != null) {
            viewManagerModel.getState().setCurViewName(revenuePredictionViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }

    /**
     * Switches to the model evaluation view.
     */
    @Override
    public void switchToBacktest() {
        if (modelEvaluationViewModel != null) {
            viewManagerModel.getState().setCurViewName(modelEvaluationViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
    }
}
