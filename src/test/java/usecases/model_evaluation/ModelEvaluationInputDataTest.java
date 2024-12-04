package usecases.model_evaluation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelEvaluationInputDataTest {

    @Test
    void constructorShouldInitializeFields() {
        ModelEvaluationInputData data = new ModelEvaluationInputData("TypeA", "Daily", 30);
        assertEquals("TypeA", data.getModelType());
        assertEquals("Daily", data.getFrequency());
        assertEquals(30, data.getLength());
    }

    @Test
    void getModelTypeShouldReturnCorrectValue() {
        ModelEvaluationInputData data = new ModelEvaluationInputData("TypeB", "Weekly", 15);
        assertEquals("TypeB", data.getModelType());
    }

    @Test
    void getFrequencyShouldReturnCorrectValue() {
        ModelEvaluationInputData data = new ModelEvaluationInputData("TypeC", "Monthly", 45);
        assertEquals("Monthly", data.getFrequency());
    }

    @Test
    void getLengthShouldReturnCorrectValue() {
        ModelEvaluationInputData data = new ModelEvaluationInputData("TypeD", "Yearly", 365);
        assertEquals(365, data.getLength());
    }

    @Test
    void constructorShouldHandleEmptyStrings() {
        ModelEvaluationInputData data = new ModelEvaluationInputData("", "", 0);
        assertEquals("", data.getModelType());
        assertEquals("", data.getFrequency());
        assertEquals(0, data.getLength());
    }

    @Test
    void constructorShouldHandleNullValues() {
        ModelEvaluationInputData data = new ModelEvaluationInputData(null, null, 0);
        assertNull(data.getModelType());
        assertNull(data.getFrequency());
        assertEquals(0, data.getLength());
    }
}