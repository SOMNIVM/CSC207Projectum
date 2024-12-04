package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import interface_adapters.ViewManagerModel;
import interface_adapters.revenue_prediction.RevenuePredictionController;
import interface_adapters.revenue_prediction.RevenuePredictionState;
import interface_adapters.revenue_prediction.RevenuePredictionViewModel;

/**
 * The view component for revenue prediction.
 * Provides a user interface for selecting prediction models and parameters,
 * and displays prediction results.
 */
public class RevenuePredictionView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final RevenuePredictionViewModel revenuePredictionViewModel;
    private RevenuePredictionController revenuePredictionController;
    private final ViewManagerModel viewManagerModel;
    private final JLabel errorMessageLabel;
    private final JComboBox<String> modelTypeComboBox;
    private final JComboBox<String> intervalTypeComboBox;
    private final JTextField intervalLengthField;
    private final JLabel resultLabel;
    private final JScrollPane scrollPane;

    /**
     * Constructs a new RevenuePredictionView.
     *
     * @param viewModel The view model containing the state and display logic
     * @param managerModel The view manager model for handling view transitions
     */
    public RevenuePredictionView(RevenuePredictionViewModel viewModel, ViewManagerModel managerModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.revenuePredictionViewModel = viewModel;
        this.viewManagerModel = managerModel;
        this.viewName = viewModel.getViewName();
        this.revenuePredictionViewModel.addPropertyChangeListener(this);
        this.modelTypeComboBox = new JComboBox<>(RevenuePredictionViewModel.MODEL_OPTIONS);
        this.intervalTypeComboBox = new JComboBox<>(RevenuePredictionViewModel.INTERVAL_OPTIONS);
        this.intervalLengthField = new JTextField(RevenuePredictionViewModel.TEXTFIELD_COL);
        this.errorMessageLabel = new JLabel();
        this.resultLabel = new JLabel();
        this.scrollPane = new JScrollPane();

        // Initialize components
        final JLabel title = new JLabel(RevenuePredictionViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Model Type Selection
        final JPanel modelTypePanel = new JPanel();
        configureModelTypePanel(modelTypePanel);

        // Interval Type Selection
        final JPanel intervalTypePanel = new JPanel();
        configureIntervalTypePanel(intervalTypePanel);

        // Interval Length Input
        final JPanel intervalLengthPanel = new JPanel();
        configureIntervalLengthPanel(intervalLengthPanel);

        // Button Panel
        final JPanel buttonPanel = new JPanel();
        final JButton predictButton = new JButton(RevenuePredictionViewModel.PREDICT_BUTTON_LABEL);
        final JButton backButton = new JButton(RevenuePredictionViewModel.BACK_BUTTON_LABEL);
        configureButtonPanel(buttonPanel, predictButton, backButton);

        // Set the appearance of components
        setAppearance();

        // Add components to panel
        addComponentsToView(title, modelTypePanel, intervalTypePanel, intervalLengthPanel, buttonPanel);
        addActionListenersToButtons(predictButton, backButton);
    }

    private void configureModelTypePanel(JPanel modelTypePanel) {
        modelTypePanel.add(new JLabel(RevenuePredictionViewModel.MODEL_TYPE_LABEL));
        modelTypePanel.add(modelTypeComboBox);
        modelTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void configureIntervalTypePanel(JPanel intervalTypePanel) {
        intervalTypePanel.add(new JLabel(RevenuePredictionViewModel.INTERVAL_TYPE_LABEL));
        intervalTypePanel.add(intervalTypeComboBox);
        intervalTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void configureIntervalLengthPanel(JPanel intervalLengthPanel) {
        intervalLengthPanel.add(new JLabel(RevenuePredictionViewModel.INTERVAL_LENGTH_LABEL));
        intervalLengthPanel.add(intervalLengthField);
        intervalLengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void configureButtonPanel(JPanel buttonPanel, JButton predictButton, JButton backButton) {
        buttonPanel.add(predictButton);
        buttonPanel.add(backButton);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void setAppearance() {
        // Error Message Label
        this.errorMessageLabel.setForeground(Color.RED);
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Result Label
        this.resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Scroll Pane
        this.scrollPane.setPreferredSize(new Dimension(
                RevenuePredictionViewModel.SCROLLPANE_WIDTH,
                RevenuePredictionViewModel.SCROLLPANE_HEIGHT));
    }

    private void addComponentsToView(JLabel title,
                                     JPanel modelTypePanel,
                                     JPanel intervalTypePanel,
                                     JPanel intervalLengthPanel,
                                     JPanel buttonPanel) {
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_NARROW));
        this.add(title);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_WIDE));
        this.add(modelTypePanel);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_NARROW));
        this.add(intervalTypePanel);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_NARROW));
        this.add(intervalLengthPanel);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_NARROW));
        this.add(errorMessageLabel);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_NARROW));
        this.add(resultLabel);
        this.add(Box.createVerticalStrut(RevenuePredictionViewModel.SEPARATION_WIDE));
        this.add(buttonPanel);
        this.add(this.scrollPane);
    }

    private void addActionListenersToButtons(JButton predictButton, JButton backButton) {
        // Add button listeners
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (revenuePredictionController != null) {
                    try {
                        final String modelType = (String) modelTypeComboBox.getSelectedItem();
                        final String intervalType = (String) intervalTypeComboBox.getSelectedItem();
                        final String intervalLengthText = intervalLengthField.getText().trim();

                        handleInput(intervalLengthText, modelType, intervalType);
                    }
                    catch (NumberFormatException ex) {
                        revenuePredictionViewModel.getState().setAsInvalid(
                                "Invalid interval length format.");
                        revenuePredictionViewModel.firePropertyChange();
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.getState().setCurViewName("homepage");
                viewManagerModel.firePropertyChange();
            }
        });
    }

    private void handleInput(String intervalLengthText, String modelType, String intervalType) {
        if (!intervalLengthText.matches("^[0-9]+$")) {
            revenuePredictionViewModel.getState().setAsInvalid(
                    "Interval length must be a positive integer.");
            revenuePredictionViewModel.firePropertyChange();
        }
        else {
            final int intervalLength = Integer.parseInt(intervalLengthText);

            if (intervalLength <= 0) {
                revenuePredictionViewModel.getState().setAsInvalid(
                        "Interval length must be greater than 0.");
                revenuePredictionViewModel.firePropertyChange();
            }
            else {
                revenuePredictionController.execute(modelType, intervalLength, intervalType);
            }
        }
    }

    /**
     * Handles property change events from the view model.
     * Updates the UI based on changes to the model state.
     *
     * @param evt The property change event containing the updated state
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RevenuePredictionState state = (RevenuePredictionState) evt.getNewValue();
        if (state.checkIfValid()) {
            errorMessageLabel.setText("");
            getTable(state);
        }
        else {
            errorMessageLabel.setText(state.getErrorMessage());
            resultLabel.setText("");
        }
    }

    private void getTable(RevenuePredictionState state) {
        // Create table model and add columns
        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Metric");
        tableModel.addColumn("Value");

        // Add data rows
        tableModel.addRow(new Object[]{"Predicted Revenue", String.format("$%.2f", state.getPredictedRevenue())});
        tableModel.addRow(new Object[]{"Confidence Level", String.format(
                "%.0f%%", state.getConfidenceLevel() * RevenuePredictionViewModel.REVERSE_PERCENT_MULTIPLIER)});
        tableModel.addRow(new Object[]{"Confidence Interval", String.format(
                "[$%.2f, $%.2f]",
                state.getLowerBound(),
                state.getUpperBound())});

        // Set the model to the table
        final JTable resultTable = new JTable(tableModel);
        this.scrollPane.setViewportView(resultTable);
    }

    /**
     * Sets the controller for this view.
     *
     * @param controller The controller to handle user interactions
     */
    public void setRevenuePredictionController(RevenuePredictionController controller) {
        this.revenuePredictionController = controller;
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
