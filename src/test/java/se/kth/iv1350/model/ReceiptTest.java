package se.kth.iv1350.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.integration.Good;
import se.kth.iv1350.utility.Amount;

/**
 * JUnit version 5.9.3 is used.
 */
public class ReceiptTest {

    private Receipt receipt;
    private Sale sale;

    @BeforeEach
    public void prepareTest() {
        sale = new Sale();
        receipt = new Receipt(sale);
    }

    @AfterEach
    public void cleanup() {
        sale = null;
        receipt = null;
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

        // Act
        String expResult = "+-----------------------+\n" +
                "| RECEIPT                |\n" +
                "+-----------------------+\n" +
                "| Sale Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"))
                + " |\n" +
                "+-----------------------+\n" +
                "| Goods                   |\n" +
                "+-----------------------+\n" +
                "Good: " + nameOfGood + "           " + "Price: " + price + "            " + "VAT: " + tax
                + "            " +
                "\tQuantity: " + quantityOfGoods + "\n" +
                "Price: " + price + "\n" +
                "VAT: " + tax + "\n" +
                "Grand total: " + new Amount(tax.getAmount() + price.getAmount()) + "\n" +
                "+-----------------------+\n" +
                "| Thank you for shopping  |\n" +
                "+-----------------------+\n";
        String result = receipt.toString();

        // Assert
        assertEquals(expResult, result, "The expected receipt format doesn't match the output.");
    }

}
