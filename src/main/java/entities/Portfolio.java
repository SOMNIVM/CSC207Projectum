package entities;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Map<Stock, Integer> shares;
    public Portfolio() {
        this.shares = new HashMap<>();
    }
    public int getShare(Stock stock) {
        return shares.get(stock);
    }
    public void setShare(Stock stock, int share) {
        shares.put(stock, share);
    }
}
