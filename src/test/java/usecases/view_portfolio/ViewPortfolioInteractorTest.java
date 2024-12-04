package usecases.view_portfolio;

import entities.Portfolio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewPortfolioInteractorTest {

    @Test
    void executeShouldPrepareViewWithSortedStockList() {
        ViewPortfolioDataAccessInterface mockDataAccess = mock(ViewPortfolioDataAccessInterface.class);
        ViewPortfolioOutputBoundary mockPresenter = mock(ViewPortfolioOutputBoundary.class);
        Portfolio mockPortfolio = mock(Portfolio.class);
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockPortfolio.getStockSymbols()).thenReturn(Set.of("AAPL", "GOOGL"));
        when(mockDataAccess.getSymbolToNameMap()).thenReturn(Map.of("AAPL", "Apple", "GOOGL", "Google"));
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of("Apple", "AAPL", "Google", "GOOGL"));
        when(mockDataAccess.getSymbolToCurrentPrice()).thenReturn(Map.of("AAPL", 150.0, "GOOGL", 2000.0));
        when(mockPortfolio.getShares("AAPL")).thenReturn(10);
        when(mockPortfolio.getShares("GOOGL")).thenReturn(5);

        ViewPortfolioInteractor interactor = new ViewPortfolioInteractor(mockPresenter, mockDataAccess);
        interactor.execute();

        verify(mockPresenter).prepareView(argThat(outputData ->
                outputData.getStocks().equals(List.of("Apple (AAPL)", "Google (GOOGL)")) &&
                        outputData.getShares().equals(List.of(10, 5)) &&
                        outputData.getValuesPerShare().equals(List.of(150.0, 2000.0))
        ));
    }

    @Test
    void executeShouldHandleEmptyPortfolio() {
        ViewPortfolioDataAccessInterface mockDataAccess = mock(ViewPortfolioDataAccessInterface.class);
        ViewPortfolioOutputBoundary mockPresenter = mock(ViewPortfolioOutputBoundary.class);
        Portfolio mockPortfolio = mock(Portfolio.class);
        when(mockDataAccess.getCurrentPortfolio()).thenReturn(mockPortfolio);
        when(mockPortfolio.getStockSymbols()).thenReturn(Set.of());
        when(mockDataAccess.getSymbolToNameMap()).thenReturn(Map.of());
        when(mockDataAccess.getNameToSymbolMap()).thenReturn(Map.of());
        when(mockDataAccess.getSymbolToCurrentPrice()).thenReturn(Map.of());

        ViewPortfolioInteractor interactor = new ViewPortfolioInteractor(mockPresenter, mockDataAccess);
        interactor.execute();

        verify(mockPresenter).prepareView(argThat(outputData ->
                outputData.getStocks().isEmpty() &&
                        outputData.getShares().isEmpty() &&
                        outputData.getValuesPerShare().isEmpty()
        ));
    }

    @Test
    void switchBackShouldInvokePresenterSwitchBack() {
        ViewPortfolioOutputBoundary mockPresenter = mock(ViewPortfolioOutputBoundary.class);
        ViewPortfolioDataAccessInterface mockDataAccess = mock(ViewPortfolioDataAccessInterface.class);

        ViewPortfolioInteractor interactor = new ViewPortfolioInteractor(mockPresenter, mockDataAccess);
        interactor.switchBack();

        verify(mockPresenter).switchBack();
    }
    @Test
    void getValuesShouldReturnCorrectValues() {
        ViewPortfolioOutputData data = new ViewPortfolioOutputData(
                List.of("AAPL", "GOOGL"),
                List.of(10, 5),
                List.of(150.0, 2000.0)
        );
        assertEquals(List.of(1500.0, 10000.0), data.getValues());
    }

    @Test
    void getTotalValueShouldReturnCorrectTotalValue() {
        ViewPortfolioOutputData data = new ViewPortfolioOutputData(
                List.of("AAPL", "GOOGL"),
                List.of(10, 5),
                List.of(150.0, 2000.0)
        );
        assertEquals(11500.0, data.getTotalValue());
    }

    @Test
    void getValuesShouldHandleEmptyLists() {
        ViewPortfolioOutputData data = new ViewPortfolioOutputData(
                List.of(),
                List.of(),
                List.of()
        );
        assertEquals(List.of(), data.getValues());
    }

    @Test
    void getTotalValueShouldHandleEmptyLists() {
        ViewPortfolioOutputData data = new ViewPortfolioOutputData(
                List.of(),
                List.of(),
                List.of()
        );
        assertEquals(0.0, data.getTotalValue());
    }
}