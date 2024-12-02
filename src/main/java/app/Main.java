
package app;

import javax.swing.JFrame;

/**
 * The main class.
 */
public class Main {
    /**
     * The main method.
     * @param args The arguments for JVM.
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame app = appBuilder
                .addViewPortfolioView()
                .addAddStockView()
                .addRemoveStockView()
                .addViewPortfolioUseCase()
                .addAddStockUseCase()
                .addRemoveStockUseCase()
                .addClearAllUseCase()
                .build();
        app.pack();
        app.setVisible(true);
    }
}
