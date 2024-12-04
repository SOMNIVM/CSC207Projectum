package interface_adapters.revenue_prediction;

import interface_adapters.ViewModel;

/**
 * ViewModel for the revenue prediction feature.
 * Manages the state and UI constants used in the revenue prediction view.
 */
public class RevenuePredictionViewModel extends ViewModel<RevenuePredictionState> {
    /** Label for the view title. */
    public static final String TITLE_LABEL = "Revenue Prediction";

    /** Label for model selection dropdown. */
    public static final String MODEL_TYPE_LABEL = "Prediction Model";

    /** Label for interval type selection. */
    public static final String INTERVAL_TYPE_LABEL = "Prediction Interval";

    /** Label for interval length input. */
    public static final String INTERVAL_LENGTH_LABEL = "Interval Length";

    /** Label for predict button. */
    public static final String PREDICT_BUTTON_LABEL = "Predict Revenue";

    /** Label for back button. */
    public static final String BACK_BUTTON_LABEL = "Back";

    /** Available prediction model options. */
    public static final String[] MODEL_OPTIONS = {"Average Model", "Linear Regression Model"};

    /** Available interval type options. */
    public static final String[] INTERVAL_OPTIONS = {"intraday", "day", "week"};

    /** Format string for displaying prediction results. */
    public static final String RESULT_FORMAT = "Predicted Revenue: $%.2f";

    /**
     * Constructs a new RevenuePredictionViewModel.
     * Initializes with a new state and sets the view name.
     */
    public RevenuePredictionViewModel() {
        super("predict revenue", new RevenuePredictionState());
    }
}
