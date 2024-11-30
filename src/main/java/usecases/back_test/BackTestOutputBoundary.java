package usecases.back_test;

/**
 * Output boundary interface for backtest operations.
 * Defines methods for handling backtest results.
 */
public interface BackTestOutputBoundary {
    
    /**
     * Presents the success result of a backtest operation.
     * @param backtestResult The result data to be presented
     */
    void presentSuccessResult(BackTestOutputData outputData);

    /**
     * Presents the failure result of a backtest operation.
     * @param error The error message to be presented
     */
    void presentFailResult(String error);
}