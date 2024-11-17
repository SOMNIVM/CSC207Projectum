package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllController;
import interface_adapters.reset_portfolio.ClearAllState;
import interface_adapters.reset_portfolio.ClearAllViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomePageView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private ClearAllViewModel clearAllViewModel;
    private ClearAllController clearAllController;
    private final ViewManagerModel viewManagerModel;

    public HomePageView(ClearAllViewModel clearAllViewModel, ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.clearAllViewModel = clearAllViewModel;
        this.clearAllViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = managerModel;
        this.viewName = clearAllViewModel.getViewName();
        JLabel title = new JLabel(viewName);
        JPanel managePortfolioPanel = new JPanel();
        JPanel analysisPanel = new JPanel();
        JButton viewPortfolio = new JButton("view portfolio");
        JButton buyStock = new JButton("buy stock");
        JButton removeStock = new JButton("remove stock");
        JButton clearAll = new JButton("clear all");
        JButton predictRevenue = new JButton("predict revenue");
        JButton backtest = new JButton("backtest");
        managePortfolioPanel.add(viewPortfolio);
        managePortfolioPanel.add(buyStock);
        managePortfolioPanel.add(removeStock);
        managePortfolioPanel.add(clearAll);
        analysisPanel.add(predictRevenue);
        analysisPanel.add(backtest);
        this.add(title, BorderLayout.CENTER);
        this.add(managePortfolioPanel, BorderLayout.CENTER);
        this.add(analysisPanel, BorderLayout.CENTER);
        viewPortfolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("view portfolio");
                viewManagerModel.firePropertyChange();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("cleared")) {
            ClearAllState state = (ClearAllState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getMessageUponClearing());
        }
    }
}
