package usecases.back_test;

/**
 * Output boundary interface for backtest operations.
 * Defines methods for handling backtest results.
 */
public interface BacktestOutputBoundary {
    
    /**
     * Presents the success result of a backtest operation.
     * @param backtestResult The result data to be presented
     */
    void presentSuccessResult(BacktestResponseModel backtestResult);

    /**
     * Presents the failure result of a backtest operation.
     * @param error The error message to be presented
     */
    void presentFailResult(String error);
}