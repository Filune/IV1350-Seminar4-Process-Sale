package se.kth.iv1350.integration;

import se.kth.iv1350.model.Receipt;

/**
 * Printer class representing an external printer system.
 */
public class Printer {

    /**
     * Empty constructor.
     */
    public Printer() {
    }

    /**
     * Prints receipt to the specified printer.
     *
     * @param receipt Receipt to be printed.
     * @throws PrinterException If an error occurs while printing the receipt.
     */
    public void printReceipt(Receipt receipt) throws PrinterException {
        try {
            System.out.println(receipt.toString());
        } catch (Exception exception) {
            throw new PrinterException("Error printing receipt.", exception);
        }
    }
}
