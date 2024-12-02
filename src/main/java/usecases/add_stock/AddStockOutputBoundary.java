
package usecases.add_stock;

/**
 * Interface for the output boundary of the Add Stock use case.
 */
public interface AddStockOutputBoundary {

    /**
     * Prepares the success view with the provided output data.
     *
     * @param addStockOutputData the output data for adding a stock
     */
    void prepareSuccessView(AddStockOutputData addStockOutputData);

    /**
     * Prepares the failure view with the provided error description.
     *
     * @param errorDescription the description of the error
     */
    void prepareFailView(String errorDescription);

    /**
     * Switches back to the previous state or view.
     */
    void switchBack();
}
