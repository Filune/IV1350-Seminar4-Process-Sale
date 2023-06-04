package se.kth.iv1350.integration;

/**
 * Exception thrown when there is an error updating the inventory.
 */
public class InventoryUpdateException extends Exception {
    public InventoryUpdateException(String message, Exception exception) {
        super(message, exception);
    }
}