package interface_adapters.model_evaluation;

import interface_adapters.ViewModel;

public class ModelEvaluationViewModel extends ViewModel<ModelEvaluationState> {
    public static final String TITLE_LABEL = "Model Evaluation";
    public static final String MODEL_NAME_TYPE_LABEL = "Model for evaluation";
    public static final String FREQUENCY_LABEL = "Trade frequency";
    public static final String LENGTH_LABEL = "length";
    public static final String[] FREQUENCY_OPTIONS = 
        {"Daily", "Weekly", "Intraday"};
    public static final String[] MODEL_NAME_OPTIONS = {
            "Average model", 
            "Moving average model(Not implemented yet)", 
            "ML model(Not implemented yet)"
    };
    public static final String PROCEED_EVALUATION_BUTTON_LABEL = "Proceed with evaluation";
    public static final String BACK_BUTTON_LABEL = "Back";
    public ModelEvaluationViewModel() {
        super("model evaluation", new ModelEvaluationState());
    }
}
