
package usecases.remove_stock;

import entities.Portfolio;

public interface RemoveStockDataAccessInterface {
    void removeStock(Portfolio portfolio);
}