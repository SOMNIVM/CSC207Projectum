package views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapters.ModifyPortfolioState;
import interface_adapters.remove_stock.RemoveStockController;
import interface_adapters.remove_stock.RemoveStockViewModel;

/**
 * The view for stock removal.
 */
public class RemoveStockView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final RemoveStockViewModel removeStockViewModel;
    private RemoveStockController removeStockController;
    private final JLabel errorMessageLabel;
    private final StockInputPanel stockInputBox;
    private final JTextField sharesField;

    public RemoveStockView(RemoveStockViewModel removeStockModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.removeStockViewModel = removeStockModel;
        this.viewName = this.removeStockViewModel.getViewName();
        this.removeStockViewModel.addPropertyChangeListener(this);
        this.stockInputBox = new StockInputPanel();
        this.sharesField = new JTextField(RemoveStockViewModel.TEXTFIELD_COLS);
        this.sharesField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(RemoveStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockInputBox);
        stockNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(RemoveStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        sharesFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel buttonPanel = new JPanel();
        final JButton removeStock = new JButton(RemoveStockViewModel.REMOVE_STOCK_BUTTON_LABEL);
        final JButton cancel = new JButton(RemoveStockViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(removeStock);
        buttonPanel.add(cancel);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addComponentsToView(title, stockNamePanel, sharesFieldPanel, buttonPanel);
        addActionListenersToButtons(removeStock, cancel);
    }

    private void addComponentsToView(JLabel title, JPanel stockNamePanel, JPanel sharesFieldPanel, JPanel buttonPanel) {
        this.add(title);
        this.add(stockNamePanel);
        this.add(sharesFieldPanel);
        this.add(this.errorMessageLabel);
        this.add(buttonPanel);
    }

    private void addActionListenersToButtons(JButton removeStock, JButton cancel) {
        removeStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (removeStockController != null) {
                    final String stockNameInput = stockInputBox.getText();
                    final String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        final int shares = Integer.parseInt(sharesInput);
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
                if (removeStockController != null) {
                    removeStockController.switchBack();
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ModifyPortfolioState state = (ModifyPortfolioState) evt.getNewValue();
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
