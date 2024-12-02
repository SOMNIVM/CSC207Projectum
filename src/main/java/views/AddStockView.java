
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

import org.jetbrains.annotations.NotNull;

import interface_adapters.buy_stock.BuyStockController;
import interface_adapters.buy_stock.BuyStockState;
import interface_adapters.buy_stock.BuyStockViewModel;

/**
 * View class for adding a stock.
 * Implements the PropertyChangeListener interface to listen for property changes in the view model.
 */
public class AddStockView extends JPanel implements PropertyChangeListener {
    public static final int COLUMNS = 20;
    private final String viewName;
    private final BuyStockViewModel buyStockViewModel;
    private BuyStockController buyStockController;
    private final JTextField stockNameField;
    private final JTextField sharesField;
    private final JLabel errorMessageLabel;

    /**
     * Constructs an AddStockView with the specified view model.
     *
     * @param buyStockModel the view model for buying stock
     */
    public AddStockView(@NotNull BuyStockViewModel buyStockModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.buyStockViewModel = buyStockModel;
        this.viewName = buyStockModel.getViewName();
        this.buyStockViewModel.addPropertyChangeListener(this);
        this.errorMessageLabel = new JLabel();
        this.errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.stockNameField = new JTextField(COLUMNS);
        this.sharesField = new JTextField(COLUMNS);
        final Result result = getResult();
        result.buyStock().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (buyStockController != null) {
                    final String stockNameInput = stockNameField.getText();
                    final String sharesInput = sharesField.getText();
                    if (sharesInput.matches("^[0-9]+$")) {
                        final int shares = Integer.parseInt(sharesInput);
                        buyStockController.execute(stockNameInput, shares);
                    }
                    else {
                        buyStockViewModel.getState().setAsInvalid("Shares to buy should be a non-negative integer.");
                        buyStockViewModel.firePropertyChange();
                    }
                }
            }
        });
        result.cancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (buyStockController != null) {
                    buyStockController.switchBack();
                }
            }
        });
    }

    @NotNull
    private Result getResult() {
        final JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel stockNamePanel = new JPanel();
        stockNamePanel.add(new JLabel(BuyStockViewModel.STOCK_NAME_FIELD_LABEL));
        stockNamePanel.add(this.stockNameField);
        final JPanel sharesFieldPanel = new JPanel();
        sharesFieldPanel.add(new JLabel(BuyStockViewModel.SHARES_FIELD_LABEL));
        sharesFieldPanel.add(this.sharesField);
        final JPanel buttonPanel = new JPanel();
        final JButton buyStock = new JButton(BuyStockViewModel.BUY_STOCK_BUTTON_LABEL);
        final JButton cancel = new JButton(BuyStockViewModel.CANCEL_BUTTON_LABEL);
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
        final Result result = new Result(buyStock, cancel);
        return result;
    }

    /**
     * A record to hold the result of the AddStockView actions.
     * Contains the buy stock and cancel buttons.
     *
     * @param buyStock the button for executing a buy stock action
     * @param cancel the button for canceling the action
     */
    private record Result(JButton buyStock, JButton cancel) {
    }

    /**
     * Handles property change events from the view model.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BuyStockState state = (BuyStockState) evt.getNewValue();
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

    /**
     * Returns the name of the view.
     *
     * @return the view name
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the controller for buying stock.
     *
     * @param controller the buy stock controller
     */
    public void setBuyStockController(BuyStockController controller) {
        buyStockController = controller;
    }
}
