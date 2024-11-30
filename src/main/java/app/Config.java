package app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Config {
    public static final String API_KEY = initialize_api_key();
    private static String initialize_api_key() {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("api_key.txt")) {
            if (inputStream != null) {
                Scanner sc = new Scanner(inputStream);
                if (sc.hasNextLine()) {
                    return sc.nextLine().trim();
                }
                else {
                    throw new RuntimeException("API key not found.");
                }
            }
            else {
                throw new RuntimeException("api_key.txt is not found.");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error occurred while trying to get resource:\n" + e.getMessage());
        }
    }
}
