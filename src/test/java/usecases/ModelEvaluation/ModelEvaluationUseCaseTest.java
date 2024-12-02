package usecases.ModelEvaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Portfolio;
import kotlin.Pair;
import java.util.*;
import usecases.LocalDataAccessInterface;

public class ModelEvaluationUseCaseTest {

    private ModelEvaluationInteractor interactor;
    private MockDataAccess dataAccess;
    private MockPresenter presenter;
    private MockLocalDataAccess localDataAccess;

    @BeforeEach
    public void setUp() {
        dataAccess = new MockDataAccess();
        presenter = new MockPresenter();
        localDataAccess = new MockLocalDataAccess();
        interactor = new ModelEvaluationInteractor(dataAccess, presenter, localDataAccess, 5, "avgModel");
    }

    @Test
    public void testModelEvaluationSuccess() {
        ModelEvaluationInputData inputData = new ModelEvaluationInputData("avgModel", "Daily", 5);
        interactor.execute(inputData);
        assertTrue(presenter.isSuccess);
        assertNotNull(presenter.outputData);
        assertEquals("avgModel", presenter.outputData.getModelName());
        assertEquals("Daily", presenter.outputData.getFrequency());
        assertEquals(5, presenter.outputData.getLength());
        // Additional assertions for metrics can be added here
        // For example:
        // assertEquals(expectedMeanSquaredError, presenter.outputData.getMeanSquaredError(), 0.001);
    }

    @Test
    public void testModelEvaluationFailureInvalidModelType() {
        ModelEvaluationInputData inputData = new ModelEvaluationInputData("invalidModel", "Daily", 5);
        interactor.execute(inputData);
        assertFalse(presenter.isSuccess);
        assertEquals("Invalid model type: invalidModel", presenter.errorMessage);
    }

    // Mock implementations
    class MockDataAccess implements ModelEvaluationDataAccessInterface {

        @Override
        public Map<String, List<Pair<String, Double>>> getHistoricalPrices(Portfolio portfolio, int numOfInterval) {
            Map<String, List<Pair<String, Double>>> prices = new HashMap<>();
            List<Pair<String, Double>> priceList = new ArrayList<>();

            // Populate priceList with dummy data matching numOfInterval
            for (int i = 0; i < numOfInterval; i++) {
                priceList.add(new Pair<>("2023-01-0" + (i + 1), 100.0 + i));
            }

            prices.put("DummyStock", priceList);
            return prices;
        }
    }

    class MockPresenter implements ModelEvaluationOutputBoundary {
        boolean isSuccess = false;
        String errorMessage = null;
        ModelEvaluationOutputData outputData;

        @Override
        public void prepareSuccessView(ModelEvaluationOutputData outputData) {
            isSuccess = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            isSuccess = false;
            this.errorMessage = errorMessage;
        }
    }

    class MockLocalDataAccess implements LocalDataAccessInterface {
        @Override
        public Portfolio getCurrentPortfolio() {
            Portfolio portfolio = new Portfolio();
            portfolio.addStock("AAPL", 10, 100.0);
            return portfolio;
        }

        @Override
        public void writeCurrentPortfolio() {
            // No operation needed for testing
        }

        @Override
        public Map<String, String> getNameToSymbolMap() {
            // Return empty map for testing
            return new HashMap<>();
        }

        @Override
        public Map<String, String> getSymbolToNameMap() {
            // Return empty map for testing
            return new HashMap<>();
        }
    }
}