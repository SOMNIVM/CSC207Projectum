package usecases.revenue_prediction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionOutputBoundaryTest {

    private static class TestOutputBoundary implements RevenuePredictionOutputBoundary {
        private RevenuePredictionOutputData lastOutputData;
        private String lastErrorMessage;
        private boolean successViewCalled = false;
        private boolean failViewCalled = false;

        @Override
        public void prepareSuccessView(RevenuePredictionOutputData outputData) {
            this.lastOutputData = outputData;
            this.successViewCalled = true;
        }

        @Override
        public void prepareFailView(String error) {
            this.lastErrorMessage = error;
            this.failViewCalled = true;
        }
    }

    @Test
    void testPrepareSuccessView() {
        TestOutputBoundary outputBoundary = new TestOutputBoundary();
        RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(1000.0, 900.0, 1100.0, 5, "day", 0.95);

        outputBoundary.prepareSuccessView(outputData);

        assertTrue(outputBoundary.successViewCalled);
        assertSame(outputData, outputBoundary.lastOutputData);
    }

    @Test
    void testPrepareFailView() {
        TestOutputBoundary outputBoundary = new TestOutputBoundary();
        String errorMessage = "Test error message";

        outputBoundary.prepareFailView(errorMessage);

        assertTrue(outputBoundary.failViewCalled);
        assertEquals(errorMessage, outputBoundary.lastErrorMessage);
    }
}