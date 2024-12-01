package interface_adapters.ModelEvaluation;

import interface_adapters.ViewModel;

public class ModelEvaluationViewModel extends ViewModel<ModelResultState> {
    public static final String MODEL_NAME_TYPE_LABEL = "model name";

    public ModelEvaluationViewModel() {
        super("model evaluation", new ModelResultState());

    }
}
