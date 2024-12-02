import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.Portfolio;

public class PortfolioTest {

    @Test
    public void testAddStock() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AAPL", 10, 150.0);
        assertEquals(10, portfolio.getShares("AAPL"));
        assertEquals(150.0, portfolio.getAveragePrice("AAPL"));

        // Add more shares
        portfolio.addStock("AAPL", 5, 155.0);
        assertEquals(15, portfolio.getShares("AAPL"));
        assertEquals(151.66666666666666, portfolio.getAveragePrice("AAPL"));
    }

    @Test
    public void testRemoveStock() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("GOOGL", 20, 1000.0);
        assertTrue(portfolio.removeStock("GOOGL", 5));
        assertEquals(15, portfolio.getShares("GOOGL"));
        assertTrue(portfolio.removeStock("GOOGL", 15));
        assertEquals(0, portfolio.getShares("GOOGL"));
        assertNull(portfolio.getAveragePrice("GOOGL"));
        assertFalse(portfolio.removeStock("MSFT", 10));
    }

    @Test
    public void testGetShares() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("TSLA", 8, 700.0);
        assertEquals(8, portfolio.getShares("TSLA"));
        assertEquals(0, portfolio.getShares("NFLX"));
    }

    @Test
    public void testGetAveragePrice() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AMZN", 5, 3100.0);
        assertEquals(3100.0, portfolio.getAveragePrice("AMZN"));
        assertNull(portfolio.getAveragePrice("FB"));
    }

    @Test
    public void testToString() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AAPL", 10, 150.0);
        portfolio.addStock("GOOGL", 5, 1000.0);
        String expected = "Your portfolio contains the following assets:\n" +
                          "10 shares of AAPL with an average price of $150.00\n" +
                          "5 shares of GOOGL with an average price of $1000.00\n";
        assertEquals(expected, portfolio.toString());
    }

    @Test
    public void testAddStock_InvalidInput() {
        Portfolio portfolio = new Portfolio();
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock("AAPL", 0, 150.0));
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock("AAPL", 10, 0.0));
        assertThrows(IllegalArgumentException.class, () -> portfolio.addStock("AAPL", 0, 0.0));
    }

    @Test
    public void testRemoveStock_InvalidInput() {
        Portfolio portfolio = new Portfolio();
        portfolio.addStock("AAPL", 10, 150.0);
        assertThrows(IllegalArgumentException.class, () -> portfolio.removeStock("AAPL", 0));
    }
}