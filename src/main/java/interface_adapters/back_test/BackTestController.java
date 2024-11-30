package interface_adapters.back_test;

import usecases.back_test.BackTestInputBoundary;
import usecases.back_test.BackTestInputData;


/**
 * Controller for the Back Test Use Case.
 */
public class BackTestController {
    private final BackTestInputBoundary backTestInteractor;

    public BackTestController(BackTestInputBoundary backTestInteractor) {
        this.backTestInteractor = backTestInteractor;
    }

    /**
     * Executes the Back Test Use Case.
     * @param startDate the start date for the back test
     * @param endDate the end date for the back test
     */
    public void execute(String strategyName, String stockSymbol, int initialCapital, int startDate, int endDate) {
        final BackTestInputData backTestInputData = new BackTestInputData(strategyName, stockSymbol, initialCapital, startDate, endDate);

        backTestInteractor.execute(backTestInputData);
    }
}
