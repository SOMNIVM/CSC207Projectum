package usecases.view_portfolio;

/**
 * The output boundary for viewing portfolio.
 */
public interface ViewPortfolioOutputBoundary {
    /**
     * Prepare the view that displays portfolio data.
     * @param viewPortfolioOutputData the output data from the interactor.
     */
    void prepareView(ViewPortfolioOutputData viewPortfolioOutputData);

    /**
     * Switch back to the home page.
     */
    void switchBack();
}
