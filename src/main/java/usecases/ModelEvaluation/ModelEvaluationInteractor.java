package usecases.ModelEvaluation;

import entities.Portfolio;
import kotlin.Pair;
import usecases.models.*;
import usecases.LocalDataAccessInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

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
            doulbe meanAbsoluteError = getMeanAbsoluteError();
            double sharpeRatio = getSharpeRatio();
            final ModelEvaluationOutputData modelEvaluationOutputData = new ModelEvaluationOutputData(meanSquaredError, predictedPrice, meanAbsoluteError, sharpeRatio);
            modelEvaluationPresenter.prepareSuccessView(modelEvaluationOutputData);
        } catch (Exception e) {
            modelEvaluationPresenter.prepareFailView(e.getMessage());
        }
    }

    private double[] getPortfolioObservations(Portfolio portfolio, ModelEvaluationDataAccessInterface data) {
        double currentValueOfPortfolio = 0;
        List<Double> localObservations = new ArrayList<>();
        Map<String,List<Pair<String,Double>>> historicalPrices = data.getHistoricalPricesInList(portfolio, numOfInterval);
        for (int i = 1; i < numOfInterval; i++) {
            for (String stockSymbol : historicalPrices.keySet()) {
                double currentValueOfStock = portfolio.getShares(stockSymbol) * historicalPrices.get(stockSymbol).get(i).getSecond();
                currentValueOfPortfolio += currentValueOfStock;
            }
            localObservations.add(currentValueOfPortfolio);
        }
    }
 
    private double getSharpeRatio() {
        return model.sharpeRatio();
    }

    private doulbe getMeanAbsoluteError() {
        return model.getMeanAbsoluteError();
    }

    private double getMeanSquaredError() {
        return model.getMeanSquaredError();
    }


    private double getPredictedPrice() {
        return model.predict();
    }
}
