package interface_adapters.add_stock;

import interface_adapters.ViewModel;

public class AddStockViewModel extends ViewModel<AddStockState> {
    public static final String STOCK_NAME_FIELD_LABEL = "stock to add";
    public static final String SHARES_FIELD_LABEL = "shares to add";
    public static final String BUY_STOCK_BUTTON_LABEL = "add stock";
    public static final String CANCEL_BUTTON_LABEL = "cancel";
    public AddStockViewModel() {
        super("add stock", new AddStockState());
    }
}
