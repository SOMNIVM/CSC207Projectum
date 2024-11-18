package interface_adapters.back_test;

public class BackTestViewModel extends ViewModel<BackTestState> {
    public static final String START_DATE_LABEL = "Start Date (YYYY-MM-DD)";
    public static final String END_DATE_LABEL = "End Date (YYYY-MM-DD)";
    public static final String RUN_BUTTON_LABEL = "Run Backtest";
    
    public BackTestViewModel() {
        super("backtest", new BackTestState());
    }
}