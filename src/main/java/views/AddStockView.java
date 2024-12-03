package views;

import interface_adapters.add_stock.AddStockController;
import interface_adapters.add_stock.AddStockState;
import interface_adapters.add_stock.AddStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddStockView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final AddStockViewModel addStockViewModel;
    private AddStockController addStockController;
    private final StockInputPanel stockInputPanel;
    private final JTextField sharesField;
    private final JLabel errorMessageLabel;
    public AddStockView(AddStockViewModel buyStockModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.addStockViewModel = buyStockModel;
        this.viewName = buyStockModel.getViewName();
        this.addStockViewModel.addPropertyChangeListener(this);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.stockInputPanel = new StockInputPanel();
        this.sharesField = new JTextField(30);
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(AddStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockInputPanel);
        JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(AddStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        JPanel buttonPanel = new JPanel();
        JButton addStock = new JButton(AddStockViewModel.BUY_STOCK_BUTTON_LABEL);
        JButton cancel = new JButton(AddStockViewModel.CANCEL_BUTTON_LABEL);
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
        addStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (addStockController != null) {
                    String stockNameInput = stockInputPanel.getText();
                    String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        int shares = Integer.parseInt(sharesInput);
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
        AddStockState state = (AddStockState) evt.getNewValue();
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
