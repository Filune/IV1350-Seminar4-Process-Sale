package se.kth.iv1350.controller;

/**
 * Exception thrown when an operation fails due to an underlying cause.
 */
public class OperationFailedException extends Exception {
    
    /**
     * Constructs a new {@code OperationFailedException} with the specified detail message
     * and exception.
     *
     * @param message The detailed message.
     * @param exception   The thrown exception.
     */
    public OperationFailedException(String message, Exception exception) {
        super(message, exception);
    }
}
