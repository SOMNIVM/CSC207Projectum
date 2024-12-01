package usecases.revenue_prediction;

/**
 * Input boundary for the revenue prediction use case.
 * This interface defines the entry point for predicting revenue over specified time intervals.
 * The implementation will process interval length and interval type (e.g., day, week)
 * to generate revenue predictions.
 */
public interface RevenuePredictionInputBoundary {
    /**
     * Executes the revenue prediction calculation for a portfolio.
     *
     * @param revenuePredictionInputData Contains the interval length and interval name
     *                                   (e.g., 5 and "days" for a 5-day prediction)
     */
    void execute(RevenuePredictionInputData revenuePredictionInputData);
}