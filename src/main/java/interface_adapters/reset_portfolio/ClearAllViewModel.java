package interface_adapters.reset_portfolio;

import interface_adapters.ViewModel;

public class ClearAllViewModel extends ViewModel<ClearAllState> {
    public static final String MESSAGE_UPON_CLEARING = "Your portfolio has been successfully cleared.";
    public static final String VIEW_PORTFOLIO_BUTTON_LABEL = "view portfolio";
    public static final String BUY_STOCK_BUTTON_LABEL = "buy stock";
    public static final String REMOVE_STOCK_BUTTON_LABEL = "remove stock";
    public static final String CLEAR_ALL_BUTTON_LABEL  = "clear all";
    public static final String PREDICT_REVENUE_BUTTON_LABEL = "predict revenue";
    public static final String BACKTEST_BUTTON_LABEL = "evaluate model";
    public ClearAllViewModel() {
        super("homepage", new ClearAllState());
    }
}
