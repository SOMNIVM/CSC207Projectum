package app;
import data_access.AlphaVantageDataAccess;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        JFrame homepage = new JFrame("Homepage");
        homepage.setSize(250, 150);
        homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = getPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        homepage.setContentPane(mainPanel);
        homepage.setVisible(true);
    }

    @NotNull
    private static JPanel getPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton predict = new JButton("predict stock price");
        JButton managePortfolio = new JButton("manage portfolio");
        JButton predictRevenue = new JButton("predict revenue");
        JButton backtest = new JButton("backtest");
        predict.setAlignmentX(Component.CENTER_ALIGNMENT);
        managePortfolio.setAlignmentX(Component.CENTER_ALIGNMENT);
        predictRevenue.setAlignmentX(Component.CENTER_ALIGNMENT);
        backtest.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(predict);
        buttonPanel.add(managePortfolio);
        buttonPanel.add(predictRevenue);
        buttonPanel.add(backtest);
        return buttonPanel;
    }
}