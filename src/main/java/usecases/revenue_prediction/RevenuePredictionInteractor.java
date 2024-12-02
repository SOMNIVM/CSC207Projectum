package usecases.revenue_prediction;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.predict_models.PredictModel;

/**
 * Implements the revenue prediction use case by coordinating between the data access layer,
 * prediction models, and output boundary.
 */
public class RevenuePredictionInteractor implements RevenuePredictionInputBoundary {
    private final RevenuePredictionOutputBoundary revenuePredictionPresenter;
    private final LocalDataAccessInterface localDataAccessObject;
    private final OnlineDataAccessInterface onlineDataAccessObject;
    private PredictModel predictModel;

    /**
     * Constructs a RevenuePredictionInteractor with necessary dependencies.
     *
     * @param presenter The output boundary for presenting prediction results
     * @param localDataAccess Data access interface for portfolio information
     * @param onlineDataAccess Online data access interface for market data
     * @param predictModel The model to use for predictions
     */
    public RevenuePredictionInteractor(
            RevenuePredictionOutputBoundary presenter,
            LocalDataAccessInterface localDataAccess,
            OnlineDataAccessInterface onlineDataAccess,
            PredictModel predictModel) {
        this.revenuePredictionPresenter = presenter;
        this.localDataAccessObject = localDataAccess;
        this.onlineDataAccessObject = onlineDataAccess;
        this.predictModel = predictModel;
    }

    /**
     * Sets the prediction model to be used.
     *
     * @param model The prediction model to use
     */
    public void setPredictModel(PredictModel model) {
        this.predictModel = model;
    }

    @Override
    public void execute(RevenuePredictionInputData revenuePredictionInputData) {
        try {
            Portfolio portfolio = localDataAccessObject.getCurrentPortfolio();

            if (portfolio.getStockSymbols().isEmpty()) {
                revenuePredictionPresenter.prepareFailView("Portfolio is empty. Please add stocks before predicting revenue.");
                return;
            }

            if (revenuePredictionInputData.getIntervalLength() <= 0) {
                revenuePredictionPresenter.prepareFailView("Interval length must be positive.");
                return;
            }

            // Validate interval type
            String intervalType = revenuePredictionInputData.getIntervalName().toLowerCase();
            if (!isValidIntervalType(intervalType)) {
                revenuePredictionPresenter.prepareFailView(
                        "Invalid interval type. Please use 'intraday', 'day', or 'week'.");
                return;
            }

            // Get prediction using the provided model
            double predictedRevenue = predictModel.predict(
                    portfolio,
                    revenuePredictionInputData.getIntervalLength(),
                    intervalType
            );

            RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(
                    predictedRevenue,
                    revenuePredictionInputData.getIntervalLength(),
                    revenuePredictionInputData.getIntervalName()
            );

            revenuePredictionPresenter.prepareSuccessView(outputData);

        } catch (IllegalArgumentException e) {
            revenuePredictionPresenter.prepareFailView("Invalid prediction parameters: " + e.getMessage());
        } catch (Exception e) {
            revenuePredictionPresenter.prepareFailView(
                    "An error occurred while predicting revenue: " + e.getMessage());
        }
    }

    /**
     * Validates if the given interval type is supported.
     *
     * @param intervalType the interval type to validate
     * @return true if the interval type is valid, false otherwise
     */
    private boolean isValidIntervalType(String intervalType) {
        return intervalType.equals("intraday") ||
                intervalType.equals("day") ||
                intervalType.equals("week");
    }
}