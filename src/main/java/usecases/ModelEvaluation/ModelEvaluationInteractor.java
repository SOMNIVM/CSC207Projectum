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
    private final Model modelType;
    private final LocalDataAccessInterface localDataAccessInterface;
    private final Portfolio portfolio;
    private final int numOfInterval;
    private double[] observations;

    public ModelEvaluationInteractor(
            ModelEvaluationDataAccessInterface modelEvaluationDataAccessInterface,
            ModelEvaluationOutputBoundary modelEvaluationPresenter,
            Model Model,
            LocalDataAccessInterface localDataAccessInterface,
            int numOfInterval) {
        this.dataAccess = modelEvaluationDataAccessInterface;
        this.modelEvaluationPresenter = modelEvaluationPresenter;
        this.modelType = Model;
        this.localDataAccessInterface = localDataAccessInterface;
        this.numOfInterval = numOfInterval;
        this.portfolio = localDataAccessInterface.getCurrentPortfolio();
    }

    @Override
    public void execute(ModelEvaluationInputData modelEvaluationInputData) {
        final double meanSquaredError = getMeanSquaredError();
        final modelEvaluationPresenter.prepareSuccessView(ge);
        
    }
    private double[] getPortfolioObservations(Portfolio portfolio, ModelEvaluationDataAccessInterface data) {
        double currentValueOfPortfolio = 0;
        List<Double> observations = new ArrayList<>();
        Map<String,List<Pair<String,Double>>> historicalPrices = data.getHistoricalPricesInList(portfolio, numOfInterval);
        for (int i = 1; i < numOfInterval; i++) {
            for (String stockSymbol : historicalPrices.keySet()) {
                double currentValueOfStock = portfolio.getShares(stockSymbol) * historicalPrices.get(stockSymbol).get(i).getSecond();
                currentValueOfPortfolio += currentValueOfStock;
            }
            observations.add(currentValueOfPortfolio);
        }
    }
    private double getPredictedPrice(double[] observations,Model model) {
        
    }
}
