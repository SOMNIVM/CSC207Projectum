package usecases.add_stock;

import usecases.LocalDataAccessInterface;

public interface AddStockDataAccessInterface extends LocalDataAccessInterface {
    double queryPrice(String symbol);
}
