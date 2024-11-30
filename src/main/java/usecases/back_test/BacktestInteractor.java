package usecases.back_test;
import entities.Portfolio;

public class BackTestInteractor implements BackTestInputBoundary{
    private final BackTestDataAccessInterface backTestDataAccessInterface;
    private final BackTestOutputBoundary backTestOutputBoundary;
    private final Portfolio portfolio;

    public BackTestInteractor(BackTestDataAccessInterface backTestDataAccessInterface, 
                                BackTestOutputBoundary backTestOutputBoundary,
                                Portfolio portfolio
                                ) {
        this.backTestDataAccessInterface = backTestDataAccessInterface;
        this.backTestOutputBoundary = backTestOutputBoundary;
        this.portfolio = portfolio;
    }
    @Override
    public void execute(BackTestInputData backTestInputData) {
        // Method
        final Portfolio portfolio = new Portfolio();
        
    }
}