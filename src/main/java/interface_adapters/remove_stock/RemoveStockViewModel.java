package interface_adapters.remove_stock;

import interface_adapters.ModifyPortfolioState;
import interface_adapters.ViewModel;

/**
 * The view model for the remove stock use case.
 */
public class RemoveStockViewModel extends ViewModel<ModifyPortfolioState> {
    public static final int TEXTFIELD_COLS = 30;
    public static final String STOCK_NAME_FIELD_LABEL = "stock to remove";
    public static final String SHARES_FIELD_LABEL = "shares to remove";
    public static final String REMOVE_STOCK_BUTTON_LABEL = "remove stock";
    public static final String CANCEL_BUTTON_LABEL = "cancel";

    public RemoveStockViewModel() {
        super("remove stock", new ModifyPortfolioState());
    }
}
