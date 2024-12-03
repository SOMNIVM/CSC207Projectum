package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockInPortfolioTest {
    private StockInPortfolio stockInPortfolio;
    private static final String COMPANY_NAME = "Apple Inc.";
    private static final String STOCK_SYMBOL = "AAPL";
    private static final double INITIAL_PRICE = 150.50;
    private static final int INITIAL_SHARES = 100;

    @BeforeEach
    void setUp() {
        stockInPortfolio = new StockInPortfolio(COMPANY_NAME, STOCK_SYMBOL, INITIAL_PRICE, INITIAL_SHARES);
    }

    @Test
    void testConstructor() {
        assertNotNull(stockInPortfolio, "StockInPortfolio should be created successfully");
        assertEquals(COMPANY_NAME, stockInPortfolio.getName(), "Company name should match constructor parameter");
        assertEquals(STOCK_SYMBOL, stockInPortfolio.getStockSymbol(), "Stock symbol should match constructor parameter");
        assertEquals(INITIAL_PRICE, stockInPortfolio.getAveragePrice(),
                "Average price should match constructor parameter");
        assertEquals(INITIAL_SHARES, stockInPortfolio.getNumberOfShares(),
                "Number of shares should match constructor parameter");
    }

    @Test
    void testInheritanceFromStock() {
        assertInstanceOf(Stock.class, stockInPortfolio, "StockInPortfolio should be an instance of Stock");
        assertEquals(stockInPortfolio.getName(), stockInPortfolio.getCompanyName(),
                "getCompanyName should return the same value as getName");
    }

    @Test
    void testGetAveragePrice() {
        assertEquals(INITIAL_PRICE, stockInPortfolio.getAveragePrice(),
                "getAveragePrice should return the correct average price");
    }

    @Test
    void testSetAveragePrice() {
        double newPrice = 160.75;
        stockInPortfolio.setAveragePrice(newPrice);
        assertEquals(newPrice, stockInPortfolio.getAveragePrice(),
                "setAveragePrice should update the average price correctly");
    }

    @Test
    void testSetAveragePriceZero() {
        stockInPortfolio.setAveragePrice(0);
        assertEquals(0, stockInPortfolio.getAveragePrice(),
                "setAveragePrice should allow setting price to zero");
    }

    @Test
    void testSetAveragePriceNegative() {
        double negativePrice = -50.25;
        stockInPortfolio.setAveragePrice(negativePrice);
        assertEquals(negativePrice, stockInPortfolio.getAveragePrice(),
                "setAveragePrice should allow negative prices");
    }

    @Test
    void testGetNumberOfShares() {
        assertEquals(INITIAL_SHARES, stockInPortfolio.getNumberOfShares(),
                "getNumberOfShares should return the correct number of shares");
    }

    @Test
    void testSetNumberOfShares() {
        int newShares = 200;
        stockInPortfolio.setNumberOfShares(newShares);
        assertEquals(newShares, stockInPortfolio.getNumberOfShares(),
                "setNumberOfShares should update the number of shares correctly");
    }

    @Test
    void testSetNumberOfSharesZero() {
        stockInPortfolio.setNumberOfShares(0);
        assertEquals(0, stockInPortfolio.getNumberOfShares(),
                "setNumberOfShares should allow setting shares to zero");
    }

    @Test
    void testSetNumberOfSharesNegative() {
        int negativeShares = -50;
        stockInPortfolio.setNumberOfShares(negativeShares);
        assertEquals(negativeShares, stockInPortfolio.getNumberOfShares(),
                "setNumberOfShares should allow negative shares");
    }

    @Test
    void testConstructorWithNullName() {
        StockInPortfolio nullNameStock = new StockInPortfolio(null, STOCK_SYMBOL, INITIAL_PRICE, INITIAL_SHARES);
        assertNull(nullNameStock.getName(), "Constructor should accept null company name");
        assertEquals(STOCK_SYMBOL, nullNameStock.getStockSymbol(),
                "Stock symbol should be set even with null company name");
    }

    @Test
    void testConstructorWithNullSymbol() {
        StockInPortfolio nullSymbolStock = new StockInPortfolio(COMPANY_NAME, null, INITIAL_PRICE, INITIAL_SHARES);
        assertEquals(COMPANY_NAME, nullSymbolStock.getName(), "Company name should be set with null symbol");
        assertNull(nullSymbolStock.getStockSymbol(), "Constructor should accept null symbol");
    }

    @Test
    void testLookForPrice() {
        assertEquals(0, stockInPortfolio.lookForPrice(),
                "lookForPrice should return 0 as it's not implemented yet");
    }

    @Test
    void testSetAveragePriceWithMaxValue() {
        stockInPortfolio.setAveragePrice(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, stockInPortfolio.getAveragePrice(),
                "setAveragePrice should handle maximum double value");
    }

    @Test
    void testSetNumberOfSharesWithMaxValue() {
        stockInPortfolio.setNumberOfShares(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, stockInPortfolio.getNumberOfShares(),
                "setNumberOfShares should handle maximum integer value");
    }
}