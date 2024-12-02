package views;

import interface_adapters.ModelEvaluation.ModelEvaluationController;
import interface_adapters.ModelEvaluation.ModelResultViewModel;
import interface_adapters.ModelEvaluation.ModelResultState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ModelResultView extends JPanel {
    private final String viewName;
    private final ModelResultViewModel modelResultViewModel;
    private final ModelEvaluationController controller;
    private final JTable resultTable;
    private final DefaultTableModel tableModel;

    public ModelResultView(ModelResultViewModel modelResultViewModel, ModelEvaluationController controller) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.modelResultViewModel = modelResultViewModel;
        this.controller = controller;
        this.viewName = modelResultViewModel.getViewName();

        // Create title
        JLabel title = new JLabel("Model Evaluation Results");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        // Initialize table with column headers
        String[] columnNames = {"Metric", "Value"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);

        // Style the table
        resultTable.setRowHeight(30);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(scrollPane);

        // Populate table with current state
        if (modelResultViewModel.getState() != null) {
            updateTable(modelResultViewModel.getState());
        }

        // Add back button
        JButton backButton = new JButton(modelResultViewModel.BACK_BUTTON_LABEL);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> controller.switchBack());
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(backButton);
    }
    public String getViewName() {
        return viewName;
    }
    public void setModuleEvaluationController(ModelEvaluationController controller) {

        controller = controller;
    }

    private void updateTable(ModelResultState state) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add rows with metrics
        tableModel.addRow(new Object[]{"Model Name", state.getModelName()});
        tableModel.addRow(new Object[]{"Frequency", state.getFrequency()});
        tableModel.addRow(new Object[]{"Data Length", state.getLength()});
        tableModel.addRow(new Object[]{"Mean Squared Error (MSE)", 
            String.format("%.4f", state.getMeanSquaredError())});
        tableModel.addRow(new Object[]{"Mean Absolute Error (MAE)", 
            String.format("%.4f", state.getMeanAbsoluteError())});
        tableModel.addRow(new Object[]{"Sharpe Ratio", 
            String.format("%.4f", state.getSharpeRatio())});
        tableModel.addRow(new Object[]{"Predicted Price", 
            String.format("$%.2f", state.getPredictedPrice())});
        tableModel.addRow(new Object[]{"Actual Price", 
            String.format("$%.2f", state.getActualPrice())});

        if (!state.isValid()) {
            tableModel.addRow(new Object[]{"Error", state.getError()});
        }

        // Refresh table
        tableModel.fireTableDataChanged();
    }
}