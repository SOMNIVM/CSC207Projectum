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

import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllState;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.view_portfolio.ViewPortfolioController;

/**
 * The view of the home page.
 */
public class HomePageView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final ClearAllViewModel clearAllViewModel;
    private ClearAllController clearAllController;
    private ViewPortfolioController viewPortfolioController;

    public HomePageView(ClearAllViewModel clearAllModel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.clearAllViewModel = clearAllModel;
        this.clearAllViewModel.addPropertyChangeListener(this);
        this.viewName = this.clearAllViewModel.getViewName();
        final JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JPanel managePortfolioPanel = new JPanel();
        final JPanel analysisPanel = new JPanel();
        final JButton viewPortfolio = new JButton(ClearAllViewModel.VIEW_PORTFOLIO_BUTTON_LABEL);
        final JButton buyStock = new JButton(ClearAllViewModel.ADD_STOCK_BUTTON_LABEL);
        final JButton removeStock = new JButton(ClearAllViewModel.REMOVE_STOCK_BUTTON_LABEL);
        final JButton clearAll = new JButton(ClearAllViewModel.CLEAR_ALL_BUTTON_LABEL);
        final JButton predictRevenue = new JButton(ClearAllViewModel.PREDICT_REVENUE_BUTTON_LABEL);
        final JButton evaluateModel = new JButton(ClearAllViewModel.EVALUATE_BUTTON_LABEL);
        managePortfolioPanel.add(viewPortfolio);
        managePortfolioPanel.add(buyStock);
        managePortfolioPanel.add(removeStock);
        managePortfolioPanel.add(clearAll);
        analysisPanel.add(predictRevenue);
        analysisPanel.add(evaluateModel);
        managePortfolioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        analysisPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(managePortfolioPanel);
        this.add(analysisPanel);
        addActionListenersToButtons(viewPortfolio, buyStock, removeStock, clearAll, predictRevenue, evaluateModel);
    }

    private void addActionListenersToButtons(JButton viewPortfolio,
                                             JButton buyStock,
                                             JButton removeStock,
                                             JButton clearAll,
                                             JButton predictRevenue,
                                             JButton backtest) {
        viewPortfolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (viewPortfolioController != null) {
                    viewPortfolioController.execute();
                }
            }
        });
        buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (clearAllController != null) {
                    clearAllController.switchToBuyStock();
                }
            }
        });
        removeStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (clearAllController != null) {
                    clearAllController.switchToRemoveStock();
                }
            }
        });
        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (clearAllController != null) {
                    clearAllController.execute();
                }
            }
        });
        predictRevenue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (clearAllController != null) {
                    clearAllController.switchToPredictRevenue();
                }
            }
        });
        backtest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (clearAllController != null) {
                    clearAllController.switchToBacktest();
                }
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

    public void setClearAllController(ClearAllController controller) {
        clearAllController = controller;
    }

    public void setViewPortfolioController(ViewPortfolioController controller) {
        viewPortfolioController = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ClearAllState state = (ClearAllState) evt.getNewValue();
        if (state.checkIfCleared()) {
            JOptionPane.showMessageDialog(null, ClearAllViewModel.MESSAGE_UPON_CLEARING);
        }
    }
}
