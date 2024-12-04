package usecases.model_evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.models.AbstractModel;

/**
 * ModelEvaluationInteractor class implements ModelEvaluationInputBoundary interface and handles the evaluation
 * of different prediction models for portfolio performance.
 * This class is responsible for:
 * - Calculating various performance metrics for the selected model
 * - Processing portfolio observations
 * - Managing model evaluation data and presenting results
 * The class supports different types of models and can evaluate them using metrics such as:
 * - Mean Squared Error
 * - Mean Absolute Error
 * - Sharpe Ratio
 * - Predicted vs Actual Price comparison
 * @see ModelEvaluationInputBoundary
 * @see ModelEvaluationOutputBoundary
 * @see LocalDataAccessInterface
 * @see Portfolio
 * @see AbstractModel
 */
public class ModelEvaluationInteractor implements ModelEvaluationInputBoundary {
    private static final String INTRADAY_FLAG = "intraday";
    private static final String DAILY_FLAG = "daily";
    private static final String WEEKLY_FLAG = "weekly";
    private final OnlineDataAccessInterface dataAccess;
    private final ModelEvaluationOutputBoundary modelEvaluationPresenter;
    private final LocalDataAccessInterface localDataAccessInterface;

    public ModelEvaluationInteractor(
            OnlineDataAccessInterface dataAccess,   
            LocalDataAccessInterface localDataAccessInterface,
            ModelEvaluationOutputBoundary modelEvaluationPresenter) {
        this.dataAccess = dataAccess;
        this.modelEvaluationPresenter = modelEvaluationPresenter;
        this.localDataAccessInterface = localDataAccessInterface;

    }

    @Override
    public void execute(ModelEvaluationInputData modelEvaluationInputData) {
        try {
            final String frequency = modelEvaluationInputData.getFrequency().toLowerCase();
            final int numOfInterval = setNunOfInterval(modelEvaluationInputData.getFrequency());
            final String modelName = modelEvaluationInputData.getModelType();
            final Portfolio portfolio = validateAndGetPortfolio(modelEvaluationInputData);
            final double[] observations = getPortfolioObservations(portfolio, frequency, numOfInterval);
            final AbstractModel abstractModel = AbstractModel.createModel(modelName, numOfInterval, observations);
            final double meanSquaredError = abstractModel.getMeanSquaredError();
            final double predictedPrice = abstractModel.getPredictedPrice();
            final double meanAbsoluteError = abstractModel.getMeanAbsoluteError();
            final double sharpeRatio = abstractModel.getSharpeRatio();
            final double actualPrice = abstractModel.getActualPrice();
    
            final ModelEvaluationOutputData modelEvaluationOutputData =
                    new ModelEvaluationOutputData(
                            modelName,
                            frequency,
                            numOfInterval,
                            meanSquaredError,
                            meanAbsoluteError,
                            sharpeRatio,
                            predictedPrice,
                            actualPrice
                    );
    
            modelEvaluationPresenter.prepareSuccessView(modelEvaluationOutputData);
            
        }
        catch (IllegalArgumentException | IllegalStateException ex) {
            modelEvaluationPresenter.prepareFailView("Error occur" + ex.getMessage());
        }
    }

    private int setNunOfInterval(String frequency) {
        final int numOfInterval;
        switch (frequency) {
            case INTRADAY_FLAG:
                numOfInterval = Config.INTRADAY_SAMPLE_SIZE;
                break;
            case DAILY_FLAG:
                numOfInterval = Config.DAILY_SAMPLE_SIZE;
                break;
            default:
                numOfInterval = Config.WEEKLY_SAMPLE_SIZE;
        }
        return numOfInterval;
    }

    private double[] getPortfolioObservations(Portfolio portfolio, String frequency, int numOfInterval) {
        final List<Double> localObservations = new ArrayList<>();
        final Map<String, List<Pair<String, Double>>> historicalPrices;
        switch (frequency) {
            case INTRADAY_FLAG:
                historicalPrices = dataAccess.getBulkTimeSeriesIntraDay(
                        portfolio,
                        numOfInterval,
                        Config.INTRADAY_PREDICT_INTERVAL);
                break;
            case DAILY_FLAG:
                historicalPrices = dataAccess.getBulkTimeSeriesDaily(portfolio, numOfInterval);
                break;
            default:
                historicalPrices = dataAccess.getBulkTimeSeriesWeekly(portfolio, numOfInterval);
                break;
        }

        for (int i = 0; i < numOfInterval; i++) {
            double currentValueOfPortfolio = 0;
            for (Map.Entry<String, List<Pair<String, Double>>> entry : historicalPrices.entrySet()) {
                final String stockSymbol = entry.getKey();
                final double currentValueOfStock =
                        portfolio.getShares(stockSymbol) * entry.getValue().get(i).getSecond();
                currentValueOfPortfolio += currentValueOfStock;
            }
            localObservations.add(currentValueOfPortfolio);
        }
        return localObservations.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private Portfolio validateAndGetPortfolio(ModelEvaluationInputData inputData) {
        final Portfolio portfolio = localDataAccessInterface.getCurrentPortfolio();

        if (portfolio.getStockSymbols().isEmpty()) {
            throw new IllegalArgumentException("Portfolio is empty. Please add stocks before model evaluation.");
        }

        final String intervalType = inputData.getFrequency().toLowerCase();
        if (!isValidFrequency(intervalType)) {
            throw new IllegalArgumentException(
                    "Invalid interval type. Please use 'intraday', 'daily', or 'weekly'.");
        }

        return portfolio;
        // Assuming you want to return the portfolio
    }

    private boolean isValidFrequency(String frequency) {
        return frequency.equals(INTRADAY_FLAG) || frequency.equals(DAILY_FLAG) || frequency.equals(WEEKLY_FLAG);
    }

    @Override
    public void switchBack() {
        modelEvaluationPresenter.switchBack();
    }
}
