package interface_adapters.reset_portfolio;

import usecases.reset_portfolio.ClearAllOutputBoundary;

public class ClearAllPresenter implements ClearAllOutputBoundary {
    private final ClearAllViewModel clearAllViewModel;
    public ClearAllPresenter(ClearAllViewModel viewModel) {
        this.clearAllViewModel = viewModel;
    }

    @Override
    public void prepareClearedView() {
        clearAllViewModel.getState().clear();
        clearAllViewModel.firePropertyChange();
    }
}
