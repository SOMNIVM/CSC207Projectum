package app;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
          AppBuilder appBuilder = new AppBuilder();
          JFrame app = appBuilder
                  .addViewPortfolioView()
                  .addAddStockView()
                  .addRemoveStockView()
                  .addRevenuePredictionView()
                  .addModelEvaluationView()
                  .addViewPortfolioUseCase()
                  .addAddStockUseCase()
                  .addRemoveStockUseCase()
                  .addRevenuePredictionUseCase()
                  .addClearAllUseCase()
                  .addModelEvaluationCase()
                  .build();
          app.pack();
          app.setVisible(true);
    }

}