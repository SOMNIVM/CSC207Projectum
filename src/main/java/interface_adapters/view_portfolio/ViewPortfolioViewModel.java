package interface_adapters.view_portfolio;

import interface_adapters.ViewModel;

public class ViewPortfolioViewModel extends ViewModel<ViewPortfolioState> {
    public static final String TOTAL_VALUE_LABEL = "total value of your portfolio ($)";
    public static final String[] COLUMNS = new String[]{"stock name", "share", "average price ($)", "value ($)"};
    public static final String BACK_BUTTON_LABEL = "back";
    public ViewPortfolioViewModel() {
        super("view portfolio", new ViewPortfolioState());
    }
}
