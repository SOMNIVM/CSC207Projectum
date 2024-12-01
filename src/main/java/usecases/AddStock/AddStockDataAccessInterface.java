package usecases.AddStock;

import usecases.LocalDataAccessInterface;

public interface BuyStockDataAccessInterface extends LocalDataAccessInterface {
    double queryPrice(String symbol);
}
