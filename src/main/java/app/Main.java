package app;

import javax.swing.*;

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