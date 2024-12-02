package usecases.add_stock;

/**
 * Interface for the input boundary of the Add Stock use case.
 */
public interface AddStockInputBoundary {

    /**
     * Executes the add stock use case with the provided input data.
     *
     * @param addStockInputData the input data for adding a stock
     */
    void execute(AddStockInputData addStockInputData);

    /**
     * Switches back to the previous state or view.
     */
    void switchBack();
}
