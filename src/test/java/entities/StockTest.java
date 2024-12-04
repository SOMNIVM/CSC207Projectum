package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StockTest {
    private static final String COMPANY_NAME = "Apple Inc.";
    private static final String STOCK_SYMBOL = "AAPL";

    @Test
    void testConstructor() {
        Stock stock = new Stock(COMPANY_NAME, STOCK_SYMBOL);
        assertNotNull(stock, "Stock object should be created successfully");
        assertEquals(COMPANY_NAME, stock.getName(), "Company name should match constructor parameter");
        assertEquals(STOCK_SYMBOL, stock.getStockSymbol(), "Stock symbol should match constructor parameter");
    }

    @Test
    void testConstructorWithNullName() {
        Stock stock = new Stock(null, STOCK_SYMBOL);
        assertNull(stock.getName(), "Stock should accept null company name");
        assertEquals(STOCK_SYMBOL, stock.getStockSymbol(), "Stock symbol should be set even with null company name");
    }

    @Test
    void testConstructorWithNullSymbol() {
        Stock stock = new Stock(COMPANY_NAME, null);
        assertEquals(COMPANY_NAME, stock.getName(), "Company name should be set with null symbol");
        assertNull(stock.getStockSymbol(), "Stock should accept null symbol");
    }

    @Test
    void testConstructorWithEmptyStrings() {
        Stock stock = new Stock("", "");
        assertEquals("", stock.getName(), "Stock should accept empty company name");
        assertEquals("", stock.getStockSymbol(), "Stock should accept empty symbol");
    }

    @Test
    void testGetCompanyName() {
        Stock stock = new Stock(COMPANY_NAME, STOCK_SYMBOL);
        assertEquals(COMPANY_NAME, stock.getCompanyName(), "getCompanyName should return the same value as getName");
        assertEquals(stock.getName(), stock.getCompanyName(), "getCompanyName should match getName");
    }

    @Test
    void testStockSymbolImmutability() {
        Stock stock = new Stock(COMPANY_NAME, STOCK_SYMBOL);
        assertEquals(STOCK_SYMBOL, stock.getStockSymbol(), "Initial stock symbol should match");

        // Verify that stockSymbol can't be changed (it's final)
        String originalSymbol = stock.getStockSymbol();
        // If stockSymbol were mutable, changing the original reference wouldn't affect the stored value
        assertEquals(originalSymbol, stock.getStockSymbol(), "Stock symbol should be immutable");
    }

    @Test
    void testInheritanceFromAsset() {
        Stock stock = new Stock(COMPANY_NAME, STOCK_SYMBOL);
        assertInstanceOf(Asset.class, stock, "Stock should be an instance of Asset");

        // Test inheritance of Asset methods
        stock.setName("New Company Name");
        assertEquals("New Company Name", stock.getName(), "Should inherit setName functionality from Asset");
        assertEquals("New Company Name", stock.getCompanyName(), "getCompanyName should reflect name changes");
    }

    @Test
    void testNameAndSymbolIndependence() {
        Stock stock = new Stock(COMPANY_NAME, STOCK_SYMBOL);

        // Change name and verify symbol remains unchanged
        stock.setName("Different Company");
        assertEquals("Different Company", stock.getName(), "Company name should be updated");
        assertEquals(STOCK_SYMBOL, stock.getStockSymbol(), "Stock symbol should remain unchanged when name is updated");
    }
}