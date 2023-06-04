package se.kth.iv1350.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.utility.Amount;

/**
 * JUnit version 5.9.3 is used.
 */
public class GoodCatalogueTest {

    private GoodCatalogue goodCatalogue;

    @BeforeEach
    public void prepareTest() {
        goodCatalogue = GoodCatalogue.getInstance();
    }

    @AfterEach
    public void cleanup() {
        goodCatalogue = null;
    }

    @Test
    public void testContainsGood() {
        // Arrange
        String goodName = "Apple";
        boolean expected = true;

        // Act
        boolean actual = goodCatalogue.containsGood(goodName);

        // Assert
        assertEquals(expected, actual, "Expected good was not found in the catalogue.");
    }

    @Test
    public void testGetGoodCorrectObject() {
        //Arrange
        String nameOfGood = "Apple";
        Amount quantityOfGood = new Amount(1);
        Good good = null;

        // Act
        try {
            good = goodCatalogue.getGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | SQLException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert
        assertNotNull(good, "Returned Good object should not be null");
        assertEquals(nameOfGood, good.getName(), "Returned Good object has incorrect name");
        assertEquals(quantityOfGood, good.getQuantity(), "Returned Good object has incorrect quantity");
    }
    

    @Test
    public void testGetGoodObjectNotNull() throws InvalidGoodException, SQLException {
        // Arrange
        String expectedName = "Apple";
        Amount expectedGoodQuantity = new Amount(10);
        GoodDTO expectedGoodDTO = new GoodDTO(expectedName, new Amount(30), new Amount(2));
        Amount expectedTotalPrice = expectedGoodDTO.getPrice().multiplyAmount(expectedGoodQuantity);

        // Act
        Good actualGood = goodCatalogue.getGood(expectedName, expectedGoodQuantity);
        String actualName = actualGood.getName();
        double actualQuantity = actualGood.getQuantity().getAmount();
        double actualTotalPrice = actualGood.getGoodDescription().getPrice().getAmount() * actualQuantity;
        String actualGoodDTO = actualGood.getGoodDescription().toString();

        // Assert that every attribute is the same, essentially proving the objects are
        // equal.
        assertNotNull(actualGood, "The returned Good object should not be null");
        assertEquals(expectedName, actualName, "The name of the returned Good object is incorrect");
        assertEquals(expectedGoodQuantity.getAmount(), actualQuantity,
                "The quantity of the returned Good object is incorrect");
        assertEquals(expectedTotalPrice.getAmount(), actualTotalPrice,
                "The price of the returned Good object is incorrect");
        assertEquals(expectedGoodDTO.toString(), actualGoodDTO, "The DTO of the returned Good object is incorrect");
    }

    @Test
    public void testGetGoodWithNullObject() {
        // Arrange
        String expectedName = null;
        Amount expectedGoodQuantity = null;

        // Act and Assert
        assertThrows(InvalidGoodException.class, () -> goodCatalogue.getGood(expectedName, expectedGoodQuantity),
        "Expected InvalidGoodException to be thrown, but it was not thrown.");
    }

    @Test
    public void testGetGoodWithInvalidScannedGood() {
        // Arrange
        String invalidGoodName = "InvalidGood";
        Amount quantity = new Amount(1);

        // Act and Assert
        assertThrows(InvalidGoodException.class, () -> goodCatalogue.getGood(invalidGoodName, quantity),
        "Expected InvalidGoodException to be thrown, but it was not thrown.");
    }

    @Test
    public void testGetGoodWithDatabaseOffline() {
        // Arrange
        String databaseOfflineName = "dbOffline";
        Amount quantity = new Amount(1);

        // Act and Assert
        assertThrows(SQLException.class, () -> goodCatalogue.getGood(databaseOfflineName, quantity),
        "Expected SQLException to be thrown, but it was not thrown.");
    }
}
