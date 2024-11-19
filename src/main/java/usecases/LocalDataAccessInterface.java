package usecases;

import entities.Portfolio;

import java.util.Map;

public interface LocalDataAccessInterface {
    Portfolio getCurrentPortfolio();
    void writeCurrentPortfolio();
    Map<String, String> getNameToSymbolMap();
    Map<String, String> getSymbolToNameMap();
}
