package usecases.buy_stock;

import usecases.LocalDataAccessInterface;

public interface BuyStockDataAccessInterface extends LocalDataAccessInterface {
    double queryPrice(String symbol);
}
