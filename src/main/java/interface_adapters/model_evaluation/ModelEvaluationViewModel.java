package interface_adapters.model_evaluation;

import interface_adapters.ViewModel;

public class ModelEvaluationViewModel extends ViewModel<ModelResultState> {
    public static final String MODEL_NAME_TYPE_LABEL = "model name";
    public static final String FREQUENCY_LABEL = "frequency";
    public static final String LENGTH_LABEL = "length";
    public static final String FREQUENCY_OPTIONS = "daily, weekly, monthly";
    public static final String MODEL_NAME_OPTIONS = "average, moving average(Not implemented yet), etc(Not implemented yet)";

    public ModelEvaluationViewModel() {
        super("model evaluation", new ModelResultState());
    }
}
