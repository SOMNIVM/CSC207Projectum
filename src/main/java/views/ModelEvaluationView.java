package views;


import interface_adapters.model_evaluation.ModelEvaluationController;
import interface_adapters.model_evaluation.ModelEvaluationState;
import interface_adapters.model_evaluation.ModelEvaluationViewModel;
import interface_adapters.ViewManagerModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

/**
 * The view component for revenue prediction.
 * Provides a user interface for selecting prediction models and parameters,
 * and displays prediction results.
 */
public class ModelEvaluationView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final ModelEvaluationViewModel modelEvaluationViewModel;
    private ModelEvaluationController modelEvaluationController;
    private final ViewManagerModel viewManagerModel;
    private final JLabel errorMessageLabel;
    private final JComboBox<String> modelTypeComboBox;
    private final JComboBox<String> intervalTypeComboBox;
    private final JLabel resultLabel;
    private JTable resultTable; // Instance variable for the table

    /**
     * Constructs a new RevenuePredictionView.
     *
     * @param viewModel The view model containing the state and display logic
     * @param managerModel The view manager model for handling view transitions
     */
    public ModelEvaluationView(ModelEvaluationViewModel viewModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.modelEvaluationViewModel = viewModel;
        this.viewManagerModel = managerModel;
        this.viewName = viewModel.getViewName();
        this.modelEvaluationViewModel.
        addPropertyChangeListener(this);

        // Initialize components
        JLabel title = new JLabel(ModelEvaluationViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Model Type Selection
        JPanel modelTypePanel = new JPanel();
        modelTypePanel.add(new JLabel(ModelEvaluationViewModel.MODEL_NAME_TYPE_LABEL));
        this.modelTypeComboBox = new JComboBox<>(ModelEvaluationViewModel.MODEL_NAME_OPTIONS);
        modelTypePanel.add(modelTypeComboBox);
        modelTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Interval Type Selection
        JPanel intervalTypePanel = new JPanel();
        intervalTypePanel.add(new JLabel(ModelEvaluationViewModel.FREQUENCY_LABEL));
        this.intervalTypeComboBox = new JComboBox<>(ModelEvaluationViewModel.FREQUENCY_OPTIONS);
        intervalTypePanel.add(this.intervalTypeComboBox); // Add this line
        intervalTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Error Message Label
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setForeground(Color.RED);
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Label
        this.resultLabel = new JLabel();
        this.resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton evaluationButton = new JButton(ModelEvaluationViewModel.PROCEED_EVALUATION_BUTTON_LABEL);
        JButton backButton = new JButton(ModelEvaluationViewModel.BACK_BUTTON_LABEL);
        buttonPanel.add(evaluationButton);
        buttonPanel.add(backButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel
        this.add(Box.createVerticalStrut(10));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(modelTypePanel);
        this.add(Box.createVerticalStrut(10));
        this.add(intervalTypePanel);
        this.add(Box.createVerticalStrut(10));
        this.add(errorMessageLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(resultLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);
        intervalTypeComboBox.setVisible(true);
        modelTypeComboBox.setVisible(true);
        errorMessageLabel.setVisible(true);
        modelTypePanel.setVisible(true);
        intervalTypePanel.setVisible(true);
        evaluationButton.setVisible(true);
        // Add button listeners
        evaluationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modelType = (String) modelTypeComboBox.getSelectedItem();
                String intervalType = (String) intervalTypeComboBox.getSelectedItem();
                modelEvaluationController.execute(modelType, intervalType);
                System.out.println("Evaluation button clicked");
                modelTypePanel.setVisible(false);
                intervalTypePanel.setVisible(false);
                evaluationButton.setVisible(false);
                }
            });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelEvaluationController.switchBack();
                System.out.println("Back button clicked");
                removeResultTable(); // Ensure no table is displayed when navigating back
                modelTypePanel.setVisible(true);
                intervalTypePanel.setVisible(true);
                evaluationButton.setVisible(true);
                intervalTypeComboBox.setVisible(true);
                modelTypeComboBox.setVisible(true);
                errorMessageLabel.setVisible(true);
            }
        });
    }

    /**
     * Handles property change events from the view model.
     * Updates the UI based on changes to the model state.
     *
     * @param evt The property change event containing the updated state
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ModelEvaluationState state = (ModelEvaluationState) evt.getNewValue();
        if (state.isValid()) {
            removeResultTable(); // Remove existing table before adding a new one
            getTable(state);
            errorMessageLabel.setText(state.getError());
            resultLabel.setText("");
            intervalTypeComboBox.setVisible(false);
            modelTypeComboBox.setVisible(false);
            errorMessageLabel.setVisible(false);
        } else {
            removeResultTable(); // Ensure no table is displayed on error
            errorMessageLabel.setText(state.getError());
            resultLabel.setText("");
            intervalTypeComboBox.setVisible(false);
            modelTypeComboBox.setVisible(false);
            errorMessageLabel.setVisible(true);
        }
    }

    /**
     * Creates and adds the result table to the view.
     *
     * @param state The current state containing evaluation metrics
     */
    private void getTable(ModelEvaluationState state) {
        // Initialize the table if it's not already created
        if (resultTable == null) {
            resultTable = new JTable();
            JScrollPane scrollPane = new JScrollPane(resultTable);
            scrollPane.setPreferredSize(new Dimension(400, 200));
            this.add(scrollPane);
        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Metric");
        tableModel.addColumn("Value");

        // Format numbers to 4 decimal places
        DecimalFormat df = new DecimalFormat("#.####");

        // Add rows with metrics
        tableModel.addRow(new Object[]{"Predicted portfolio value", df.format(state.getPredictedPrice())});
        tableModel.addRow(new Object[]{"Actual portfolio value", df.format(state.getActualPrice())});
        tableModel.addRow(new Object[]{"Mean Absolute Error", df.format(state.getMeanAbsoluteError())});
        tableModel.addRow(new Object[]{"Mean Squared Error", df.format(state.getMeanSquaredError())});
        tableModel.addRow(new Object[]{"Sharpe Ratio", df.format(state.getSharpeRatio())});

        // Set model and styling
        resultTable.setModel(tableModel);
        resultTable.setEnabled(false); // Make table non-editable
        resultTable.setShowGrid(true);
        resultTable.setIntercellSpacing(new Dimension(10, 1));

        // Refresh the UI
        this.revalidate();
        this.repaint();
    }

    /**
     * Helper method to remove the result table from the view.
     * Ensures that previous results are cleared when navigating back.
     */
    public void removeResultTable() {
        if (resultTable != null) {
            // Remove the table's parent scroll pane
            JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, resultTable);
            if (scrollPane != null) {
                this.remove(scrollPane);
            }
            resultTable = null;
            // Refresh the UI
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to handle user interactions
     */
    public void setModelEvaluationController(ModelEvaluationController controller) {
        this.modelEvaluationController = controller;
    }
    /**
     * Gets the name of this view.
     *
     * @return the view name as defined in the view model
     */
    public String getViewName() {
        return viewName;
    }
}
