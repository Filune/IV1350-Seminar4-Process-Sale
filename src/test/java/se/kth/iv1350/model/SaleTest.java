package se.kth.iv1350.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.integration.Good;
import se.kth.iv1350.utility.Amount;

/**
 * JUnit version 5.9.3 is used.
 */
public class SaleTest {

    private Sale sale;

    @BeforeEach
    public void prepareTest() {
        sale = new Sale();
    }

    @AfterEach
    public void cleanup() {
        sale = null;
    }

    @Test
    public void testPresentGoodDescription() {
        // Arrange
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);

        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);

        // Act
        String expResult = String.format("Good: %-15s Price: %-15s VAT: %-14s ", nameOfGood, price, tax);
        String result = sale.presentGoodDescription(good);

        // Assert
        assertEquals(expResult, result, "Expected good update doesn't match actual good update.");
    }

    @Test
    public void testToString() {
        // Arrange
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);

        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);

        sale.updateSale(good);
        sale.presentGoodDescription(good);

        // Act
        String expResult = String.format(
                "Good: %-15s Price: %-15s VAT: %-15s\tQuantity: %-1s\nPrice: %-1s\nVAT: %-1s\nGrand total: %-1s\n",
                nameOfGood, price, tax, quantityOfGoods, price, tax, new Amount(price.getAmount() + tax.getAmount()));
        String result = sale.toString();

        // Assert
        assertEquals(expResult, result, "Expected format of toString different from actual format.");
    }

}
