package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {
    private Asset asset;
    private static final String INITIAL_NAME = "Test Asset";
    private static final String NEW_NAME = "Updated Asset";

    @BeforeEach
    void setUp() {
        asset = new Asset(INITIAL_NAME);
    }

    @Test
    void testConstructor() {
        assertNotNull(asset, "Asset should be created successfully");
        assertEquals(INITIAL_NAME, asset.getName(), "Asset name should match the constructor parameter");
    }

    @Test
    void testConstructorWithNullName() {
        Asset nullAsset = new Asset(null);
        assertNull(nullAsset.getName(), "Asset should accept null name");
    }

    @Test
    void testConstructorWithEmptyName() {
        Asset emptyAsset = new Asset("");
        assertEquals("", emptyAsset.getName(), "Asset should accept empty string name");
    }

    @Test
    void testGetName() {
        assertEquals(INITIAL_NAME, asset.getName(), "getName should return the correct asset name");
    }

    @Test
    void testSetName() {
        asset.setName(NEW_NAME);
        assertEquals(NEW_NAME, asset.getName(), "setName should update the asset name correctly");
    }

    @Test
    void testSetNameToNull() {
        asset.setName(null);
        assertNull(asset.getName(), "setName should allow setting name to null");
    }

    @Test
    void testSetNameToEmptyString() {
        asset.setName("");
        assertEquals("", asset.getName(), "setName should allow setting name to empty string");
    }

    @Test
    void testNameMutation() {
        String mutableName = "Original Name";
        Asset mutationTestAsset = new Asset(mutableName);
        mutableName = "Changed Name";

        assertEquals("Original Name", mutationTestAsset.getName(),
                "Asset name should not be affected by changes to the original string");
    }
}