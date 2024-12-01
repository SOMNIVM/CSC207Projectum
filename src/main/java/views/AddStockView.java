package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.buy_stock.BuyStockController;
import interface_adapters.buy_stock.BuyStockState;
import interface_adapters.buy_stock.BuyStockViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BuyStockView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final BuyStockViewModel buyStockViewModel;
    private BuyStockController buyStockController;
    private final JTextField stockNameField;
    private final JTextField sharesField;
    private final JLabel errorMessageLabel;
    public BuyStockView(BuyStockViewModel buyStockModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.buyStockViewModel = buyStockModel;
        this.viewName = buyStockModel.getViewName();
        this.buyStockViewModel.addPropertyChangeListener(this);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.stockNameField = new JTextField(20);
        this.sharesField = new JTextField(20);
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(BuyStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockNameField);
        JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(BuyStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        JPanel buttonPanel = new JPanel();
        JButton buyStock = new JButton(BuyStockViewModel.BUY_STOCK_BUTTON_LABEL);
        JButton cancel = new JButton(BuyStockViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(buyStock);
        buttonPanel.add(cancel);
        stockNamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sharesFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(stockNamePanel);
        this.add(sharesFieldPanel);
        this.add(this.errorMessageLabel);
        this.add(buttonPanel);
        buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (buyStockController != null) {
                    String stockNameInput = stockNameField.getText();
                    String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        int shares = Integer.parseInt(sharesInput);
                        buyStockController.execute(stockNameInput, shares);
                    }
                    else {
                        buyStockViewModel.getState().setAsInvalid("Shares to buy should be a non-negative integer.");
                        buyStockViewModel.firePropertyChange();
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (buyStockController != null) {
                    buyStockController.switchBack();
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        BuyStockState state = (BuyStockState) evt.getNewValue();
        if (state.checkIfValid()) {
            errorMessageLabel.setText("");
            JOptionPane.showMessageDialog(null,
                    String.format("You purchased %d shares of %s stocks at price $%.2f per share.",
                            state.getSharesChanged(),
                            state.getStockName(),
                            state.getBuyingPrice()));
        }
        else {
            errorMessageLabel.setText(state.getErrorMessage());
        }
    }
    public String getViewName() {
        return viewName;
    }
    public void setBuyStockController(BuyStockController controller) {
        buyStockController = controller;
    }
}
