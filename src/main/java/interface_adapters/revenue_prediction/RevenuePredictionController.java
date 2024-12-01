package interface_adapters.revenue_prediction;

import usecases.revenue_prediction.RevenuePredictionInputBoundary;
import usecases.revenue_prediction.RevenuePredictionInputData;

public class RevenuePredictionController {
    private final RevenuePredictionInputBoundary revenuePredictionUseCaseInteractor;

    public RevenuePredictionController(RevenuePredictionInputBoundary interactor) {
        this.revenuePredictionUseCaseInteractor = interactor;
    }

    /**
     * Execute the revenue prediction use case with the specified parameters.
     *
     * @param intervalLength The interval length (e.g., number of minutes for "intraday")
     * @param intervalName   The type of interval: "day", "week", or "intraday"
     */
    public void execute(int intervalLength, String intervalName) {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(intervalLength, intervalName);
        revenuePredictionUseCaseInteractor.execute(inputData);
    }
}

