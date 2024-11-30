package interface_adapters.view_portfolio;

public class ViewPortfolioState {
    private double totalValue;
    private String[][] portfolioData;
    public ViewPortfolioState() {
        totalValue = 0;
        portfolioData = new String[][]{};
    }
    public double getTotalValue() {
        return totalValue;
    }
    public String[][] getPortfolioData() {
        return portfolioData;
    }
    public void setTotalValue(double newValue) {
        totalValue = newValue;
    }
    public void setPortfolioData(String[][] newData) {
        portfolioData = newData;
    }
}
