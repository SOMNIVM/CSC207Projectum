package interface_adapters.revenue_prediction;

import interface_adapters.ViewManagerModel;
import usecases.revenue_prediction.RevenuePredictionOutputBoundary;
import usecases.revenue_prediction.RevenuePredictionOutputData;

/**
 * Presenter class for the revenue prediction feature that implements the RevenuePredictionOutputBoundary.
 * This class is responsible for preparing and managing the view state based on the results of revenue predictions.
 * It handles both successful predictions and error cases, updating the view model and managing view transitions.
 */
public class RevenuePredictionPresenter implements RevenuePredictionOutputBoundary {
    private final RevenuePredictionViewModel revenuePredictionViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a new RevenuePredictionPresenter with the specified view models.
     *
     * @param viewModel The view model for revenue prediction that manages the state and data for the view
     * @param managerModel The view manager model that handles view navigation and transitions
     */
    public RevenuePredictionPresenter(RevenuePredictionViewModel viewModel, ViewManagerModel managerModel) {
        this.revenuePredictionViewModel = viewModel;
        this.viewManagerModel = managerModel;
    }

    /**
     * Prepares the success view with prediction results and confidence intervals.
     * Updates the view model state with prediction results and triggers view updates.
     * Also ensures the revenue prediction view is displayed to show the results.
     *
     * @param outputData The output data containing prediction results including predicted revenue,
     *                   confidence intervals, and other prediction metadata
     */
    @Override
    public void prepareSuccessView(RevenuePredictionOutputData outputData) {
        final RevenuePredictionState state = revenuePredictionViewModel.getState();
        state.setAsValid();
        state.setPredictedRevenue(outputData.getPredictedRevenue());
        state.setLowerBound(outputData.getLowerBound());
        state.setUpperBound(outputData.getUpperBound());
        state.setConfidenceLevel(outputData.getConfidenceLevel());
        state.setPredictionInterval(outputData.getIntervalName());
        state.setIntervalLength(outputData.getIntervalLength());

        // Show the revenue prediction view
        viewManagerModel.getState().setCurViewName(revenuePredictionViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        revenuePredictionViewModel.firePropertyChange();
    }

    /**
     * Prepares the failure view when revenue prediction encounters an error.
     * Updates the view model state with an error message and ensures the revenue
     * prediction view is displayed to show the error message.
     *
     * @param error The error message describing what went wrong during the prediction process
     */
    @Override
    public void prepareFailView(String error) {
        final RevenuePredictionState state = revenuePredictionViewModel.getState();
        state.setAsInvalid(error);

        // Show the revenue prediction view even in case of error
        viewManagerModel.getState().setCurViewName(revenuePredictionViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        revenuePredictionViewModel.firePropertyChange();
    }

    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName("homepage");
        viewManagerModel.firePropertyChange();
        revenuePredictionViewModel.getState().reset();
    }
}
