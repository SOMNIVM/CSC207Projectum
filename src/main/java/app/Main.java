package app;
import data_access.AlphaVantageDataAccess;
import interface_adapters.ViewManagerModel;
import interface_adapters.reset_portfolio.ClearAllViewModel;
import org.jetbrains.annotations.NotNull;
import views.HomePageView;
import views.ViewManager;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
          JFrame app = new JFrame("App");
          app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          JPanel cardPanel = new JPanel();
          CardLayout cardLayout = new CardLayout();
          cardPanel.setLayout(cardLayout);
          ViewManagerModel viewManagerModel = new ViewManagerModel();
          ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);
          HomePageView homePageView = new HomePageView(new ClearAllViewModel(), viewManagerModel);
          cardPanel.add(homePageView, homePageView.getViewName());
          viewManagerModel.getState().setCurViewName(homePageView.getViewName());
          viewManagerModel.firePropertyChange();
          app.add(cardPanel);
          app.pack();
          app.setVisible(true);
//        JFrame homepage = new JFrame("Homepage");
//        homepage.setSize(250, 150);
//        homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel mainPanel = new JPanel();
//        JPanel buttonPanel = getPanel();
//        mainPanel.add(buttonPanel, BorderLayout.CENTER);
//        homepage.setContentPane(mainPanel);
//        homepage.setVisible(true);
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