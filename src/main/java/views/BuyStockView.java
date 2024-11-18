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
    private final ViewManagerModel viewManagerModel;
    private final JLabel errorMessageLabel;
    public BuyStockView(BuyStockViewModel buyStockViewModel, ViewManagerModel viewManagerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.viewManagerModel = viewManagerModel;
        this.buyStockViewModel = buyStockViewModel;
        this.viewName = buyStockViewModel.getViewName();
        this.buyStockViewModel.addPropertyChangeListener(this);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel title = new JLabel(viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel stockNamePanel = new JPanel();
        JTextField stockNameField = new JTextField(20);
        stockNamePanel.add(new JLabel(BuyStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(stockNameField);
        JPanel sharesFieldPanel = new JPanel();
        JTextField sharesField = new JTextField(20);
        sharesFieldPanel.add(new JLabel(BuyStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(sharesField);
        JPanel buttonPanel = new JPanel();
        JButton buyStock = new JButton(BuyStockViewModel.BUY_STOCK_BUTTON_LABEL);
        JButton cancel = new JButton(BuyStockViewModel.CANCEL_BUTTON_LABEL);
        buttonPanel.add(buyStock);
        buttonPanel.add(cancel);
        this.add(title, BorderLayout.CENTER);
        this.add(stockNamePanel, BorderLayout.CENTER);
        this.add(sharesFieldPanel, BorderLayout.CENTER);
        this.add(errorMessageLabel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.CENTER);
        buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (buyStockController != null) {
                    String stockName = stockNameField.getText();
                    String sharesInput = stockNameField.getText();
                    if (sharesInput.matches("\\d+")) {
                        int shares = Integer.parseInt(sharesInput);
                        BuyStockState state = buyStockViewModel.getState();
                        state.setAsValid();
                        state.setStockName(stockName);
                        state.setSharesChanged(shares);
                        buyStockController.execute(stockName, shares);
                    }
                    else {
                        buyStockViewModel.getState().setAsInvalid("error: non-integer input for shares");
                        buyStockViewModel.firePropertyChange();
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
        BuyStockState state = (BuyStockState) evt.getNewValue();
        if (state.checkIfValid()) {
            JOptionPane.showMessageDialog(null,
                    "You purchased " + state.getSharesChanged()
                            + " shares of " + state.getStockName()
                            + " stock at price $" + state.getBuyingPrice() + " per share.");
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
