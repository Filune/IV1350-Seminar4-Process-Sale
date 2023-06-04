package se.kth.iv1350.integration;

/**
 * Exception thrown when there is an error with the printer.
 */
public class PrinterException extends Exception {
    public PrinterException(String message, Exception exception) {
        super(message, exception);
    }
}