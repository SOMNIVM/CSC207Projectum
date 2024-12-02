package usecases.ModelEvaluation;

import entities.Portfolio;
import kotlin.Pair;

import java.util.SortedMap;

import javax.sound.sampled.Port;

import java.util.List;
import java.util.Map;
import usecases.LocalDataAccessInterface;

/**
 * Interface for accessing model evaluation data.
 */
public interface ModelEvaluationDataAccessInterface {

    /**
     * Retrieves historical prices for a given portfolio.
     *
     * @param portfolio The portfolio for which historical prices are to be retrieved.
     * @param numOfInterval The number of intervals for which historical prices are to be retrieved.
     * @return A map where the key is a string representing the asset and the value is a sorted map with the date as the key and the price as the value.
     */
    Map<String, List<Pair<String, Double>>> getHistoricalPrices(Portfolio portfolio, int numOfInterval);

    /**
     * Retrieves historical prices for a given portfolio in a list format.
     *
     * @param portfolio The portfolio for which historical prices are to be retrieved.
     * @param numOfInterval The number of intervals for which historical prices are to be retrieved.
     * @return A map where the key is a string representing the asset and the value is a list of pairs, with each pair containing a date and a price.
     */


}



