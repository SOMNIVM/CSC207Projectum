package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class PortfolioTest {
    private Portfolio portfolio;
    private static final String TEST_SYMBOL = "AAPL";
    private static final String TEST_SYMBOL_2 = "GOOGL";
    private static final int INITIAL_SHARES = 100;
    private static final double INITIAL_PRICE = 150.0;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio();
    }

    @Test
    void testNewPortfolioIsEmpty() {
        assertTrue(portfolio.getStockSymbols().isEmpty(), "New portfolio should be empty");
    }

    @Test
    void testGetSharesNonExistentStock() {
        Portfolio newportfolio = new Portfolio();
        assertThrows(NullPointerException.class, () -> {
            newportfolio.getShares("NONEXISTENT");
        }, "Getting shares of non-existent stock should throw NullPointerException due to null unboxing");
    }

    @Test
    void testAddStock() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);

        assertEquals(INITIAL_SHARES, portfolio.getShares(TEST_SYMBOL),
                "Portfolio should contain the correct number of shares after adding");
        assertTrue(portfolio.getStockSymbols().contains(TEST_SYMBOL),
                "Portfolio should contain the added stock symbol");
    }

    @Test
    void testAddStockMultipleTimes() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        portfolio.addStock(TEST_SYMBOL, 50, 160.0);

        assertEquals(INITIAL_SHARES + 50, portfolio.getShares(TEST_SYMBOL),
                "Portfolio should correctly sum shares when adding to existing position");
    }

    @Test
    void testAddMultipleStocks() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        portfolio.addStock(TEST_SYMBOL_2, 200, 2500.0);

        Set<String> symbols = portfolio.getStockSymbols();
        assertEquals(2, symbols.size(), "Portfolio should contain both stocks");
        assertTrue(symbols.contains(TEST_SYMBOL), "Portfolio should contain first stock");
        assertTrue(symbols.contains(TEST_SYMBOL_2), "Portfolio should contain second stock");
    }

    @Test
    void testRemoveAllShares() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        boolean removed = portfolio.removeStock(TEST_SYMBOL, INITIAL_SHARES);

        assertTrue(removed, "Remove operation should return true");
        assertFalse(portfolio.getStockSymbols().contains(TEST_SYMBOL),
                "Stock should be completely removed when all shares are removed");
    }

    @Test
    void testRemovePartialShares() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        boolean removed = portfolio.removeStock(TEST_SYMBOL, 50);

        assertTrue(removed, "Remove operation should return true");
        assertEquals(50, portfolio.getShares(TEST_SYMBOL),
                "Portfolio should contain correct number of shares after partial removal");
    }

    @Test
    void testRemoveMoreSharesThanOwned() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        boolean removed = portfolio.removeStock(TEST_SYMBOL, INITIAL_SHARES + 50);

        assertTrue(removed, "Remove operation should return true");
        assertFalse(portfolio.getStockSymbols().contains(TEST_SYMBOL),
                "Stock should be completely removed when removing more shares than owned");
    }

    @Test
    void testRemoveNonexistentStock() {
        boolean removed = portfolio.removeStock("NONEXISTENT", 100);

        assertFalse(removed, "Removing non-existent stock should return false");
    }

    @Test
    void testToString() {
        portfolio.addStock(TEST_SYMBOL, INITIAL_SHARES, INITIAL_PRICE);
        String expected = "Your portfolio contains the following assets:\n" +
                "100 shares of AAPL";

        assertEquals(expected, portfolio.toString().trim(),
                "toString should return correctly formatted portfolio summary");
    }

    @Test
    void testEmptyPortfolioToString() {
        String expected = "Your portfolio contains the following assets:\n";

        assertEquals(expected.trim(), portfolio.toString().trim(),
                "toString should handle empty portfolio correctly");
    }

    @Test
    void testAddStockWithZeroShares() {
        portfolio.addStock(TEST_SYMBOL, 0, INITIAL_PRICE);

        assertEquals(0, portfolio.getShares(TEST_SYMBOL),
                "Portfolio should allow adding zero shares");
        assertTrue(portfolio.getStockSymbols().contains(TEST_SYMBOL),
                "Portfolio should contain symbol even with zero shares");
    }

    @Test
    void testAddStockWithNegativeShares() {
        portfolio.addStock(TEST_SYMBOL, -50, INITIAL_PRICE);

        assertEquals(-50, portfolio.getShares(TEST_SYMBOL),
                "Portfolio should handle negative shares (though this might be an issue to address)");
    }
}