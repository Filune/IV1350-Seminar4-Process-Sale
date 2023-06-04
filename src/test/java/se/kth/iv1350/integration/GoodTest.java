package se.kth.iv1350.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.utility.Amount;

/**
 * JUnit version 5.9.3 is used.
 */
public class GoodTest {

    private Good good;

    @BeforeEach
    public void prepareTest() {
        good = new Good(new GoodDTO("Apple", new Amount(1),
                new Amount(2)), "Apple", new Amount(1));
    }

    @AfterEach
    public void cleanUp() {
        good = null;
    }

    @Test
    public void testIncreaseQuantityOfGoods() {
        // Assert initial quantity is 1
        double expResult = new Amount(1).getAmount();
        double result = good.getQuantity().getAmount();
        assertEquals(expResult, result, "Quantity is not 1.");

        // Act to increase quantity
        Amount increaseQuantityBy = new Amount(1);
        good.increaseQuantityOfGood(increaseQuantityBy);

        // Assert quantity has increased to 2
        expResult = new Amount(2).getAmount();
        result = good.getQuantity().getAmount();
        assertEquals(expResult, result, "Quantity was not increased to 2.");
    }

    @Test
    public void testDecreaseQuantityOfGoods() {
        // Assert initial quantity is 1
        double expectedQuantity = 1.0;
        double actualQuantity = good.getQuantity().getAmount();
        assertEquals(expectedQuantity, actualQuantity, "Quantity is not 1.");

        // Act to decrease quantity
        Amount decreaseQuantityBy = new Amount(1);
        good.decreaseQuantityOfGood(decreaseQuantityBy);

        // Assert that quantity has decreased to 0
        expectedQuantity = 0.0;
        actualQuantity = good.getQuantity().getAmount();
        assertEquals(expectedQuantity, actualQuantity, "Quantity was not decreased to 0.");
    }

    @Test
    public void testToString() {
        String expResult = String.format("Good: %s\tQuantity: %s\tDescription of Good: %s",
                good.getName(), good.getQuantity(), good.getGoodDescription());
        String result = good.toString();
        assertEquals(expResult, result, "Returned string and expected string do not match.");
    }

    @Test
    public void testGetName() {
        // Arrange
        String name = "Apple";

        // Act
        String result = good.getName();

        // Assert
        assertEquals(name, result, "Expected name does not match actual name.");
    }

    @Test
    public void testGetGoodDescription() {
        // Arrange
        String goodDTO = new GoodDTO("Apple", new Amount(1), new Amount(2)).toString();

        // Act
        String result = good.getGoodDescription().toString();

        // Assert
        assertEquals(goodDTO, result, "Expected good description does not match actual good description.");
    }

    @Test
    public void testGetQuantity() {
        // Arrange
        double quantityOfGoods = new Amount(1).getAmount();

        // Act
        double result = good.getQuantity().getAmount();

        // Assert
        assertEquals(quantityOfGoods, result, "Expected quantity does not match actual quantity.");
    }
}
