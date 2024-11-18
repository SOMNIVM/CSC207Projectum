package interface_adapters.back_test;

public class BackTestController {
    private final BackTestInputBoundary backTestInteractor;
    
    public BackTestController(BackTestInputBoundary interactor) {
        this.backTestInteractor = interactor;
    }
    
    public void execute(String startDate, String endDate) {
        // Convert and validate dates, then call use case
        backTestInteractor.execute();
    }
}
