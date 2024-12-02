package data_access;

import entities.Portfolio;
import java.io.*;

// Mock version of the FilePortfolioDataAccessObject class
public class FilePortfolioDataAccessObject {
    private final String filePath;
    private Portfolio portfolio;

    public FilePortfolioDataAccessObject(String filePath) {
        this.filePath = filePath;
        loadPortfolio();
    }

    private void loadPortfolio() {
        File file = new File(filePath);
        if (!file.exists()) {
            this.portfolio = new Portfolio();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.portfolio = (Portfolio) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            this.portfolio = new Portfolio();
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void savePortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(portfolio);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save portfolio", e);
        }
    }

    public boolean hasPortfolio() {
        return portfolio != null;
    }
}