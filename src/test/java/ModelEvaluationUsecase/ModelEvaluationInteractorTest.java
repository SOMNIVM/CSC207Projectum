package ModelEvaluationUsecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.model_evaluation.ModelEvaluationInteractor;
import usecases.model_evaluation.ModelEvaluationInputData;
import usecases.model_evaluation.ModelEvaluationOutputBoundary;
import entities.Portfolio;

/**
 * Unit tests for the ModelEvaluationInteractor class.
 */
public class ModelEvaluationInteractorTest {

    private ModelEvaluationOutputBoundary mockOutputBoundary;
    private LocalDataAccessInterface mockLocalDataAccessInterface;
    private OnlineDataAccessInterface mockOnlineDataAccessInterface;
    private ModelEvaluationInteractor interactor;

    @BeforeEach
    public void setUp() {
        mockOutputBoundary = mock(ModelEvaluationOutputBoundary.class);
        mockLocalDataAccessInterface = mock(LocalDataAccessInterface.class);
        mockOnlineDataAccessInterface = mock(OnlineDataAccessInterface.class);
        interactor = new ModelEvaluationInteractor(mockOnlineDataAccessInterface, mockLocalDataAccessInterface, mockOutputBoundary);
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        String modelType = "Average model";
        String frequency = "Daily";
        int length = 100;
        ModelEvaluationInputData inputData = new ModelEvaluationInputData(modelType, frequency, length);

        // Mock behaviors to return a valid Portfolio
        Portfolio mockPortfolio = new Portfolio();
        mockPortfolio.addStock("AAPL", 10, 100.1);
        when(mockLocalDataAccessInterface.getCurrentPortfolio()).thenReturn(mockPortfolio);

        // Mock for the OnlineDataAccessInterface
        


        // Act
        interactor.execute(inputData);

        // Assert
        verify(mockOutputBoundary, times(1)).prepareSuccessView(any());
    }

    @Test
    public void testExecute_Failure_InvalidFrequency() {
        // Arrange
        String modelType = "Average model";
        String frequency = "Monthly"; // Assuming 'Monthly' is invalid
        int length = 100;
        ModelEvaluationInputData inputData = new ModelEvaluationInputData(modelType, frequency, length);
        Portfolio mockPortfolio = new Portfolio();
        mockPortfolio.addStock("AAPL", 10, 100.1);
        when(mockLocalDataAccessInterface.getCurrentPortfolio()).thenReturn(mockPortfolio);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(mockOutputBoundary, times(1)).prepareFailView("Error occurInvalid interval type. Please use 'intraday', 'daily', or 'weekly'.");
    }
    @Test
    public void testExecute_Failure_fake_model() {
        // Arrange
        String modelType = "Wrong model";
        String frequency = "Intraday"; 
        int length = 100;
        ModelEvaluationInputData inputData = new ModelEvaluationInputData(modelType, frequency, length);
        Portfolio mockPortfolio = new Portfolio();
        mockPortfolio.addStock("AAPL", 10, 100.1);
        when(mockLocalDataAccessInterface.getCurrentPortfolio()).thenReturn(mockPortfolio);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(mockOutputBoundary, times(1)).prepareFailView("Error occurInvalid model type: Wrong model");
    }
}