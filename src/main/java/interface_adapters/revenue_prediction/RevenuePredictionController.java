package interface_adapters.revenue_prediction;

import usecases.revenue_prediction.RevenuePredictionInputBoundary;
import usecases.revenue_prediction.RevenuePredictionInputData;

/**
 * The controller for the revenue prediction use case.
 */
public class RevenuePredictionController {
    private final RevenuePredictionInputBoundary revenuePredictionInteractor;

    public RevenuePredictionController(RevenuePredictionInputBoundary interactor) {
        this.revenuePredictionInteractor = interactor;
    }

    /**
     * Execute the revenue prediction use case.
     * @param model The name of the model to use.
     * @param intervalLength The time interval (in minutes) at which the time series data of stock price is sampled.
     * @param intervalName The string representing the time interval at which the time series data are sampled.
     *                     This param must be among "intraday", "day", and "week".
     */
    public void execute(String model, int intervalLength, String intervalName) {
        final RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                model, intervalLength, intervalName
        );
        revenuePredictionInteractor.execute(inputData);
    }

    public void switchBack() {
        revenuePredictionInteractor.switchBack();
    }
}

