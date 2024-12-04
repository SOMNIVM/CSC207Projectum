package usecases.revenue_prediction;

import entities.Portfolio;
import usecases.LocalDataAccessInterface;
import usecases.predict_models.PredictAvgModel;
import usecases.predict_models.PredictModel;

/**
 * Implements the revenue prediction use case by coordinating between the data access layer,
 * prediction models, and output boundary. Handles both point estimates and confidence intervals
 * for revenue predictions.
 */
public class RevenuePredictionInteractor implements RevenuePredictionInputBoundary {
    private static final double DEFAULT_CONFIDENCE_LEVEL = 0.95;
    private final RevenuePredictionOutputBoundary revenuePredictionPresenter;
    private final LocalDataAccessInterface localDataAccessObject;
    private PredictModel predictModel;

    /**
     * Constructs a RevenuePredictionInteractor with necessary dependencies.
     *
     * @param presenter The output boundary for presenting prediction results
     * @param localDataAccess Data access interface for portfolio information
     * @param predictModel The model to use for predictions
     */
    public RevenuePredictionInteractor(
            RevenuePredictionOutputBoundary presenter,
            LocalDataAccessInterface localDataAccess,
            PredictModel predictModel) {
        this.revenuePredictionPresenter = presenter;
        this.localDataAccessObject = localDataAccess;
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
            // Validate inputs and get portfolio
            final Portfolio portfolio = validateAndGetPortfolio(revenuePredictionInputData);

            // Get prediction and confidence interval
            final PredictionResult result = getPredictionWithInterval(portfolio, revenuePredictionInputData);

            // Create output data with prediction results
            final RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(
                    result.pointEstimate,
                    result.lowerBound,
                    result.upperBound,
                    revenuePredictionInputData.getIntervalLength(),
                    revenuePredictionInputData.getIntervalName(),
                    DEFAULT_CONFIDENCE_LEVEL
            );

            revenuePredictionPresenter.prepareSuccessView(outputData);

        }
        catch (IllegalArgumentException ex) {
            revenuePredictionPresenter.prepareFailView("Invalid prediction parameters: " + ex.getMessage());
        }
        catch (IllegalStateException ex) {
            revenuePredictionPresenter.prepareFailView(
                    "An error occurred while predicting revenue: " + ex.getMessage());
        }
    }

    /**
     * Validates input parameters and retrieves the portfolio.
     *
     * @param inputData the input data to validate
     * @return the validated portfolio
     * @throws IllegalArgumentException if validation fails
     */
    private Portfolio validateAndGetPortfolio(RevenuePredictionInputData inputData) {
        final Portfolio portfolio = localDataAccessObject.getCurrentPortfolio();

        if (portfolio.getStockSymbols().isEmpty()) {
            throw new IllegalArgumentException("Portfolio is empty. Please add stocks before predicting revenue.");
        }

        if (inputData.getIntervalLength() <= 0) {
            throw new IllegalArgumentException("Interval length must be positive.");
        }

        final String intervalType = inputData.getIntervalName().toLowerCase();
        if (!isValidIntervalType(intervalType)) {
            throw new IllegalArgumentException(
                    "Invalid interval type. Please use 'intraday', 'day', or 'week'.");
        }

        return portfolio;
    }

    /**
     * Gets prediction and confidence interval from the model.
     *
     * @param portfolio the portfolio to predict for
     * @param inputData the input parameters
     * @return PredictionResult containing point estimate and confidence bounds
     */
    private PredictionResult getPredictionWithInterval(Portfolio portfolio, RevenuePredictionInputData inputData) {
        final PredictionResult result;
        final double pointEstimate = predictModel.predictRevenue(
                portfolio,
                inputData.getIntervalLength(),
                inputData.getIntervalName().toLowerCase()
        );

        // If using PredictAvgModel, get confidence interval
        if (predictModel instanceof PredictAvgModel avgModel) {
            final double[] intervalResults = avgModel.predictRevenueWithInterval(
                    portfolio,
                    inputData.getIntervalLength(),
                    inputData.getIntervalName().toLowerCase()
            );
            result = new PredictionResult(intervalResults[0], intervalResults[1], intervalResults[2]);
        }
        else {
            result = new PredictionResult(pointEstimate, pointEstimate, pointEstimate);
            // For other models, use point estimate with no interval
        }
        return result;
    }

    /**
     * Validates if the given interval type is supported.
     *
     * @param intervalType the interval type to validate
     * @return true if the interval type is valid, false otherwise
     */
    private boolean isValidIntervalType(String intervalType) {
        return "intraday".equals(intervalType)
                || "day".equals(intervalType)
                || "week".equals(intervalType);
    }

    /**
     * Record class to hold prediction results including confidence interval.
     * @param pointEstimate the pointEstimate of the predicted value.
     * @param lowerBound the lower bound of the 95% confidence interval.
     * @param upperBound the upper bound of the 95% confidence interval.
     */
    private record PredictionResult(double pointEstimate, double lowerBound, double upperBound) {

    }
}
