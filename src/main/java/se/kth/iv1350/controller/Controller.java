package se.kth.iv1350.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.integration.*;
import se.kth.iv1350.model.*;

import se.kth.iv1350.utility.Amount;
import se.kth.iv1350.utility.LogHandler;

/**
 * This class is responsible for managing calls and coordination.
 */
public class Controller {
    private InventoryManager inventoryManager;
    private LedgerSystem ledgerSystem;
    private Sale sale;
    private GoodCatalogue goodCatalogue;
    private Register register;
    private Printer printer;
    private LogHandler logger = LogHandler.getInstance();
    private DiscountCatalogue discountdb;
    private List<PaymentObserver> paymentObservers;

    /**
     * Creates an instance of the Controller class, initializing its dependencies.
     * 
     * @param printer      The printer object used for printing receipts.
     * @param ledgerSystem The external accounting system used for accessing
     *                     inventory and registering payments.
     * 
     */
    public Controller(Printer printer, LedgerSystem ledgerSystem) {
        this.ledgerSystem = ledgerSystem;
        this.printer = printer;
        this.inventoryManager = ledgerSystem.getInventoryManager();
        this.register = ledgerSystem.getRegister();
        this.goodCatalogue = ledgerSystem.getInventoryManager().getGoodCatalogue();
        this.discountdb = new DiscountCatalogue();
        this.paymentObservers = new ArrayList<>();
    }

    /**
     * Searches for a good with the given name and registers it with the specified
     * quantity.
     * 
     * @param name The name of the good to search for.
     * @param quantity The quantity of the good to register.
     * @return Returns a string to show item description.
     * @return Returns a string to show item description.
     * @throws InvalidGoodException Thrown when the name of the good is invalid.
     * @throws OperationFailedException Thrown if a database error occurs during the
     *                                  retrieval of the good.
     */
    public String searchForGood(String name, Amount quantity) throws InvalidGoodException, OperationFailedException {
        checkSaleNotNull();
        Good good = registerGood(name, quantity);
        return generateGoodDescription(good, quantity);
    }

    private String generateGoodDescription(Good good, Amount quantity) {
        return String.format("%s%nQuantity: %s%n%s%n%s",
                sale.presentGoodDescription(good),
                quantity,
                displayTotal(),
                displayTotalWithVAT());
    }

    private Good registerGood(String name, Amount quantity) throws InvalidGoodException, OperationFailedException {
        checkSaleNotNull();

        Good good;
        try {
            good = goodCatalogue.getGood(name, quantity);
        } catch (InvalidGoodException exception) {
            throw exception;
        } catch (SQLException exception) {
            throw new OperationFailedException("Database is offline.", exception);
        }

        sale.updateSale(good);
        return good;
    }

    private void checkSaleNotNull() {
        if (sale == null) {
            logger.logException(new Exception("Sale not made in correct order."));
            throw new IllegalStateException("Sale has not been started.");
        }
    }

    /**
     * This method returns the running total of the sale with VAT included as a
     * string.
     * 
     * @return Returns running total with VAT as string.
     */
    public String displayTotalWithVAT() {
        checkSaleNotNull();
        return "Total with VAT: " + sale.getTotal().getTotalPriceWithTax().toString();
    }

    /**
     * This method returns the running total of the sale, without VAT, as a string.
     * 
     * @return Returns running total as string.
     */
    public String displayTotal() {
        checkSaleNotNull();
        return "Running total: " + sale.getTotal().getRunningTotal().toString();
    }

    /**
     * Handles payment for the current sale.
     * 
     * @param paidAmt The amount paid by the customer.
     * @return A string representing the change to be given to the customer.
     * @throws InvalidPaymentException Thrown if the payment amount is invalid or
     *                                 insufficient.
     */
    public String enterPaidAmount(Amount paidAmt) throws InvalidPaymentException {
        checkSaleNotNull();
        validatePaymentAmount(paidAmt);
        Payment payment = createPayment(paidAmt);
        register.addPayment(payment);
        saveTimeOfSale();
        try {
            updateInventory();
            printReceipt();
            return processPaymentResult(payment);
        } catch (InventoryUpdateException | PrinterException exception) {
            handlePaymentProcessingError(exception);
            return "An error occurred while processing the payment.";
        }
    }
    
    private void validatePaymentAmount(Amount paidAmt) throws InvalidPaymentException {
        Amount totalPriceWithTax = sale.getTotal().getTotalPriceWithTax();
        if (paidAmt.getAmount() < totalPriceWithTax.getAmount()) {
            throw new InvalidPaymentException("Insufficient payment amount.", paidAmt, totalPriceWithTax);
        }
    }
    
    private Payment createPayment(Amount paidAmt) {
        Payment payment = new Payment(paidAmt, sale.getTotal());
        payment.addObserver(paymentObservers);
        return payment;
    }
    
    private void saveTimeOfSale() {
        ledgerSystem.saveTimeOfSale(sale);
    }
    
    private void updateInventory() throws InventoryUpdateException {
        inventoryManager.updateInventory(sale);
    }
    
    private void printReceipt() throws PrinterException {
        printer.printReceipt(new Receipt(sale));
    }
    
    private String processPaymentResult(Payment payment) {
        sale = null;
        return "Change for customer: " + payment.getChange().toString();
    }
    
    private void handlePaymentProcessingError(Exception exception) {
        exception.printStackTrace();
        logger.logException(exception);
    }

    /**
     * Adds a PaymentObserver to the list of observers.
     *
     * @param paymentObserver The PaymentObserver to be added.
     */
    public void addObserver(PaymentObserver paymentObserver){
        paymentObservers.add(paymentObserver);
    }

    /**
     * This method initialises a new sale by creating a new instance of the Sale
     * class.
     */
    public void startSale() {
        this.sale = new Sale();
    }
}
