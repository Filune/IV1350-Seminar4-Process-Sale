package se.kth.iv1350.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.integration.Good;
import se.kth.iv1350.utility.Amount;


/**
 * JUnit version 5.9.3 is used.
 */
public class RegisterTest {
    
    private Register register;

    @BeforeEach
    public void prepareTest() {
        register = new Register();
    }

    @AfterEach
    public void cleanup() {
        register = null;
    }

    @Test
    public void testAddPayment() {
        // Arrange
        String nameOfGood = "Apple";
        Amount price = new Amount(10);
        Amount tax = new Amount(2);
        Amount quantityOfGoods = new Amount(1);
        Amount paidAmount = new Amount(12);
    
        GoodDTO goodDTO = new GoodDTO(nameOfGood, price, tax);
        Good good = new Good(goodDTO, nameOfGood, quantityOfGoods);
    
        Sale sale = new Sale();
        sale.updateSale(good);
    
        Payment payment = new Payment(paidAmount, sale.getTotal());
    
        double expResult = 0.0;
    
        // Act
        double resultBefore = register.getBalance().getAmount();
        register.addPayment(payment);
        double resultAfter = register.getBalance().getAmount();
    
        // Assert
        assertEquals(expResult, resultBefore, "Balance in register is not zero.");
        assertEquals(paidAmount.getAmount(), resultAfter, "Register balance did not increase by correct amount.");
    }    
}
