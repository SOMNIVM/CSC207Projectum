package interface_adapters.reset_portfolio;

import interface_adapters.ViewModel;

/**
 * The view model characterizing the clear all use case.
 */
public class ClearAllViewModel extends ViewModel<ClearAllState> {
    public static final String MESSAGE_UPON_CLEARING = "Your portfolio has been successfully cleared.";
    public static final String VIEW_PORTFOLIO_BUTTON_LABEL = "view portfolio";
    public static final String ADD_STOCK_BUTTON_LABEL = "add stock";
    public static final String REMOVE_STOCK_BUTTON_LABEL = "remove stock";
    public static final String CLEAR_ALL_BUTTON_LABEL = "clear all";
    public static final String PREDICT_REVENUE_BUTTON_LABEL = "predict revenue";
    public static final String EVALUATE_BUTTON_LABEL = "evaluate model";

    public ClearAllViewModel() {
        super("homepage", new ClearAllState());
    }
}
