package usecases.revenue_prediction;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.models.Model;
import java.util.Map;

/**
 * Implements the revenue prediction use case by coordinating between the data access layer,
 * prediction models, and output boundary. Supports multiple prediction models for different intervals.
 */
public class RevenuePredictionInteractor implements RevenuePredictionInputBoundary {
    private final RevenuePredictionOutputBoundary revenuePredictionPresenter;
    private final LocalDataAccessInterface dataAccessObject;
    private final Map<String, Model> predictionModels;

    /**
     * Constructs a RevenuePredictionInteractor with necessary dependencies.
     *
     * @param presenter the output boundary for presenting prediction results
     * @param dataAccessObject data access interface for portfolio information
     * @param predictionModels map of model names to their implementations
     */
    public RevenuePredictionInteractor(
            RevenuePredictionOutputBoundary presenter,
            LocalDataAccessInterface dataAccessObject,
            Map<String, Model> predictionModels) {
        this.revenuePredictionPresenter = presenter;
        this.dataAccessObject = dataAccessObject;
        this.predictionModels = predictionModels;
    }

    @Override
    public void execute(RevenuePredictionInputData revenuePredictionInputData) {
        try {
            Portfolio portfolio = dataAccessObject.getCurrentPortfolio();

            if (portfolio.getStockSymbols().isEmpty()) {
                revenuePredictionPresenter.prepareFailView("Portfolio is empty. Please add stocks before predicting revenue.");
                return;
            }

            if (revenuePredictionInputData.getIntervalLength() <= 0) {
                revenuePredictionPresenter.prepareFailView("Interval length must be positive.");
                return;
            }

            Model selectedModel = getAppropriateModel(revenuePredictionInputData.getIntervalName());

            if (selectedModel == null) {
                revenuePredictionPresenter.prepareFailView(
                        "No prediction model available for interval type: " + revenuePredictionInputData.getIntervalName());
                return;
            }

            double predictedRevenue = makePrediction(
                    selectedModel,
                    portfolio,
                    revenuePredictionInputData.getIntervalLength(),
                    revenuePredictionInputData.getIntervalName()
            );

            // Format with two decimal places
            String formattedRevenue = String.format("%.2f", predictedRevenue);

            RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(
                    formattedRevenue,
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
     * Selects the appropriate prediction model based on the interval type.
     *
     * @param intervalName the type of interval for prediction
     * @return the appropriate Model implementation, or null if none available
     */
    private Model getAppropriateModel(String intervalName) {
        return predictionModels.get(intervalName.toLowerCase());
    }

    /**
     * Makes a revenue prediction using the selected model and parameters.
     *
     * @param model the prediction model to use
     * @param portfolio the portfolio to predict revenue for
     * @param intervalLength the length of the prediction interval
     * @param intervalName the type of interval
     * @return predicted revenue value
     * @throws IllegalArgumentException if interval type is invalid
     */
    private double makePrediction(Model model, Portfolio portfolio, int intervalLength, String intervalName) {
        // Check if intervalName is valid
        switch (intervalName.toLowerCase()) {
            case "day", "week", "intraday" -> {
                // Proceed with prediction logic
                return model.predict(portfolio, intervalLength, intervalName.toLowerCase());
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported interval type: " + intervalName + ". Use 'day', 'week', or 'intraday'.");
        }
    }
}