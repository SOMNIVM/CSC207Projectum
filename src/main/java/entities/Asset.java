package entities;

/**
 * Represents a financial asset with a name.
 */
public class Asset {
    private String name;

    /**
     * Constructs an Asset with the specified name.
     *
     * @param name the name of the asset
     */
    public Asset(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the asset.
     *
     * @return the name of the asset
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the asset.
     *
     * @param name the new name of the asset
     */
    public void setName(String name) {
        this.name = name;
    }
}
