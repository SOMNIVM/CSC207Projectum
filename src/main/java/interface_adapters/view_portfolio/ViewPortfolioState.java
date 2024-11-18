package interface_adapters.view_portfolio;

public class ViewPortfolioState {
    private int totalValue;
    private String[][] portfolioData;
    public ViewPortfolioState() {
        totalValue = 0;
        portfolioData = new String[][]{};
    }
    public int getTotalValue() {
        return totalValue;
    }
    public String[][] getPortfolioData() {
        return portfolioData;
    }
    public void setTotalValue(int newValue) {
        totalValue = newValue;
    }
    public void setPortfolioData(String[][] newData) {
        portfolioData = newData;
    }
}
