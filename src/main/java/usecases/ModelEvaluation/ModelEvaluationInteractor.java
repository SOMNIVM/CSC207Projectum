package usecases.ModelEvaluation;

import entities.Portfolio;
import kotlin.Pair;
import usecases.models.*;
import usecases.LocalDataAccessInterface;

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
    private final ModelEvaluationDataAccessInterface dataAccess;
    private final ModelEvaluationOutputBoundary modelEvaluationPresenter;
    private String modelType = "avgModel";
    private final LocalDataAccessInterface localDataAccessInterface;
    private final Portfolio portfolio;
    private final int numOfInterval;
    private double[] observations;
    private Model model;


    public ModelEvaluationInteractor(
            ModelEvaluationDataAccessInterface modelEvaluationDataAccessInterface,
            ModelEvaluationOutputBoundary modelEvaluationPresenter,
            LocalDataAccessInterface localDataAccessInterface,
            int numOfInterval) {
        this.dataAccess = modelEvaluationDataAccessInterface;
        this.modelEvaluationPresenter = modelEvaluationPresenter;
        this.localDataAccessInterface = localDataAccessInterface;
        this.numOfInterval = numOfInterval;
        this.portfolio = localDataAccessInterface.getCurrentPortfolio();
        this.observations = getPortfolioObservations(portfolio, dataAccess);
        this.model = Model.createModel(modelType, numOfInterval, observations);
    }
    public ModelEvaluationInteractor(
        ModelEvaluationDataAccessInterface modelEvaluationDataAccessInterface,
        ModelEvaluationOutputBoundary modelEvaluationPresenter,
        LocalDataAccessInterface localDataAccessInterface,
        int numOfInterval,
        String modelType) {
    this.dataAccess = modelEvaluationDataAccessInterface;
    this.modelEvaluationPresenter = modelEvaluationPresenter;
    this.localDataAccessInterface = localDataAccessInterface;
    this.numOfInterval = numOfInterval;
    this.portfolio = localDataAccessInterface.getCurrentPortfolio();
    this.observations = getPortfolioObservations(portfolio, dataAccess);
    this.modelType = modelType;
    this.model = Model.createModel(modelType, numOfInterval, observations);
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

    private double[] getPortfolioObservations(Portfolio portfolio, ModelEvaluationDataAccessInterface data) {
        double currentValueOfPortfolio = 0;
        List<Double> localObservations = new ArrayList<>();
        Map<String,List<Pair<String,Double>>> historicalPrices = data.getHistoricalPrices(portfolio, numOfInterval);
        for (int i = 0; i < numOfInterval; i++) {
            for (String stockSymbol : historicalPrices.keySet()) {
                double currentValueOfStock = portfolio.getShares(stockSymbol) * historicalPrices.get(stockSymbol).get(i).getSecond();
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
}