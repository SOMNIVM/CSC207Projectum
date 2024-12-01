package interface_adapters.ModelEvaluation;

import usecases.ModelEvaluation.ModelEvaluationOutputBoundary;


public class ModelEvaluationPresenter implements ModelEvaluationOutputBoundary{

    private final ChooseFrequencyViewModel chooseFrequencyViewModel;
    private final ChooseModelViewModel chooseModelViewModel;
    public ModelEvaluationPresenter(ChooseFrequencyViewModel chooseFrequencyViewModel
    , ChooseModelViewModel chooseModelViewModel) {
        this.chooseFrequencyViewModel = chooseFrequencyViewModel;
        this.chooseModelViewModel = chooseModelViewModel;
    }
    public void prepareSuccessView() {
    // TODO Auto-generated method stub
    }
    public void prepareFailView(String errorDescription) {
    // TODO Auto-generated method stub
    }
}

