package interface_adapters.model_evaluation;

import interface_adapters.ViewModel;

/**
 * The view model for the model evaluation use case.
 */
public class ModelEvaluationViewModel extends ViewModel<ModelEvaluationState> {
    public static final int SEPARATION_NARROW = 10;
    public static final int SEPARATION_WIDE = 20;
    public static final int INTERCELL_WIDTH = 10;
    public static final int INTERCELL_HEIGHT = 1;
    public static final int SCROLL_PANE_WIDTH = 400;
    public static final int SCROLL_PANE_HEIGHT = 200;
    public static final String TITLE_LABEL = "Model Evaluation";
    public static final String MODEL_NAME_TYPE_LABEL = "Model for evaluation";
    public static final String FREQUENCY_LABEL = "Trade frequency";
    public static final String LENGTH_LABEL = "length";
    public static final String[] FREQUENCY_OPTIONS = 
        {"Daily", "Weekly", "Intraday"};
    public static final String[] MODEL_NAME_OPTIONS = {
        "Average model",
        "Moving average model(Not implemented yet)",
        "ML model(Not implemented yet)"};
    public static final String PROCEED_EVALUATION_BUTTON_LABEL = "Proceed with evaluation";
    public static final String BACK_BUTTON_LABEL = "Back";

    public ModelEvaluationViewModel() {
        super("model evaluation", new ModelEvaluationState());
    }
}
