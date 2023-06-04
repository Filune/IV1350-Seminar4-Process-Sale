package se.kth.iv1350.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.model.Receipt;
import se.kth.iv1350.model.Sale;
import se.kth.iv1350.utility.Amount;

/**
 * JUnit version 5.9.3 is used.
 */
public class PrinterTest {

    private Printer printer;

    @BeforeEach
    public void prepareTest() {
        printer = new Printer();
    }

    @AfterEach
    public void cleanup() {
        printer = null;
    }

    @Test
    public void testPrintReceipt() {
        // Arrange
        Sale sale = new Sale();
        Receipt receipt = new Receipt(sale);
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);
        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);
        sale.updateSale(good);
        sale.presentGoodDescription(good);
        ByteArrayOutputStream capturedString = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedString));

        // Act
        try {
            printer.printReceipt(receipt);
        } catch (PrinterException e) {
            fail("Exception occurred while printing the receipt: " + e.getMessage());
        }

        // Assert
        String expResult = "+-----------------------+\n" +
                "| RECEIPT                |\n" +
                "+-----------------------+\n" +
                "| Sale Time: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")).toString() + " |\n" +
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
                "+-----------------------+\n\n";
        String result = capturedString.toString();
        assertEquals(expResult, result, "String doesn't match expected output.");
    }

    @Test
    public void testPrintReceiptException() {
        // Arrange
        Sale sale = new Sale();
        Receipt receipt = new Receipt(sale);
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);
        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);
        sale.updateSale(good);
        sale.presentGoodDescription(good);
        PrintStream originalSystemOut = System.out;
    
        PrintStream errorStream = new PrintStream(new ByteArrayOutputStream()) {
            @Override
            public void println(String x) {
                throw new RuntimeException("Printing error occurred.");
            }
        };
        System.setOut(errorStream);
    
        // Act and assert
        assertThrows(PrinterException.class, () -> printer.printReceipt(receipt),
            "Expected PrinterException to be thrown, but nothing was thrown.");
        
        System.setOut(originalSystemOut);
    }
}
