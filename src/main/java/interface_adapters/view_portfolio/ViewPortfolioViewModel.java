package interface_adapters.view_portfolio;

import interface_adapters.ViewModel;

/**
 * The view model for viewing portfolio.
 */
public class ViewPortfolioViewModel extends ViewModel<ViewPortfolioState> {
    public static final String TOTAL_VALUE_LABEL = "total value of your portfolio ($)";
    public static final int SCROLL_PANE_WIDTH = 1200;
    public static final int SCROLL_PANE_HEIGHT = 300;
    public static final String[] COLUMNS = new String[]{
        "stock",
        "number of shares",
        "current value per share ($)",
        "current value ($)"};
    public static final String BACK_BUTTON_LABEL = "back";

    public ViewPortfolioViewModel() {
        super("view portfolio", new ViewPortfolioState());
    }
}
