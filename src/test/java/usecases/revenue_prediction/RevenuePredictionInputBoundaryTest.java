package usecases.revenue_prediction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionInputBoundaryTest {

    private TestRevenuePredictionInputBoundary inputBoundary;

    private static class TestRevenuePredictionInputBoundary implements RevenuePredictionInputBoundary {
        private boolean executeWasCalled = false;
        private boolean switchBackWasCalled = false;
        private RevenuePredictionInputData lastInputData = null;

        @Override
        public void execute(RevenuePredictionInputData inputData) {
            this.executeWasCalled = true;
            this.lastInputData = inputData;
        }

        @Override
        public void switchBack() {
            this.switchBackWasCalled = true;
        }

        public boolean wasExecuteCalled() {
            return executeWasCalled;
        }

        public boolean wasSwitchBackCalled() {
            return switchBackWasCalled;
        }

        public RevenuePredictionInputData getLastInputData() {
            return lastInputData;
        }
    }

    @BeforeEach
    void setUp() {
        inputBoundary = new TestRevenuePredictionInputBoundary();
    }

    @Test
    void testExecute() {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData("Average Model", 5, "day");
        inputBoundary.execute(inputData);

        assertTrue(inputBoundary.wasExecuteCalled(), "Execute method should have been called");
        assertSame(inputData, inputBoundary.getLastInputData(), "Input data should be passed to execute method");
    }

    @Test
    void testSwitchBack() {
        inputBoundary.switchBack();
        assertTrue(inputBoundary.wasSwitchBackCalled(), "SwitchBack method should have been called");
    }
}