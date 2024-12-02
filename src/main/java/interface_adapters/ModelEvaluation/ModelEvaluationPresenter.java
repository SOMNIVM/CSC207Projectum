package interface_adapters.ModelEvaluation;

import usecases.ModelEvaluation.ModelEvaluationOutputBoundary;
import usecases.ModelEvaluation.ModelEvaluationOutputData;

import interface_adapters.ViewManagerModel;




public class ModelEvaluationPresenter implements ModelEvaluationOutputBoundary{
    private final ModelEvaluationViewModel modelEvaluationViewModel;
    private ModelResultViewModel modelResultViewModel;
    private final ViewManagerModel viewManagerModel;
    public ModelEvaluationPresenter(ModelEvaluationViewModel modelEvaluationViewModel,
                                    ModelResultViewModel modelResultViewModel,
                                    ViewManagerModel viewManagerModel) {
        this.modelEvaluationViewModel = modelEvaluationViewModel;
        this.modelResultViewModel = modelResultViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView( ModelEvaluationOutputData modelEvaluationOutputData) {
    ModelResultState modelResultState = modelEvaluationViewModel.getState();
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
        if (modelResultViewModel != null) {
            viewManagerModel.getState().setCurViewName(modelResultViewModel.getViewName());
            viewManagerModel.firePropertyChange();            
        }
    }
    @Override
    public void switchBack() {
        viewManagerModel.getState().setCurViewName(modelEvaluationViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}

