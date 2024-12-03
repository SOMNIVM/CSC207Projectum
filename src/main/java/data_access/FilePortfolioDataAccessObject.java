package data_access;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entities.Portfolio;

/**
 * Mock version of the FilePortfolioDataAccessObject class.
 */
public class FilePortfolioDataAccessObject {
    private final String filePath;
    private Portfolio portfolio;

    public FilePortfolioDataAccessObject(String filePath) {
        this.filePath = filePath;
        loadPortfolio();
    }

    private void loadPortfolio() {
        final File file = new File(filePath);
        if (!file.exists()) {
            this.portfolio = new Portfolio();
        }
        else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                this.portfolio = (Portfolio) ois.readObject();
            }
            catch (IOException | ClassNotFoundException ex) {
                this.portfolio = new Portfolio();
            }
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Save the data of a portfolio as a file.
     * @param newPortfolio the portfolio object to be saved.
     * @throws RuntimeException if the method failed to save the portfolio data.
     */
    public void savePortfolio(Portfolio newPortfolio) {
        portfolio = newPortfolio;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(portfolio);
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to save portfolio", ex);
        }
    }

    /**
     * Check whether a portfolio is tracked by this object.
     * @return true if there is one.
     */
    public boolean hasPortfolio() {
        return portfolio != null;
    }
}
