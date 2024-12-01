package interface_adapters.revenue_prediction;

import interface_adapters.ViewModel;
import interface_adapters.revenue_prediction.RevenuePredictionState;

/**
 * ViewModel for the revenue prediction feature.
 * Manages the state and constants used in the revenue prediction view.
 */
public class RevenuePredictionViewModel extends ViewModel<RevenuePredictionState> {

    // Labels for UI components
    public static final String TITLE_LABEL = "Revenue Prediction";
    public static final String INTERVAL_TYPE_LABEL = "Prediction Interval";
    public static final String INTERVAL_LENGTH_LABEL = "Interval Length";
    public static final String PREDICT_BUTTON_LABEL = "Predict Revenue";
    public static final String BACK_BUTTON_LABEL = "Back";

    // Options for interval types
    public static final String[] INTERVAL_OPTIONS = {"day", "week", "intraday"};

    // Display format for results
    public static final String RESULT_FORMAT = "Predicted Revenue: $%.2f";

    /**
     * Constructs a new RevenuePredictionViewModel with initial state.
     */
    public RevenuePredictionViewModel() {
        super("predict revenue", new RevenuePredictionState());
    }
}