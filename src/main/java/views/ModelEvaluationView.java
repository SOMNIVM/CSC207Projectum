// package views;
// import interface_adapters.ModelEvaluation.ModelEvaluationController;

// import java.beans.PropertyChangeListener;
// import interface_adapters.*;
// import interface_adapters.ModelEvaluation.ModelEvaluationViewModel;
// import interface_adapters.ModelEvaluation.ModelResultState;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;

// public class ModelEvaluationView extends JPanel implements PropertyChangeListener{
//     private final String viewName;
//     private final ModelEvaluationViewModel modelEvaluationViewModel;
//     private ModelEvaluationController modelEvaluationController;
//     private final JLabel errorMessageLabel;
//     private final JTextField modelTypeField;
//     private final JTextField frequencyField;
//     public ModelEvaluationView(ModelEvaluationViewModel modelEvaluationModel) {
//         super();
//         this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//         this.modelEvaluationViewModel = modelEvaluationModel;
//         this.viewName = this.modelEvaluationViewModel.getViewName();
//         this.modelEvaluationViewModel.addPropertyChangeListener(this);
//         this.modelTypeField = new JTextField(20);
//         this.modelTypeField.setAlignmentX(Component.CENTER_ALIGNMENT);
//         this.frequencyField = new JTextField(20);
//         this.frequencyField.setAlignmentX(Component.CENTER_ALIGNMENT);
//         this.errorMessageLabel = new JLabel();
//         this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JLabel title = new JLabel(this.viewName);
//         title.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JPanel modelTypePanel = new JPanel();
//         modelTypePanel.add(new JLabel(ModelEvaluationViewModel.MODEL_NAME_TYPE_LABEL));
//         JLabel modelNameOutput = new JLabel();
//         modelNameOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JLabel frequencyOutput = new JLabel();
//         frequencyOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JLabel meanSquaredErrorOutput = new JLabel();
//         meanSquaredErrorOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JLabel meanAbsoluteErrorOutput = new JLabel();
//         meanAbsoluteErrorOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
//         JLabel sharpRatioOutput = new JLabel();
//         sharpRatioOutput.setAlignmentX(Component.CENTER_ALIGNMENT);

//         // Add labels to the panel
//         this.add(new JLabel("Model Name:"));
//         this.add(modelNameOutput);
//         this.add(new JLabel("Frequency:"));
//         this.add(frequencyOutput);
//         this.add(new JLabel("Mean Squared Error:"));
//         this.add(meanSquaredErrorOutput);
//         this.add(new JLabel("Mean Absolute Error:"));
//         this.add(meanAbsoluteErrorOutput);
//         this.add(new JLabel("Sharpe Ratio:"));
//         this.add(sharpRatioOutput);

//         // Implement propertyChange to update labels
//         @Override
//         public void propertyChange(PropertyChangeEvent evt) {
//             ModuleResultState modelResultState = (ModuleResultState) evt.getNewValue();
//             }
// }
// }
package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.ModelEvaluation.ModelEvaluationController;
import interface_adapters.ModelEvaluation.ModelEvaluationViewModel;
import interface_adapters.ModelEvaluation.ModelResultState;
import interface_adapters.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ModelEvaluationView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final ModelEvaluationViewModel modelEvaluationViewModel;
    private ModelEvaluationController modelEvaluationController;
//    private final ViewManagerModel viewManagerModel;
    private final JComboBox<String> modelTypeComboBox;
    private final JComboBox<String> intervalTypeComboBox;
    // private final JLabel meanSquaredErrorLabel;
    // private final JLabel meanAbsoluteErrorLabel;
    // private final JLabel sharpeRatioLabel;
    // private final JLabel errorMessageLabel;
    // private final JLabel predictedValuLabel;
    // private final JLabel actualValueLabel;
    private final JButton evaluationProceedButton;

    pubglic ModelEvaluationView(ModelEvaluationViewModel modelEvaluationModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.modelEvaluationViewModel = modelEvaluationModel;
//        this.viewManagerModel = managerModel;
        this.viewName = this.modelEvaluationViewModel.getViewName();
        this.modelEvaluationViewModel.addPropertyChangeListener(this);
        // this.meanSquaredErrorLabel = new JLabel();
        // this.meanSquaredErrorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.meanAbsoluteErrorLabel = new JLabel();
        // this.meanAbsoluteErrorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.sharpeRatioLabel = new JLabel();
        // this.sharpeRatioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.errorMessageLabel = new JLabel();
        // this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.predictedValuLabel = new JLabel();
        // this.predictedValuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // this.actualValueLabel = new JLabel();
        // this.actualValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.evaluationProceedButton = new JButton();
        this.evaluationProceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize components
        JLabel title = new JLabel(ModelEvaluationViewModel.MODEL_NAME_TYPE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Interval Type Selection
        JPanel intervalTypePanel = new JPanel();
        intervalTypePanel.add(new JLabel(ModelEvaluationViewModel.FREQUENCY_LABEL));
        this.intervalTypeComboBox = new JComboBox<>(ModelEvaluationViewModel.FREQUENCY_OPTIONS.split(","));
        intervalTypePanel.add(intervalTypeComboBox);
        intervalTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        intervalTypeComboBox.addActionListener(actionEvent -> {
            String intervalType = (String) intervalTypeComboBox.getSelectedItem();
        }); 

        // Interval Length Input
        JPanel modelTypePanel = new JPanel();
        modelTypePanel.add(new JLabel(ModelEvaluationViewModel.MODEL_NAME_TYPE_LABEL));
        this.modelTypeComboBox = new JComboBox<>(ModelEvaluationViewModel.MODEL_NAME_OPTIONS.split(","));
        modelTypePanel.add(modelTypeComboBox);
        modelTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        modelTypeComboBox.addActionListener(actionEvent -> {
                String modelType = (String) modelTypeComboBox.getSelectedItem();
        });
        evaluationProceedButton.setText("Click to proceed");
        evaluationProceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (modelEvaluationController != null) {
                    modelEvaluationController.execute((String) modelTypeComboBox.getSelectedItem(), (String) intervalTypeComboBox.getSelectedItem());
                }
            }
        });
}



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // no action needed
    }

    public String getViewName() {return this.viewName;}
}



