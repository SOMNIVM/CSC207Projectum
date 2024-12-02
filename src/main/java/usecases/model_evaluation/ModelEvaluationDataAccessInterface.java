package usecases.model_evaluation;

import entities.Portfolio;
import kotlin.Pair;

import java.util.SortedMap;

import java.util.List;
import java.util.Map;

public interface ModelEvaluationDataAccessInterface {

    Map<String, SortedMap<String,Double>> getHistoricalPrices(Portfolio portfolio, int numOfInterval);

    Map<String,List<Pair<String,Double>>> getHistoricalPricesInList(Portfolio portfolio, int numOfInterval);

}
