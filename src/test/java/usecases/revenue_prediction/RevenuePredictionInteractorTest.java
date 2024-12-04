package usecases.revenue_prediction;

import entities.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.predict_models.PredictModel;

import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionInteractorTest {

    private static class TestLocalDataAccess implements LocalDataAccessInterface {
        private final Portfolio portfolio;

        TestLocalDataAccess(Portfolio portfolio) {
            this.portfolio = portfolio;
        }

        @Override
        public Portfolio getCurrentPortfolio() {
            return portfolio;
        }

        @Override
        public void writeCurrentPortfolio() {}

        @Override
        public java.util.Map<String, String> getNameToSymbolMap() {
            return new java.util.HashMap<>();
        }

        @Override
        public java.util.Map<String, String> getSymbolToNameMap() {
            return new java.util.HashMap<>();
        }
    }

    private static class TestPredictModel implements PredictModel {
        private double predictedRevenue = 500.0;

        @Override
        public void setOnlineDataAccess(OnlineDataAccessInterface onlineDataAccess) {}

        @Override
        public double predictValue(Portfolio portfolio, int intervalLength, String intervalName) {
            return predictedRevenue;
        }

        @Override
        public double predictRevenue(Portfolio portfolio, int intervalLength, String intervalName) {
            return predictedRevenue;
        }

        public void setPredictedRevenue(double revenue) {
            this.predictedRevenue = revenue;
        }
    }

    private static class TestOnlineDataAccess implements OnlineDataAccessInterface {
        @Override
        public java.util.List<kotlin.Pair<String, Double>> getSingleTimeSeriesIntraDay(String symbol, int sampleSize, int interval) {
            return new java.util.ArrayList<>();
        }

        @Override
        public java.util.List<kotlin.Pair<String, Double>> getSingleTimeSeriesDaily(String symbol, int sampleSize) {
            return new java.util.ArrayList<>();
        }

        @Override
        public java.util.List<kotlin.Pair<String, Double>> getSingleTimeSeriesWeekly(String symbol, int sampleSize) {
            return new java.util.ArrayList<>();
        }

        @Override
        public java.util.Map<String, java.util.List<kotlin.Pair<String, Double>>> getBulkTimeSeriesIntraDay(Portfolio portfolio, int sampleSize, int interval) {
            return new java.util.HashMap<>();
        }

        @Override
        public java.util.Map<String, java.util.List<kotlin.Pair<String, Double>>> getBulkTimeSeriesDaily(Portfolio portfolio, int sampleSize) {
            return new java.util.HashMap<>();
        }

        @Override
        public java.util.Map<String, java.util.List<kotlin.Pair<String, Double>>> getBulkTimeSeriesWeekly(Portfolio portfolio, int sampleSize) {
            return new java.util.HashMap<>();
        }
    }

    private static class TestRevenuePredictionOutputBoundary implements RevenuePredictionOutputBoundary {
        private RevenuePredictionOutputData lastSuccessData;
        private String lastErrorMessage;
        private boolean successViewCalled = false;
        private boolean failViewCalled = false;
        private boolean switchBackCalled = false;

        @Override
        public void prepareSuccessView(RevenuePredictionOutputData outputData) {
            this.lastSuccessData = outputData;
            this.successViewCalled = true;
        }

        @Override
        public void prepareFailView(String error) {
            this.lastErrorMessage = error;
            this.failViewCalled = true;
        }

        @Override
        public void switchBack() {
            this.switchBackCalled = true;
        }

        public void reset() {
            lastSuccessData = null;
            lastErrorMessage = null;
            successViewCalled = false;
            failViewCalled = false;
            switchBackCalled = false;
        }
    }

    // Instance variables
    private RevenuePredictionInteractor revenuePredictionInteractor;
    private TestRevenuePredictionOutputBoundary outputBoundary;
    private TestLocalDataAccess localDataAccess;
    private TestPredictModel predictModel;
    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio();
        localDataAccess = new TestLocalDataAccess(portfolio);
        predictModel = new TestPredictModel();
        outputBoundary = new TestRevenuePredictionOutputBoundary();
        revenuePredictionInteractor = new RevenuePredictionInteractor(outputBoundary, localDataAccess, predictModel);
    }

    @Test
    void testExecuteWithEmptyPortfolio() {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                "Average Model", 5, "day");

        revenuePredictionInteractor.execute(inputData);

        assertTrue(outputBoundary.failViewCalled, "Should fail with empty portfolio");
        assertTrue(outputBoundary.lastErrorMessage.contains("Portfolio is empty"),
                "Error message should mention empty portfolio");
    }

    @Test
    void testExecuteWithInvalidIntervalType() {
        portfolio.addStock("AAPL", 100, 150.0);
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                "Average Model", 5, "invalid_interval");

        revenuePredictionInteractor.execute(inputData);

        assertTrue(outputBoundary.failViewCalled, "Should fail with invalid interval");
        assertTrue(outputBoundary.lastErrorMessage.contains("Invalid"),
                "Error message should mention invalid interval type");
    }

    @Test
    void testExecuteWithValidDailyInterval() {
        portfolio.addStock("AAPL", 100, 150.0);
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                "Average Model", 5, "day");

        revenuePredictionInteractor.execute(inputData);

        assertTrue(outputBoundary.successViewCalled, "Should succeed with valid daily interval");
        assertNotNull(outputBoundary.lastSuccessData, "Success data should not be null");
        assertEquals("day", outputBoundary.lastSuccessData.getIntervalName());
    }

    @Test
    void testSwitchBack() {
        revenuePredictionInteractor.switchBack();
        assertTrue(outputBoundary.switchBackCalled, "switchBack should be called on the output boundary");
    }
}