package app;
import data_access.AlphaVantageDataAccess;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame homepage = new JFrame("Homepage");
        homepage.setSize(400, 400);
        homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JButton predict = new JButton("predict stock price");
        JButton managePortfolio = new JButton("manage portfolio");
        JButton predictRevenue = new JButton("predict revenue");
        JButton backtest = new JButton("backtest");
        mainPanel.add(predict);
        mainPanel.add(managePortfolio);
        mainPanel.add(predictRevenue);
        mainPanel.add(backtest);
        homepage.setContentPane(mainPanel);
        homepage.setVisible(true);
    }
}