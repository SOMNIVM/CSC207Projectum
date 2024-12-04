package usecases.remove_stock;

/**
 * The input boundary for removing stock.
 */
public interface RemoveStockInputBoundary {
    /**
     * Execute the remove stock use case.
     * @param removeStockInputData the input data to the remove stock use case.
     */
    void execute(RemoveStockInputData removeStockInputData);

    /**
     * Switch back to the homepage.
     */
    void switchBack();
}
