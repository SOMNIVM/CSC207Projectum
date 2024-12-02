package interface_adapters.revenue_prediction;

import usecases.revenue_prediction.RevenuePredictionInputBoundary;
import usecases.revenue_prediction.RevenuePredictionInputData;
import usecases.predict_models.PredictModel;

public class RevenuePredictionController {
    private final RevenuePredictionInputBoundary revenuePredictionInteractor;

    public RevenuePredictionController(RevenuePredictionInputBoundary interactor) {
        this.revenuePredictionInteractor = interactor;
    }

    public void execute(String model, int intervalLength, String intervalName) {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                model, intervalLength, intervalName
        );
        revenuePredictionInteractor.execute(inputData);
    }
}

