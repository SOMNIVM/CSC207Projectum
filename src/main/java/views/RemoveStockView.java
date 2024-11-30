package views;

import interface_adapters.ModifyPortfolioState;
import interface_adapters.ViewManagerModel;
import interface_adapters.remove_stock.RemoveStockController;
import interface_adapters.remove_stock.RemoveStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RemoveStockView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final RemoveStockViewModel removeStockViewModel;
    private RemoveStockController removeStockController;
    private final ViewManagerModel viewManagerModel;
    private final JLabel errorMessageLabel;
    private final JTextField stockNameField;
    private final JTextField sharesField;
    public RemoveStockView(RemoveStockViewModel removeStockModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.removeStockViewModel = removeStockModel;
        this.viewName = this.removeStockViewModel.getViewName();
        this.viewManagerModel = managerModel;
        this.removeStockViewModel.addPropertyChangeListener(this);
        this.stockNameField = new JTextField(20);
        this.stockNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.sharesField = new JTextField(20);
        this.sharesField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(RemoveStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockNameField);
        stockNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(RemoveStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        sharesFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel buttonPanel = new JPanel();
        JButton removeStock = new JButton(RemoveStockViewModel.REMOVE_STOCK_BUTTON_LABEL);
        JButton cancel = new JButton(RemoveStockViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(removeStock);
        buttonPanel.add(cancel);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(stockNamePanel);
        this.add(sharesFieldPanel);
        this.add(this.errorMessageLabel);
        this.add(buttonPanel);
        removeStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (removeStockController != null) {
                    String stockNameInput = stockNameField.getText();
                    String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        int shares = Integer.parseInt(sharesInput);
                        removeStockController.execute(stockNameInput, shares);
                    }
                    else {
                        removeStockViewModel.getState()
                                .setAsInvalid("Shares to remove should be a non-negative integer.");
                        removeStockViewModel.firePropertyChange();
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("homepage");
                viewManagerModel.firePropertyChange();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ModifyPortfolioState state = (ModifyPortfolioState) evt.getNewValue();
        if (state.checkIfValid()) {
            errorMessageLabel.setText("");
            JOptionPane.showMessageDialog(null, String.format(
                    "You removed %d shares of %s stock from your portfolio",
                    -state.getSharesChanged(),
                    state.getStockName()));
        }
        else {
            errorMessageLabel.setText(state.getErrorMessage());
        }
    }
    public String getViewName() {
        return viewName;
    }
    public void setRemoveStockController(RemoveStockController controller) {
        removeStockController = controller;
    }
}
