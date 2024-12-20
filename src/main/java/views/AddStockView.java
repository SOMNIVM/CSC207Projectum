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

import interface_adapters.add_stock.AddStockController;
import interface_adapters.add_stock.AddStockState;
import interface_adapters.add_stock.AddStockViewModel;

/**
 * The view for the add stock use case.
 */
public class AddStockView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final AddStockViewModel addStockViewModel;
    private AddStockController addStockController;
    private final StockInputPanel stockInputPanel;
    private final JTextField sharesField;
    private final JLabel errorMessageLabel;

    public AddStockView(AddStockViewModel buyStockModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addStockViewModel = buyStockModel;
        this.viewName = buyStockModel.getViewName();
        this.addStockViewModel.addPropertyChangeListener(this);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.stockInputPanel = new StockInputPanel();
        this.sharesField = new JTextField(AddStockViewModel.TEXTFIELD_COLS);
        final JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(AddStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockInputPanel);
        final JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(AddStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        final JPanel buttonPanel = new JPanel();
        final JButton addStock = new JButton(AddStockViewModel.BUY_STOCK_BUTTON_LABEL);
        final JButton cancel = new JButton(AddStockViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(addStock);
        buttonPanel.add(cancel);
        stockNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharesFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(stockNamePanel);
        this.add(sharesFieldPanel);
        this.add(this.errorMessageLabel);
        this.add(buttonPanel);
        addActionListenersForButtons(addStock, cancel);
    }

    private void addActionListenersForButtons(JButton addStock, JButton cancel) {
        addStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (addStockController != null) {
                    final String stockNameInput = stockInputPanel.getText();
                    final String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        final int shares = Integer.parseInt(sharesInput);
                        addStockController.execute(stockNameInput, shares);
                    }
                    else {
                        addStockViewModel.getState().setAsInvalid("Shares to add should be a non-negative integer.");
                        addStockViewModel.firePropertyChange();
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (addStockController != null) {
                    addStockController.switchBack();
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddStockState state = (AddStockState) evt.getNewValue();
        if (state.checkIfValid()) {
            errorMessageLabel.setText("");
            JOptionPane.showMessageDialog(null,
                    String.format("You added %d shares of %s stocks at price $%.2f per share.",
                            state.getSharesChanged(),
                            state.getStockName(),
                            state.getCurrentPrice()));
        }
        else {
            errorMessageLabel.setText(state.getErrorMessage());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setBuyStockController(AddStockController controller) {
        addStockController = controller;
    }
}
