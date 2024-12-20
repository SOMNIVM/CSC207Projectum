package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Portfolio;

public class PortfolioTest {

    @Test
    public void testAddStock() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AAPL", 10, 150.0);
        assertEquals(10, portfolio.getShares("AAPL"));

        // Add more shares
        portfolio.addStock("AAPL", 5, 155.0);
        assertEquals(15, portfolio.getShares("AAPL"));
    }

    @Test
    public void testRemoveStock() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("GOOGL", 20, 1000.0);
        assertTrue(portfolio.removeStock("GOOGL", 5));
        assertEquals(15, portfolio.getShares("GOOGL"));
        assertTrue(portfolio.removeStock("GOOGL", 15));
        assertFalse(portfolio.removeStock("MSFT", 10));
    }

    @Test
    public void testGetShares() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("TSLA", 8, 700.0);
        assertEquals(8, portfolio.getShares("TSLA"));
    }

    @Test
    public void testToString() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AAPL", 10, 150.0);
        portfolio.addStock("GOOGL", 5, 1000.0);
        String expected = "Your portfolio contains the following assets:\n" +
                "5 shares of GOOGL\n" +
                "10 shares of AAPL\n";
        assertEquals(expected, portfolio.toString());
    }



}