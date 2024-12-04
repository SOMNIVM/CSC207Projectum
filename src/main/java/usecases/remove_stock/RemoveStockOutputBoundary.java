package usecases.remove_stock;

/**
 * The output boundary for the remove stock use case.
 */
public interface RemoveStockOutputBoundary {
    /**
     * Prepare the view for a successful stock removal.
     * @param removeStockOutputData the output data of the remove stock use case.
     */
    void prepareSuccessView(RemoveStockOutputData removeStockOutputData);

    /**
     * Prepare the view for a failed stock removal.
     * @param errorDescription the description about the failure.
     */
    void prepareFailView(String errorDescription);

    /**
     * Switch back to the homepage.
     */
    void switchBack();
}
