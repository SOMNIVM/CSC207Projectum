package usecases.revenue_prediction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionInputBoundaryTest {

    private static class TestInputBoundary implements RevenuePredictionInputBoundary {
        private RevenuePredictionInputData lastInputData;
        private boolean executeCalled = false;

        @Override
        public void execute(RevenuePredictionInputData inputData) {
            this.lastInputData = inputData;
            this.executeCalled = true;
        }
    }

    @Test
    void testExecute() {
        TestInputBoundary inputBoundary = new TestInputBoundary();
        RevenuePredictionInputData inputData = new RevenuePredictionInputData("Average Model", 5, "day");

        inputBoundary.execute(inputData);

        assertTrue(inputBoundary.executeCalled, "Execute should have been called");
        assertSame(inputData, inputBoundary.lastInputData, "Input data should be stored");
        assertEquals("Average Model", inputBoundary.lastInputData.getModelName());
        assertEquals(5, inputBoundary.lastInputData.getIntervalLength());
        assertEquals("day", inputBoundary.lastInputData.getIntervalName());
    }

    @Test
    void testExecuteWithDifferentParameters() {
        TestInputBoundary inputBoundary = new TestInputBoundary();
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(
                "Linear Regression Model", 7, "week");

        inputBoundary.execute(inputData);

        assertTrue(inputBoundary.executeCalled, "Execute should have been called");
        assertEquals("Linear Regression Model", inputBoundary.lastInputData.getModelName());
        assertEquals(7, inputBoundary.lastInputData.getIntervalLength());
        assertEquals("week", inputBoundary.lastInputData.getIntervalName());
    }
}