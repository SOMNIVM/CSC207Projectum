
package usecases.model_evaluation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelEvaluationOutputDataTest {

    @Test
    void constructorShouldInitializeFields() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelA", "Daily", 30, 0.5, 0.3, 1.2, 100.0, 95.0);
        assertEquals("ModelA", data.getModelName());
        assertEquals("Daily", data.getFrequency());
        assertEquals(30, data.getLength());
        assertEquals(0.5, data.getMeanSquaredError());
        assertEquals(0.3, data.getMeanAbsoluteError());
        assertEquals(1.2, data.getSharpRatio());
        assertEquals(100.0, data.getPredictedPrice());
        assertEquals(95.0, data.getActualPrice());
    }

    @Test
    void getModelNameShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelB", "Weekly", 15, 0.4, 0.2, 1.1, 110.0, 105.0);
        assertEquals("ModelB", data.getModelName());
    }

    @Test
    void getFrequencyShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelC", "Monthly", 45, 0.6, 0.4, 1.3, 120.0, 115.0);
        assertEquals("Monthly", data.getFrequency());
    }

    @Test
    void getLengthShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelD", "Yearly", 365, 0.7, 0.5, 1.4, 130.0, 125.0);
        assertEquals(365, data.getLength());
    }

    @Test
    void getMeanSquaredErrorShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelE", "Daily", 30, 0.8, 0.6, 1.5, 140.0, 135.0);
        assertEquals(0.8, data.getMeanSquaredError());
    }

    @Test
    void getMeanAbsoluteErrorShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelF", "Weekly", 15, 0.9, 0.7, 1.6, 150.0, 145.0);
        assertEquals(0.7, data.getMeanAbsoluteError());
    }

    @Test
    void getSharpRatioShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelG", "Monthly", 45, 1.0, 0.8, 1.7, 160.0, 155.0);
        assertEquals(1.7, data.getSharpRatio());
    }

    @Test
    void getPredictedPriceShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelH", "Yearly", 365, 1.1, 0.9, 1.8, 170.0, 165.0);
        assertEquals(170.0, data.getPredictedPrice());
    }

    @Test
    void getActualPriceShouldReturnCorrectValue() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("ModelI", "Daily", 30, 1.2, 1.0, 1.9, 180.0, 175.0);
        assertEquals(175.0, data.getActualPrice());
    }

    @Test
    void constructorShouldHandleEmptyStrings() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData("", "", 0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertEquals("", data.getModelName());
        assertEquals("", data.getFrequency());
        assertEquals(0, data.getLength());
        assertEquals(0.0, data.getMeanSquaredError());
        assertEquals(0.0, data.getMeanAbsoluteError());
        assertEquals(0.0, data.getSharpRatio());
        assertEquals(0.0, data.getPredictedPrice());
        assertEquals(0.0, data.getActualPrice());
    }

    @Test
    void constructorShouldHandleNullValues() {
        ModelEvaluationOutputData data = new ModelEvaluationOutputData(null, null, 0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertNull(data.getModelName());
        assertNull(data.getFrequency());
        assertEquals(0, data.getLength());
        assertEquals(0.0, data.getMeanSquaredError());
        assertEquals(0.0, data.getMeanAbsoluteError());
        assertEquals(0.0, data.getSharpRatio());
        assertEquals(0.0, data.getPredictedPrice());
        assertEquals(0.0, data.getActualPrice());
    }
}