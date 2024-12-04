package usecases.model_evaluation;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
import usecases.model_evaluation.ModelEvaluationInputBoundary;
import usecases.model_evaluation.ModelEvaluationOutputBoundary;
import usecases.models.*;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * ModelEvaluationInteractor class implements ModelEvaluationInputBoundary interface and handles the evaluation
 * of different prediction models for portfolio performance.
 * 
 * This class is responsible for:
 * - Calculating various performance metrics for the selected model
 * - Processing portfolio observations
 * - Managing model evaluation data and presenting results
 *
 *
 * 
 * 
 * The class supports different types of models and can evaluate them using metrics such as:
 * - Mean Squared Error
 * - Mean Absolute Error
 * - Sharpe Ratio
 * - Predicted vs Actual Price comparison
 *
 * @see ModelEvaluationInputBoundary
 * @see ModelEvaluationDataAccessInterface
 * @see ModelEvaluationOutputBoundary
 * @see LocalDataAccessInterface
 * @see Portfolio
 * @see Model
 */
public class ModelEvaluationInteractor implements ModelEvaluationInputBoundary {
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
            final double[] observations = getPortfolioObservations(portfolio, dataAccess, frequency, numOfInterval);
            Model model = Model.createModel(modelName, numOfInterval, observations);
            double meanSquaredError = model.getMeanSquaredError();
            double predictedPrice = model.getPredictedPrice();
            double meanAbsoluteError = model.getMeanAbsoluteError();
            double sharpeRatio = model.getSharpeRatio();
            double actualPrice = model.getActualPrice();
    
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
            
        } catch (Exception e) {
            modelEvaluationPresenter.prepareFailView("Error occur" + e.getMessage());
        }
    }



    private int setNunOfInterval(String frequency) {
        switch (frequency) {
            case "intraday":
                return Config.INTRADAY_SAMPLE_SIZE;
            case "daily":
                return Config.DAILY_SAMPLE_SIZE;
            default:
                return Config.WEEKLY_SAMPLE_SIZE;
        }
    }

private double[] getPortfolioObservations(Portfolio portfolio, OnlineDataAccessInterface dataAccess, String frequency, int numOfInterval) {
    List<Double> localObservations = new ArrayList<>();
    Map<String, List<Pair<String, Double>>> historicalPrices;
    switch (frequency) {
        case "intraday":
            historicalPrices = dataAccess.getBulkTimeSeriesIntraDay(portfolio, numOfInterval, Config.INTRADAY_PREDICT_INTERVAL);
            break;
        case "daily":
            historicalPrices = dataAccess.getBulkTimeSeriesDaily(portfolio, numOfInterval);
            break;
        default:
            historicalPrices = dataAccess.getBulkTimeSeriesWeekly(portfolio, numOfInterval);
            break;
    }

    for (int i = 0; i < numOfInterval; i++) {
        double currentValueOfPortfolio = 0;
        for (Map.Entry<String, List<Pair<String, Double>>> entry : historicalPrices.entrySet()) {
            String stockSymbol = entry.getKey();
            double currentValueOfStock = portfolio.getShares(stockSymbol) * entry.getValue().get(i).getSecond();
            currentValueOfPortfolio += currentValueOfStock;
        }
        localObservations.add(currentValueOfPortfolio);
    }
    return localObservations.stream().mapToDouble(Double::doubleValue).toArray();
}


private Portfolio validateAndGetPortfolio(ModelEvaluationInputData inputData) {
    Portfolio portfolio = localDataAccessInterface.getCurrentPortfolio();

    if (portfolio.getStockSymbols().isEmpty()) {
        throw new IllegalArgumentException("Portfolio is empty. Please add stocks before model evaluation.");
    }

    String intervalType = inputData.getFrequency().toLowerCase();
    if (!isValidFrequency(intervalType)) {
        throw new IllegalArgumentException(
                "Invalid interval type. Please use 'intraday', 'daily', or 'weekly'.");
    }

    return portfolio; // Assuming you want to return the portfolio
}

private boolean isValidFrequency(String frequency) {
    return frequency.equals("intraday") || frequency.equals("daily") || frequency.equals("weekly");
}

@Override
public void switchBack() {
    modelEvaluationPresenter.switchBack();
}
}
