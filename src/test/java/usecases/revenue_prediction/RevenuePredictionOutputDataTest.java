package usecases.revenue_prediction;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevenuePredictionOutputDataTest {

    @Test
    void testConstructorAndGetters() {
        RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(1000.0, 900.0, 1100.0, 5, "day", 0.95);

        assertEquals(1000.0, outputData.getPredictedRevenue());
        assertEquals(900.0, outputData.getLowerBound());
        assertEquals(1100.0, outputData.getUpperBound());
        assertEquals(5, outputData.getIntervalLength());
        assertEquals("day", outputData.getIntervalName());
        assertEquals(0.95, outputData.getConfidenceLevel());
    }

    @Test
    void testGetFormattedMessage() {
        RevenuePredictionOutputData outputData = new RevenuePredictionOutputData(1000.0, 900.0, 1100.0, 5, "day", 0.95);

        String expectedMessage = String.format(
                "Predicted revenue after %d %s(s):%n" +
                        "Point estimate: $%.2f%n" +
                        "%.0f%% Confidence Interval: [$%.2f, $%.2f]",
                5, "day", 1000.0, 95.0, 900.0, 1100.0);

        assertEquals(expectedMessage, outputData.getFormattedMessage());
    }
}