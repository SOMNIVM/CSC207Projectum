
package usecases.reset_portfolio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClearAllInteractorTest {

    @Test
    void executeShouldClearPortfolioData() {
        ClearAllDataAccessInterface mockDataAccess = mock(ClearAllDataAccessInterface.class);
        ClearAllOutputBoundary mockPresenter = mock(ClearAllOutputBoundary.class);

        ClearAllInteractor interactor = new ClearAllInteractor(mockPresenter, mockDataAccess);
        interactor.execute();

        verify(mockDataAccess).clearPortfolioData();
        verify(mockPresenter).prepareClearedView();
    }

    @Test
    void switchToBuyStockShouldInvokePresenterSwitchToBuyStock() {
        ClearAllOutputBoundary mockPresenter = mock(ClearAllOutputBoundary.class);
        ClearAllDataAccessInterface mockDataAccess = mock(ClearAllDataAccessInterface.class);

        ClearAllInteractor interactor = new ClearAllInteractor(mockPresenter, mockDataAccess);
        interactor.switchToBuyStock();

        verify(mockPresenter).switchToBuyStock();
    }

    @Test
    void switchToRemoveStockShouldInvokePresenterSwitchToRemoveStock() {
        ClearAllOutputBoundary mockPresenter = mock(ClearAllOutputBoundary.class);
        ClearAllDataAccessInterface mockDataAccess = mock(ClearAllDataAccessInterface.class);

        ClearAllInteractor interactor = new ClearAllInteractor(mockPresenter, mockDataAccess);
        interactor.switchToRemoveStock();

        verify(mockPresenter).switchToRemoveStock();
    }

    @Test
    void switchToPredictRevenueShouldInvokePresenterSwitchToPredictRevenue() {
        ClearAllOutputBoundary mockPresenter = mock(ClearAllOutputBoundary.class);
        ClearAllDataAccessInterface mockDataAccess = mock(ClearAllDataAccessInterface.class);

        ClearAllInteractor interactor = new ClearAllInteractor(mockPresenter, mockDataAccess);
        interactor.switchToPredictRevenue();

        verify(mockPresenter).switchToPredictRevenue();
    }

    @Test
    void switchToBacktestShouldInvokePresenterSwitchToBacktest() {
        ClearAllOutputBoundary mockPresenter = mock(ClearAllOutputBoundary.class);
        ClearAllDataAccessInterface mockDataAccess = mock(ClearAllDataAccessInterface.class);

        ClearAllInteractor interactor = new ClearAllInteractor(mockPresenter, mockDataAccess);
        interactor.switchToBacktest();

        verify(mockPresenter).switchToBacktest();
    }
}