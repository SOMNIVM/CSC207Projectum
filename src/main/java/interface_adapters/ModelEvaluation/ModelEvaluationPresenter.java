package interface_adapters.ModelEvaluation;

import usecases.ModelEvaluation.ModelEvaluationOutputBoundary;
import usecases.ModelEvaluation.ModelEvaluationOutputData;



public class ModelEvaluationPresenter implements ModelEvaluationOutputBoundary{
    private final ModelEvaluationViewModel modelEvaluationViewModel;
    public ModelEvaluationPresenter(ModelEvaluationViewModel modelEvaluationViewModel) {
        this.modelEvaluationViewModel = modelEvaluationViewModel;
    }
    @Override
    public void prepareSuccessView( ModelEvaluationOutputData modelEvaluationOutputData) {
    ModelResultState modelResultState = modelEvaluationViewModel.getState();
    modelResultState.setAsValid();
    modelResultState.setModelName(modelEvaluationOutputData.getModelName());
    modelResultState.setFrequency(modelEvaluationOutputData.getFrequency());
    modelResultState.setLength(modelEvaluationOutputData.getLength());
    modelResultState.setMeanSquaredError(modelEvaluationOutputData.getMeanSquaredError());
    modelResultState.setMeanAbsoluteError(modelEvaluationOutputData.getMeanAbsoluteError());
    modelResultState.setSharpRatio(modelEvaluationOutputData.getSharpRatio());
    modelResultState.setPredictedPrice(modelEvaluationOutputData.getPredictedPrice());
    modelResultState.setActualPrice(modelEvaluationOutputData.getActualPrice());
    modelEvaluationViewModel.firePropertyChange();
    }
    @Override
    public void prepareFailView(String errorDescription) {
    modelEvaluationViewModel.getState().setAsInvalid(errorDescription);
    modelEvaluationViewModel.firePropertyChange();
    }
}

