package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllState;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.view_portfolio.ViewPortfolioController;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomePageView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private final ClearAllViewModel clearAllViewModel;
    private ClearAllController clearAllController;
    private ViewPortfolioController viewPortfolioController;
    private final ViewManagerModel viewManagerModel;

    public HomePageView(ClearAllViewModel clearAllModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.clearAllViewModel = clearAllModel;
        this.clearAllViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = managerModel;
        this.viewName = this.clearAllViewModel.getViewName();
        JLabel title = new JLabel(this.viewName);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel managePortfolioPanel = new JPanel();
        JPanel analysisPanel = new JPanel();
        JButton viewPortfolio = new JButton(ClearAllViewModel.VIEW_PORTFOLIO_BUTTON_LABEL);
        JButton buyStock = new JButton(ClearAllViewModel.BUY_STOCK_BUTTON_LABEL);
        JButton removeStock = new JButton(ClearAllViewModel.REMOVE_STOCK_BUTTON_LABEL);
        JButton clearAll = new JButton(ClearAllViewModel.CLEAR_ALL_BUTTON_LABEL);
        JButton predictRevenue = new JButton(ClearAllViewModel.PREDICT_REVENUE_BUTTON_LABEL);
        JButton backtest = new JButton(ClearAllViewModel.BACKTEST_BUTTON_LABEL);
        managePortfolioPanel.add(viewPortfolio);
        managePortfolioPanel.add(buyStock);
        managePortfolioPanel.add(removeStock);
        managePortfolioPanel.add(clearAll);
        analysisPanel.add(predictRevenue);
        analysisPanel.add(backtest);
        managePortfolioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        analysisPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        this.add(managePortfolioPanel);
        this.add(analysisPanel);
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
                clearAllViewModel.getState().unclear();
                viewManagerModel.getState().setCurViewName("buy stock");
                viewManagerModel.firePropertyChange();
            }
        });
        removeStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("remove stock");
                viewManagerModel.firePropertyChange();
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
                viewManagerModel.getState().setCurViewName("predict revenue");
                viewManagerModel.firePropertyChange();
            }
        });
        backtest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("backtest");
                viewManagerModel.firePropertyChange();
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
        ClearAllState state = (ClearAllState) evt.getNewValue();
        if (state.checkIfCleared()) {
            JOptionPane.showMessageDialog(null, ClearAllViewModel.MESSAGE_UPON_CLEARING);
        }
    }
}
