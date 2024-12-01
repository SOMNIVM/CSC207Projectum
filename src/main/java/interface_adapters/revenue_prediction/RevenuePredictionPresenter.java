package interface_adapters.revenue_prediction;


import interface_adapters.ViewManagerModel;
import usecases.revenue_prediction.RevenuePredictionOutputBoundary;
import usecases.revenue_prediction.RevenuePredictionOutputData;


/**
 * Presenter class for the revenue prediction feature that prepares data for display in the view.
 * This class implements the RevenuePredictionOutputBoundary and is responsible for transforming
 * use case output data into a format suitable for display, managing view state, and handling
 * both successful predictions and error cases.
 */
public class RevenuePredictionPresenter implements RevenuePredictionOutputBoundary {
    private final RevenuePredictionViewModel revenuePredictionViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a new RevenuePredictionPresenter.
     *
     * @param viewModel     The view model for revenue prediction that manages the state and data for the view
     * @param managerModel  The view manager model that handles view navigation and transitions
     */
    public RevenuePredictionPresenter(RevenuePredictionViewModel viewModel, ViewManagerModel managerModel) {
        this.revenuePredictionViewModel = viewModel;
        this.viewManagerModel = managerModel;
    }

    /**
     * Prepares the success view when revenue prediction is successful.
     * Updates the view model state with prediction results and triggers view updates.
     *
     * @param outputData  The output data containing prediction results including predicted revenue,
     *                    interval name, and interval length
     */
    @Override
    public void prepareSuccessView(RevenuePredictionOutputData outputData) {
        RevenuePredictionState state = revenuePredictionViewModel.getState();
        state.setAsValid();
        state.setPredictedRevenue(outputData.getPredictedRevenue());
        state.setPredictionInterval(outputData.getIntervalName());
        state.setIntervalLength(outputData.getIntervalLength());

        // Update the view with the new state
        viewManagerModel.getState().setCurViewName(revenuePredictionViewModel.getViewName());
        revenuePredictionViewModel.firePropertyChange();
        viewManagerModel.firePropertyChange();
    }

    /**
     * Prepares the failure view when revenue prediction encounters an error.
     * Updates the view model state with an error message and triggers view update.
     *
     * @param error  The error message describing what went wrong during the prediction process
     */
    @Override
    public void prepareFailView(String error) {
        RevenuePredictionState state = revenuePredictionViewModel.getState();
        state.setAsInvalid(error);
        revenuePredictionViewModel.firePropertyChange();
    }
}
