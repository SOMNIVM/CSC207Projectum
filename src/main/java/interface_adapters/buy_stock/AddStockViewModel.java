package interface_adapters.buy_stock;

import interface_adapters.ViewModel;

public class AddStockViewModel extends ViewModel<AddStockState> {
    public static final String STOCK_NAME_FIELD_LABEL = "stock to buy";
    public static final String SHARES_FIELD_LABEL = "shares to buy";
    public static final String BUY_STOCK_BUTTON_LABEL = "buy stock";
    public static final String CANCEL_BUTTON_LABEL = "cancel";
    public AddStockViewModel() {
        super("buy stock", new AddStockState());
    }
}
