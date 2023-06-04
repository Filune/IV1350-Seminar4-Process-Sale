package se.kth.iv1350.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * JUnit version 5.9.3 is used.
 */
public class AmountTest {

    private Amount firstAmount;
    private Amount secondAmount;

    @BeforeEach
    public void prepareTest() {
        firstAmount = new Amount(15);
        secondAmount = new Amount(10);
    }

    @AfterEach
    public void cleanup() {
        firstAmount = null;
        secondAmount = null;
    }

    @Test
    public void testAddAmount() {
        // Arrange
        Amount firstAmount = new Amount(10);
        Amount secondAmount = new Amount(15);
        double expected = 25;

        // Act
        double result = firstAmount.addAmount(secondAmount).getAmount();

        // Assert
        assertEquals(expected, result, "Addition of amounts did not yield correct sum.");
    }

    @Test
    public void testSubtractAmount() {
        // Arrange
        Amount firstAmount = new Amount(10);
        Amount secondAmount = new Amount(5);
        double expResult = 5;

        // Act
        double result = firstAmount.subtractAmount(secondAmount).getAmount();

        // Assert
        assertEquals(expResult, result, "Subtraction of amounts did not yield correct difference.");
    }

    @Test
    public void testMultiplyAmount() {
        // Arrange
        double expected = 150;

        // Act
        double actual = firstAmount.multiplyAmount(secondAmount).getAmount();

        // Assert
        assertEquals(expected, actual, "Multiplication of amounts did not yield correct product.");
    }

    @Test
    public void testToString() {
        // Arrange
        Amount firstAmount = new Amount(15);
        String expResult = "15.0";

        // Act
        String result = firstAmount.toString();

        // Assert
        assertEquals(expResult, result, "Expected string is not same as actual string.");
    }

}
