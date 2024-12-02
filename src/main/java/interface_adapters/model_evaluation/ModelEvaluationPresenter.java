package interface_adapters.model_evaluation;

import interface_adapters.ViewManagerModel;
import usecases.model_evaluation.ModelEvaluationOutputBoundary;
import usecases.model_evaluation.ModelEvaluationOutputData;

/**
 * The presenter for the model evaluation use case.
 */
public class ModelEvaluationPresenter implements ModelEvaluationOutputBoundary {
    private final ModelEvaluationViewModel modelEvaluationViewModel;
    private final ViewManagerModel viewManagerModel;
    public ModelEvaluationPresenter(ModelEvaluationViewModel modelEvaluationViewModel,
                                    ViewManagerModel viewManagerModel) {
        this.modelEvaluationViewModel = modelEvaluationViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView( ModelEvaluationOutputData modelEvaluationOutputData) {
    ModelEvaluationState modelResultState = modelEvaluationViewModel.getState();
    modelResultState.setModelName(modelEvaluationOutputData.getModelName());
    modelResultState.setFrequency(modelEvaluationOutputData.getFrequency());
    modelResultState.setLength(modelEvaluationOutputData.getLength());
    modelResultState.setMeanSquaredError(modelEvaluationOutputData.getMeanSquaredError());
    modelResultState.setMeanAbsoluteError(modelEvaluationOutputData.getMeanAbsoluteError());
    modelResultState.setSharpeRatio(modelEvaluationOutputData.getSharpRatio());
    modelResultState.setPredictedPrice(modelEvaluationOutputData.getPredictedPrice());
    modelResultState.setActualPrice(modelEvaluationOutputData.getActualPrice());
    modelEvaluationViewModel.firePropertyChange();
    }
    @Override
    public void prepareFailView(String errorDescription) {
    modelEvaluationViewModel.getState().setAsInvalid(errorDescription);
    modelEvaluationViewModel.firePropertyChange();
    }
    @Override
    public void switchToModelResult() {
        if (modelEvaluationViewModel != null) {
            viewManagerModel.getState().setCurViewName(modelEvaluationViewModel.getViewName());
            viewManagerModel.firePropertyChange();            
        }
    }
    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName("homepage");
        viewManagerModel.firePropertyChange();
        modelEvaluationViewModel.getState().reset();
        System.out.println("Switching back to homepage");
    }
}

