package usecases.model_evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import entities.Portfolio;

/**
 * Unit tests for the ModelEvaluationInteractor class.
 */
public class ModelEvaluationInputBoundaryTest {

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
    public void testSwitchBack() {
        // Arrange
        // Act
        interactor.switchBack();
        // Assert
        verify(mockOutputBoundary, times(1)).switchBack();
    }   
}