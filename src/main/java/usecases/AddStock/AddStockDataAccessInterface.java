package usecases.AddStock;

import usecases.LocalDataAccessInterface;

public interface AddStockDataAccessInterface extends LocalDataAccessInterface {
    double queryPrice(String symbol);
}
