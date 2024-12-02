package usecases.ModelEvaluation;

import app.Config;
import entities.Portfolio;
import kotlin.Pair;
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
    private String modelType = "avgModel";
    private final LocalDataAccessInterface localDataAccessInterface;
    private final Portfolio portfolio;
    private final int numOfInterval;
    private double[] observations;
    private Model model;
    private String frequency;


    public ModelEvaluationInteractor(
            OnlineDataAccessInterface dataAccess,   
            ModelEvaluationOutputBoundary modelEvaluationPresenter,
            LocalDataAccessInterface localDataAccessInterface,
            int numOfInterval,
            String frequency) {
        this.dataAccess = dataAccess;
        this.modelEvaluationPresenter = modelEvaluationPresenter;
        this.localDataAccessInterface = localDataAccessInterface;
        this.portfolio = localDataAccessInterface.getCurrentPortfolio();
        this.observations = getPortfolioObservations(portfolio, dataAccess);
        this.model = Model.createModel(modelType, numOfInterval, observations);
        this.frequency = frequency;
        switch (frequency) {
            case "intraday":
                this.numOfInterval = Config.INTRADAY_SAMPLE_SIZE;
                break;
            case "daily":
                this.numOfInterval = Config.DAILY_SAMPLE_SIZE;
                break;
            default:
                this.numOfInterval = Config.WEEKLY_SAMPLE_SIZE;
                break;
        }

    }
    public ModelEvaluationInteractor(
        OnlineDataAccessInterface dataAccess,
        ModelEvaluationOutputBoundary modelEvaluationPresenter,
        LocalDataAccessInterface localDataAccessInterface,
        int numOfInterval,
        String modelType,
        String frequency) {
    this.dataAccess = dataAccess;
    this.modelEvaluationPresenter = modelEvaluationPresenter;
    this.localDataAccessInterface = localDataAccessInterface;
    this.portfolio = localDataAccessInterface.getCurrentPortfolio();
    this.observations = getPortfolioObservations(portfolio, dataAccess);
    this.modelType = modelType;
    this.model = Model.createModel(modelType, numOfInterval, observations);
    this.frequency = frequency;
    switch (frequency) {
        case "intraday":
            this.numOfInterval = Config.INTRADAY_SAMPLE_SIZE;
            break;
        case "daily":
            this.numOfInterval = Config.DAILY_SAMPLE_SIZE;
            break;
        default:
            this.numOfInterval = Config.WEEKLY_SAMPLE_SIZE;
            break;
    }

    }
    @Override
    public void execute(ModelEvaluationInputData modelEvaluationInputData) {
        try {
            double meanSquaredError = getMeanSquaredError();
            double predictedPrice = getPredictedPrice();
            double meanAbsoluteError = getMeanAbsoluteError();
            double sharpeRatio = getSharpeRatio();
            double actualPrice = getActualPrice();
            int length = this.numOfInterval;
            String modelName = modelType;
            String frequency = modelEvaluationInputData.getFrequency();
    
            final ModelEvaluationOutputData modelEvaluationOutputData =
                    new ModelEvaluationOutputData(
                            modelName,
                            frequency,
                            length,
                            meanSquaredError,
                            meanAbsoluteError,
                            sharpeRatio,
                            predictedPrice,
                            actualPrice
                    );
    
            modelEvaluationPresenter.prepareSuccessView(modelEvaluationOutputData);
            
        } catch (Exception e) {
            modelEvaluationPresenter.prepareFailView(e.getMessage());
        }
    }

private double[] getPortfolioObservations(Portfolio portfolio, OnlineDataAccessInterface dataAccess) {
    List<Double> localObservations = new ArrayList<>();
    List<String> stockSymbols = new ArrayList<>(portfolio.getStockSymbols());
    Map<String, List<Pair<String, Double>>> historicalPrices;
    switch (this.frequency) {
        case "intraday":
            historicalPrices = dataAccess.getBulkTimeSeriesIntraDay(stockSymbols, numOfInterval, Config.INTRADAY_PREDICT_INTERVAL);
            break;
        case "daily":
            historicalPrices = dataAccess.getBulkTimeSeriesDaily(stockSymbols, numOfInterval);
            break;
        default:
            historicalPrices = dataAccess.getBulkTimeSeriesWeekly(stockSymbols, numOfInterval);
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
 
    private double getSharpeRatio() {
        return model.getSharpeRatio();
    }

    private double getMeanAbsoluteError() {
        return model.getMeanAbsoluteError();
    }

    private double getMeanSquaredError() {
        return model.getMeanSquaredError();
    }


    private double getPredictedPrice() {
        return model.getPredictedPrice();
    }
    private double getActualPrice() {
        return model.getActualPrice();

}
    @Override
    public void switchBack() {
        modelEvaluationPresenter.switchToModelResult();

}
}