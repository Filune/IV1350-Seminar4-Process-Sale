package se.kth.iv1350.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import se.kth.iv1350.integration.LedgerSystem;
import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.model.InvalidPaymentException;
import se.kth.iv1350.utility.Amount;
import se.kth.iv1350.integration.InvalidGoodException;

/**
 * JUnit version 5.9.3 is used.
 */
public class ControllerTest {

    private Controller contr;

    @BeforeEach
    public void prepareTest() {
        contr = new Controller(new Printer(), new LedgerSystem());
    }

    @AfterEach
    public void cleanup() {
        contr = null;
    }

    @Test
    public void testSearchForGoodWithCorrectObject() {
        // Arrange
        contr.startSale();
        String nameOfGood = "Hamburger";
        Amount quantityOfGood = new Amount(1);
        Amount price = new Amount(65);
        Amount tax = new Amount(10);
        Amount totalWithTax = new Amount(price.getAmount() + tax.getAmount());
        String expResult = String.format(
                "Good: %-16sPrice: %-16sVAT: %-15s\nQuantity: %s\nRunning total: %s\nTotal with VAT: %s",
                nameOfGood, price, tax, new Amount(1), price, totalWithTax);

        // Act
        String result = null;
        try {
            result = contr.searchForGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | OperationFailedException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert
        assertEquals(expResult, result,
                "Expected representation of good not the same as actual representation of good.");
    }

    @Test
    public void testSearchForGoodWithNullObject() {
        // Arrange
        contr.startSale();
        Amount quantityOfGood = new Amount(1);

        // Act and Assert
        assertThrows(InvalidGoodException.class, () -> {
            contr.searchForGood(null, quantityOfGood);
        }, "Expected InvalidGoodException to be thrown, but it was not thrown.");
    }

    @Test
    public void testSearchForGoodWithEmptyName() {
        // Arrange
        contr.startSale();
        Amount quantityOfGood = new Amount(1);

        // Act and Assert
        assertThrows(InvalidGoodException.class, () -> {
            contr.searchForGood("", quantityOfGood);
        }, "Expected InvalidGoodException to be thrown, but it was not thrown.");
    }

    @Test
    public void testSearchForGoodWithUnavailableGood() {
        // Arrange
        contr.startSale();
        String nameOfGood = "Nonexistent Good";
        Amount quantityOfGood = new Amount(1);

        // Act and Assert
        assertThrows(InvalidGoodException.class, () -> {
            contr.searchForGood(nameOfGood, quantityOfGood);
        }, "Expected InvalidGoodException to be thrown, but it was not thrown.");
    }

    @Test
    public void testSearchForGoodWithDatabaseErrorException() {
        // Arrange
        contr.startSale();
        String nameOfGood = "dbOffline";
        Amount quantityOfGood = new Amount(1);

        // Act and Assert
        assertThrows(OperationFailedException.class, () -> {
            contr.searchForGood(nameOfGood, quantityOfGood);
        }, "Expected OperationFailedException to be thrown, but it was not thrown.");
    }

    @Test
    public void testDisplayTotalWithVAT() {
        // Arrange
        String nameOfGood = "Hamburger";
        Amount price = new Amount(65);
        Amount tax = new Amount(10);
        Amount quantityOfGood = new Amount(1);
        contr.startSale();

        try {
            contr.searchForGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | OperationFailedException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Act
        String result = null;
        try {
            result = contr.displayTotalWithVAT();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert
        String expectedTotalWithVAT = String.format("Total with VAT: %.1f", price.getAmount() + tax.getAmount());
        assertEquals(expectedTotalWithVAT, result,
                "Expected total price with VAT differs from actual total price with VAT.");
    }

    @Test
    public void testDisplayTotal() {
        // Arrange
        contr.startSale();
        String nameOfGood = "Hamburger";
        Amount price = new Amount(65);
        Amount quantityOfGood = new Amount(1);

        try {
            contr.searchForGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | OperationFailedException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Act
        String result = null;
        try {
            result = contr.displayTotal();
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }

        // Assert
        String expResult = "Running total: " + price.getAmount();
        assertEquals(expResult, result, "Expected total price differs from actual total price.");
    }

    @Test
    public void testEnterPaidAmount() {
        // Arrange
        contr.startSale();
        String nameOfGood = "Hamburger";
        Amount quantityOfGood = new Amount(1);
        Amount paidAmount = new Amount(3000);

        try {
            contr.searchForGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | OperationFailedException e) {
            fail("Exception occurred: " + e.getMessage());
        }
        String expResult = "Change for customer: " + "2925.0";

        try {
            String result = contr.enterPaidAmount(paidAmount);
            // Assert
            assertEquals(expResult, result, "Change given is not equal to expected change to be given.");
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testEnterPaidAmountInvalidPaymentException() {
        // Arrange
        contr.startSale();
        String nameOfGood = "Hamburger"; // Price 65.
        Amount quantityOfGood = new Amount(1);

        try {
            contr.searchForGood(nameOfGood, quantityOfGood);
        } catch (InvalidGoodException | OperationFailedException e) {
            fail("Exception occurred: " +
                    e.getMessage());
        }

        Amount paidAmount = new Amount(50);

        // Act and Assert
        assertThrows(InvalidPaymentException.class, () -> {
            contr.enterPaidAmount(paidAmount);
        }, "Expected InvalidPaymentException to be thrown, but it was not thrown.");
    }

    @Test
    public void testEnterPaidAmountWithIllegalStateException() {
        // Arrange
        Amount paidAmount = new Amount(100);

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> {
            contr.enterPaidAmount(paidAmount);
        }, "Expected IllegalStateException to be thrown, but it was not thrown.");
    }

    @Test
    public void testSearchForGoodWithSaleNotStarted() {
        // Arrange
        Amount quantityOfGood = new Amount(1);
        String goodName = "Hamburger";

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> {
            contr.searchForGood(goodName, quantityOfGood);
        }, "Expected IlleglaStateException to be thrown, but it was not thrown.");
    }

    @Test
    public void testDisplayTotalWithVATWithoutSaleStarted() {
        // Act and Assert
        assertThrows(IllegalStateException.class, () -> {
            contr.displayTotalWithVAT();
        }, "Expected IllegalStateException to be thrown, but it was not thrown.");
    }

    @Test
    public void testDisplayTotalWithoutSaleStarted() {
        // Act and Assert
        assertThrows(IllegalStateException.class, () -> {
            contr.displayTotal();
        }, "Expected IllegalStateException to be thrown, but it was not thrown.");
    }
}