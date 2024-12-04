package usecases.revenue_prediction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionOutputBoundaryTest {

    private static class TestRevenuePredictionOutputBoundary implements RevenuePredictionOutputBoundary {
        private RevenuePredictionOutputData outputData;
        private String errorMessage;
        private boolean successViewCalled = false;
        private boolean failViewCalled = false;
        private boolean switchBackCalled = false;

        @Override
        public void prepareSuccessView(RevenuePredictionOutputData data) {
            this.outputData = data;
            this.successViewCalled = true;
        }

        @Override
        public void prepareFailView(String error) {
            this.errorMessage = error;
            this.failViewCalled = true;
        }

        @Override
        public void switchBack() {
            this.switchBackCalled = true;
        }

        // Getters for testing
        public RevenuePredictionOutputData getOutputData() {
            return outputData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean isSuccessViewCalled() {
            return successViewCalled;
        }

        public boolean isFailViewCalled() {
            return failViewCalled;
        }

        public boolean isSwitchBackCalled() {
            return switchBackCalled;
        }

        public void reset() {
            outputData = null;
            errorMessage = null;
            successViewCalled = false;
            failViewCalled = false;
            switchBackCalled = false;
        }
    }

    private TestRevenuePredictionOutputBoundary outputBoundary;

    @BeforeEach
    void setUp() {
        outputBoundary = new TestRevenuePredictionOutputBoundary();
    }

    @Test
    void testPrepareSuccessView() {
        RevenuePredictionOutputData testData = new RevenuePredictionOutputData(
                100.0,  // predictedRevenue
                90.0,   // lowerBound
                110.0,  // upperBound
                5,      // intervalLength
                "day",  // intervalName
                0.95    // confidenceLevel
        );

        outputBoundary.prepareSuccessView(testData);

        assertTrue(outputBoundary.isSuccessViewCalled());
        assertNotNull(outputBoundary.getOutputData());
        assertEquals(100.0, outputBoundary.getOutputData().getPredictedRevenue());
        assertEquals(90.0, outputBoundary.getOutputData().getLowerBound());
        assertEquals(110.0, outputBoundary.getOutputData().getUpperBound());
        assertEquals(5, outputBoundary.getOutputData().getIntervalLength());
        assertEquals("day", outputBoundary.getOutputData().getIntervalName());
        assertEquals(0.95, outputBoundary.getOutputData().getConfidenceLevel());
    }

    @Test
    void testPrepareFailView() {
        String errorMessage = "Test error message";
        outputBoundary.prepareFailView(errorMessage);

        assertTrue(outputBoundary.isFailViewCalled());
        assertEquals(errorMessage, outputBoundary.getErrorMessage());
    }

    @Test
    void testSwitchBack() {
        outputBoundary.switchBack();
        assertTrue(outputBoundary.isSwitchBackCalled());
    }
}