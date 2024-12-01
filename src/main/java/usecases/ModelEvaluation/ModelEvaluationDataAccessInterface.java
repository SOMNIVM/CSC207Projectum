package usecases.ModelEvaluation;

import entities.Portfolio;
import kotlin.Pair;

import java.util.SortedMap;

import javax.sound.sampled.Port;

import java.util.List;
import java.util.Map;
import usecases.LocalDataAccessInterface;

public interface ModelEvaluationDataAccessInterface {

    Map<String, SortedMap<String,Double>> getHistoricalPrices(Portfolio portfolio, int numOfInterval);

    Map<String,List<Pair<String,Double>>> getHistoricalPricesInList(Portfolio portfolio, int numOfInterval);

}
