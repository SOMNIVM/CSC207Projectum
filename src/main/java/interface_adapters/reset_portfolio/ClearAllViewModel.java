package interface_adapters.reset_portfolio;

import interface_adapters.ViewModel;

public class ClearAllViewModel extends ViewModel<ClearAllState> {
    public ClearAllViewModel() {
        super("homepage", new ClearAllState());
    }
}
