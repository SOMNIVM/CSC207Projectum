package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.revenue_prediction.RevenuePredictionController;
import interface_adapters.revenue_prediction.RevenuePredictionState;
import interface_adapters.revenue_prediction.RevenuePredictionViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    /**
     * Constructs a new RevenuePredictionView.
     *
     * @param viewModel The view model containing the state and display logic
     * @param managerModel The view manager model for handling view transitions
     */
    public RevenuePredictionView(RevenuePredictionViewModel viewModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.revenuePredictionViewModel = viewModel;
        this.viewManagerModel = managerModel;
        this.viewName = viewModel.getViewName();
        this.revenuePredictionViewModel.addPropertyChangeListener(this);

        // Initialize components
        JLabel title = new JLabel(RevenuePredictionViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Model Type Selection
        JPanel modelTypePanel = new JPanel();
        modelTypePanel.add(new JLabel(RevenuePredictionViewModel.MODEL_TYPE_LABEL));
        this.modelTypeComboBox = new JComboBox<>(RevenuePredictionViewModel.MODEL_OPTIONS);
        modelTypePanel.add(modelTypeComboBox);
        modelTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Interval Type Selection
        JPanel intervalTypePanel = new JPanel();
        intervalTypePanel.add(new JLabel(RevenuePredictionViewModel.INTERVAL_TYPE_LABEL));
        this.intervalTypeComboBox = new JComboBox<>(RevenuePredictionViewModel.INTERVAL_OPTIONS);
        intervalTypePanel.add(intervalTypeComboBox);
        intervalTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Interval Length Input
        JPanel intervalLengthPanel = new JPanel();
        intervalLengthPanel.add(new JLabel(RevenuePredictionViewModel.INTERVAL_LENGTH_LABEL));
        this.intervalLengthField = new JTextField(10);
        intervalLengthPanel.add(intervalLengthField);
        intervalLengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Error Message Label
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setForeground(Color.RED);
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Label
        this.resultLabel = new JLabel();
        this.resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton predictButton = new JButton(RevenuePredictionViewModel.PREDICT_BUTTON_LABEL);
        JButton backButton = new JButton(RevenuePredictionViewModel.BACK_BUTTON_LABEL);
        buttonPanel.add(predictButton);
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
        this.add(intervalLengthPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(errorMessageLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(resultLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);

        // Add button listeners
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (revenuePredictionController != null) {
                    try {
                        String modelType = (String) modelTypeComboBox.getSelectedItem();
                        String intervalType = (String) intervalTypeComboBox.getSelectedItem();
                        String intervalLengthText = intervalLengthField.getText().trim();

                        if (!intervalLengthText.matches("^[0-9]+$")) {
                            revenuePredictionViewModel.getState().setAsInvalid(
                                    "Interval length must be a positive integer.");
                            revenuePredictionViewModel.firePropertyChange();
                            return;
                        }

                        int intervalLength = Integer.parseInt(intervalLengthText);

                        if (intervalLength <= 0) {
                            revenuePredictionViewModel.getState().setAsInvalid(
                                    "Interval length must be greater than 0.");
                            revenuePredictionViewModel.firePropertyChange();
                            return;
                        }

                        revenuePredictionController.execute(modelType, intervalLength, intervalType);
                    } catch (NumberFormatException ex) {
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

    /**
     * Handles property change events from the view model.
     * Updates the UI based on changes to the model state.
     *
     * @param evt The property change event containing the updated state
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RevenuePredictionState state = (RevenuePredictionState) evt.getNewValue();
        if (state.checkIfValid()) {
            errorMessageLabel.setText("");
            resultLabel.setText(String.format(
                    "<html>Predicted Revenue: $%.2f<br>" +
                            "%.0f%% Confidence Interval:<br>" +
                            "[$%.2f, $%.2f]</html>",
                    state.getPredictedRevenue(),
                    state.getConfidenceLevel() * 100,
                    state.getLowerBound(),
                    state.getUpperBound()
            ));
        } else {
            errorMessageLabel.setText(state.getErrorMessage());
            resultLabel.setText("");
        }
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
