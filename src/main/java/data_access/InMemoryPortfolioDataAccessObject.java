package data_access;

import entities.Portfolio;
import usecases.remove_stock.RemoveStockDataAccessInterface;

public class InMemoryPortfolioDataAccessObject implements RemoveStockDataAccessInterface {
    private Portfolio portfolio;

    public InMemoryPortfolioDataAccessObject() {
        this.portfolio = new Portfolio(); // Creates empty portfolio with initialized maps
    }

    public Portfolio getPortfolio() {
        return portfolio; 
    }

    public void savePortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public void removeStock(Portfolio portfolio) {
        savePortfolio(portfolio);
    }
}