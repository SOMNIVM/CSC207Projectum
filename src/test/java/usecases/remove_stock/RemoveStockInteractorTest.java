
package usecases.remove_stock;

import entities.Portfolio;
import org.junit.jupiter.api.Test;
import usecases.LocalDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Set;


class RemoveStockInteractorTest {

    @Test
    void executeShouldRemoveStockWhenStockExistsAndSharesAreSufficient() {
        LocalDataAccessInterface mockDataAccess = mock(LocalDataAccessInterface.class);
        RemoveStockOutputBoundary mockPresenter = mock(RemoveStockOutputBoundary.class);
        Portfolio mockPortfolio = mock(Portfolio.class);
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of("AAPL", "AAPL"));
        when(mockPortfolio.getStockSymbols()).thenReturn(Set.of("AAPL"));
        when(mockPortfolio.getShares("AAPL")).thenReturn(10);

        RemoveStockInteractor interactor = new RemoveStockInteractor(mockPresenter, mockDataAccess);
        RemoveStockInputData inputData = new RemoveStockInputData("AAPL", 5);

        interactor.execute(inputData);

        verify(mockPortfolio).removeStock("AAPL", 5);
        verify(mockDataAccess).writeCurrentPortfolio();
        verify(mockPresenter).prepareSuccessView(any(RemoveStockOutputData.class));
    }

    @Test
    void executeShouldNotRemoveStockWhenStockDoesNotExist() {
        LocalDataAccessInterface mockDataAccess = mock(LocalDataAccessInterface.class);
        RemoveStockOutputBoundary mockPresenter = mock(RemoveStockOutputBoundary.class);
        Portfolio mockPortfolio = mock(Portfolio.class);
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of());

        RemoveStockInteractor interactor = new RemoveStockInteractor(mockPresenter, mockDataAccess);
        RemoveStockInputData inputData = new RemoveStockInputData("AAPL", 5);

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("The stock to be removed is not in your portfolio.");
    }

    @Test
    void executeShouldNotRemoveStockWhenSharesAreInsufficient() {
        LocalDataAccessInterface mockDataAccess = mock(LocalDataAccessInterface.class);
        RemoveStockOutputBoundary mockPresenter = mock(RemoveStockOutputBoundary.class);
        Portfolio mockPortfolio = mock(Portfolio.class);
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of("AAPL", "AAPL"));
        when(mockPortfolio.getStockSymbols()).thenReturn(Set.of("AAPL"));
        when(mockPortfolio.getShares("AAPL")).thenReturn(3);

        RemoveStockInteractor interactor = new RemoveStockInteractor(mockPresenter, mockDataAccess);
        RemoveStockInputData inputData = new RemoveStockInputData("AAPL", 5);

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("You only have 3 shares of AAPL stock.");
    }

    @Test
    void switchBackShouldInvokePresenterSwitchBack() {
        RemoveStockOutputBoundary mockPresenter = mock(RemoveStockOutputBoundary.class);
        LocalDataAccessInterface mockDataAccess = mock(LocalDataAccessInterface.class);

        RemoveStockInteractor interactor = new RemoveStockInteractor(mockPresenter, mockDataAccess);

        interactor.switchBack();

        verify(mockPresenter).switchBack();
    }
}