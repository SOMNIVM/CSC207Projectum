package usecases.revenue_prediction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionInputDataTest {

    @Test
    void testConstructorAndGetters() {
        String modelName = "Average Model";
        int intervalLength = 5;
        String intervalName = "day";

        RevenuePredictionInputData inputData = new RevenuePredictionInputData(modelName, intervalLength, intervalName);

        assertEquals(modelName, inputData.getModelName(), "Model name should match constructor input");
        assertEquals(intervalLength, inputData.getIntervalLength(), "Interval length should match constructor input");
        assertEquals(intervalName, inputData.getIntervalName(), "Interval name should match constructor input");
    }

    @Test
    void testWithNullModelName() {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData(null, 5, "day");
        assertNull(inputData.getModelName(), "Model name should be null");
        assertEquals(5, inputData.getIntervalLength(), "Interval length should still be valid");
        assertEquals("day", inputData.getIntervalName(), "Interval name should still be valid");
    }

    @Test
    void testWithNullIntervalName() {
        RevenuePredictionInputData inputData = new RevenuePredictionInputData("Average Model", 5, null);
        assertEquals("Average Model", inputData.getModelName(), "Model name should be valid");
        assertEquals(5, inputData.getIntervalLength(), "Interval length should be valid");
        assertNull(inputData.getIntervalName(), "Interval name should be null");
    }
}