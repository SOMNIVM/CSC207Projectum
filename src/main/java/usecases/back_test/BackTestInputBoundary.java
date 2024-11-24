package usecases.back_test;

/**
 * The Back Test Use Case.
 */
public interface BackTestInputBoundary {

    /**
     * Execute the Back Test Use Case.
     * @param backTestInputData the input data for this use case
     */
    void execute(BackTestInputData backTestInputData);

}