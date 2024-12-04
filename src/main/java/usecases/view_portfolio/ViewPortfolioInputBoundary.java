package usecases.view_portfolio;

/**
 * The input boundary for viewing portfolio.
 */
public interface ViewPortfolioInputBoundary {
    /**
     * Display the portfolio.
     */
    void execute();

    /**
     * Switch back to the home page.
     */
    void switchBack();
}
