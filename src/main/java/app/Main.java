package app;
import data_access.AlphaVantageDataAccessObject;
import data_access.LocalDataAccessObject;
import interface_adapters.ViewManagerModel;
import interface_adapters.buy_stock.BuyStockViewModel;
import interface_adapters.remove_stock.RemoveStockViewModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import interface_adapters.view_portfolio.ViewPortfolioController;
import interface_adapters.view_portfolio.ViewPortfolioPresenter;
import interface_adapters.view_portfolio.ViewPortfolioViewModel;
import usecases.LocalDataAccessInterface;
import usecases.OnlineDataAccessInterface;
import usecases.view_portfolio.ViewPortfolioInteractor;
import views.*;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame app = appBuilder
                .addViewPortfolioView()
                .addBuyStockView()
                .addRemoveStockView()
                .addViewPortfolioUseCase()
                .addBuyStockUseCase()
                .addRemoveStockUseCase()
                .addClearAllUseCase()
                .build();
        app.pack();
        app.setVisible(true);
    }

//    @NotNull
//    private static JPanel getPanel() {
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
//        JButton predict = new JButton("predict stock price");
//        JButton managePortfolio = new JButton("manage portfolio");
//        JButton predictRevenue = new JButton("predict revenue");
//        JButton backtest = new JButton("backtest");
//        predict.setAlignmentX(Component.CENTER_ALIGNMENT);
//        managePortfolio.setAlignmentX(Component.CENTER_ALIGNMENT);
//        predictRevenue.setAlignmentX(Component.CENTER_ALIGNMENT);
//        backtest.setAlignmentX(Component.CENTER_ALIGNMENT);
//        buttonPanel.add(predict);
//        buttonPanel.add(managePortfolio);
//        buttonPanel.add(predictRevenue);
//        buttonPanel.add(backtest);
//        return buttonPanel;
//    }
}