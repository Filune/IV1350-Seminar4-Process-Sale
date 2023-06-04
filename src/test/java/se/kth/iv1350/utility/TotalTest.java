package se.kth.iv1350.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.integration.Good;

/**
 * JUnit version 5.9.3 is used.
 */
public class TotalTest {
    private Total total;

    @BeforeEach
    public void prepareTest() {
        total = new Total();
    }

    @AfterEach
    public void cleanup() {
        total = null;
    }

    @Test
    public void testUpdateTotal() {
        // Arrange
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);

        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);

        // Act
        double expTotal = price.multiplyAmount(quantityOfGoods).getAmount();
        double expTax = tax.multiplyAmount(quantityOfGoods).getAmount();
        total.updateTotalGood(good);

        double resTotal = total.getRunningTotal().getAmount();
        double resTax = total.getTax().getAmount();

        // Assert
        assertEquals(expTotal, resTotal, "Expected running total not equal to resulting total.");
        assertEquals(expTax, resTax, "Expected tax not equal to resulting tax.");
    }

}
