
package usecases.add_stock;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Map;

import entities.Portfolio;

class AddStockInteractorTest {

    @Test
    void executeShouldAddStockWhenStockExists() {
        AddStockDataAccessInterface mockDataAccess = mock(AddStockDataAccessInterface.class);
        AddStockOutputBoundary mockPresenter = mock(AddStockOutputBoundary.class);
        Portfolio mockPortfolio = new Portfolio();
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of("AAPL", "AAPL"));
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockDataAccess.queryPrice("AAPL")).thenReturn(150.0);

        AddStockInteractor interactor = new AddStockInteractor(mockPresenter, mockDataAccess);
        AddStockInputData inputData = new AddStockInputData("AAPL", 10);

        interactor.execute(inputData);

        assertEquals(1, mockPortfolio.getStockSymbols().size());
        verify(mockDataAccess).writeCurrentPortfolio();
        verify(mockPresenter).prepareSuccessView(any(AddStockOutputData.class));
    }

    @Test
    void executeShouldNotAddStockWhenStockDoesNotExist() {
        AddStockDataAccessInterface mockDataAccess = mock(AddStockDataAccessInterface.class);
        AddStockOutputBoundary mockPresenter = mock(AddStockOutputBoundary.class);
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of());

        AddStockInteractor interactor = new AddStockInteractor(mockPresenter, mockDataAccess);
        AddStockInputData inputData = new AddStockInputData("AAPL", 10);

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("The stock can't be found. Please try again.");
    }

    @Test
    void executeShouldHandleNullStockName() {
        AddStockDataAccessInterface mockDataAccess = mock(AddStockDataAccessInterface.class);
        AddStockOutputBoundary mockPresenter = mock(AddStockOutputBoundary.class);

        AddStockInteractor interactor = new AddStockInteractor(mockPresenter, mockDataAccess);
        AddStockInputData inputData = new AddStockInputData(null, 10);

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("The stock can't be found. Please try again.");
    }

    @Test
    void switchBackShouldInvokePresenterSwitchBack() {
        AddStockOutputBoundary mockPresenter = mock(AddStockOutputBoundary.class);
        AddStockDataAccessInterface mockDataAccess = mock(AddStockDataAccessInterface.class);

        AddStockInteractor interactor = new AddStockInteractor(mockPresenter, mockDataAccess);

        interactor.switchBack();

        verify(mockPresenter).switchBack();
    }
    @Test
    void constructorShouldInitializeFields() {
        AddStockOutputData data = new AddStockOutputData("AAPL", 10, 150.0);
        assertEquals("AAPL", data.getStockName());
        assertEquals(10, data.getSharesPurchased());
        assertEquals(150.0, data.getCurrentPrice());
    }
}