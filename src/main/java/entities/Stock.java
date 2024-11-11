package entities;

public class Stock {
    private final String companyName;
    private final String symbol;
    public Stock(String companyName, String symbol) {
        this.companyName = companyName;
        this.symbol = symbol;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getSymbol() {
        return symbol;
    }
    public int hashCode() {
        return symbol.hashCode();
    }
}
