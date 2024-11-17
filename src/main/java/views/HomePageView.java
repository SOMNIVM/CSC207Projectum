package views;

import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomePageView extends JPanel implements PropertyChangeListener {
    private final String viewName;
    private ClearAllController clearAllController;
    private final ViewManagerModel viewManagerModel;
    private final JButton viewPortfolio;
    private final JButton buyStock;
    private final JButton removeStock;
    private final JButton clearAll;
    private final JButton predictRevenue;
    private final JButton backtest;
    public HomePageView(ViewManagerModel managerModel) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.viewName = "homepage";
        this.viewManagerModel = managerModel;
        JPanel managePortfolioPanel = new JPanel();
        JPanel analysisPanel = new JPanel();
        this.viewPortfolio = new JButton("view portfolio");
        this.buyStock = new JButton("buy stock");
        this.removeStock = new JButton("remove stock");
        this.clearAll = new JButton("clear all");
        this.predictRevenue = new JButton("predict revenue");
        this.backtest = new JButton("backtest");
        managePortfolioPanel.add(this.viewPortfolio);
        managePortfolioPanel.add(this.buyStock);
        managePortfolioPanel.add(this.removeStock);
        managePortfolioPanel.add(this.clearAll);
        analysisPanel.add(this.predictRevenue);
        analysisPanel.add(this.backtest);
        this.add(managePortfolioPanel, BorderLayout.CENTER);
        this.add(analysisPanel, BorderLayout.CENTER);
        this.viewPortfolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("view portfolio");
                viewManagerModel.firePropertyChange();
            }
        });
        this.buyStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("buy stock");
                viewManagerModel.firePropertyChange();
            }
        });
        this.removeStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("remove stock");
                viewManagerModel.firePropertyChange();
            }
        });
        this.clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearAllController.execute();
            }
        });
        this.predictRevenue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewManagerModel.getState().setCurViewName("predict revenue");
                viewManagerModel.firePropertyChange();
            }
        });
        this.backtest.addActionListener(new ActionListener() {
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
            JOptionPane.showMessageDialog(this, evt.getNewValue());
        }
    }
}
