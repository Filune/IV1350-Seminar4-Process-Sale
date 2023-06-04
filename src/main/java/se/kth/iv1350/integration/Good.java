package se.kth.iv1350.integration;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.utility.Amount;

/**
 * Represents a good.
 */
public class Good {
    private final GoodDTO goodDescription;
    private final String name;
    private Amount quantity;

    /**
     * Creates a new instance of a good.
     * 
     * @param goodDescription The description of the good.
     * @param name The name of the good.
     * @param quantity The quantity of the good.
     */
    public Good(GoodDTO goodDescription, String name, Amount quantity) {
        this.goodDescription = goodDescription;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Gets the name of the good.
     * 
     * @return The name of the good.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the good.
     * 
     * @return The description of the good.
     */
    public GoodDTO getGoodDescription() {
        return goodDescription;
    }

    /**
     * Gets the quantity of the good.
     * 
     * @return The quantity of the good.
     */
    public Amount getQuantity() {
        return quantity;
    }

    /**
     * Increases the quantity of the good.
     * 
     * @param newQuantity The amount to increase the quantity by.
     */
    public void increaseQuantityOfGood(Amount newQuantity) {
        this.quantity = this.quantity.addAmount(newQuantity);
    }

    /**
     * Decreases the quantity of the good.
     * 
     * @param newQuantity The amount to decrease the quantity by.
     * @throws IllegalArgumentException If the new quantity is negative or invalid.
     */
    public void decreaseQuantityOfGood(Amount newQuantity) {
        this.quantity = this.quantity.subtractAmount(newQuantity);
    }

    /**
     * Returns a string representation of the good.
     * 
     * @return A string representation of the good.
     */
    @Override
    public String toString() {
        return String.format("Good: %s\tQuantity: %s\tDescription of Good: %s", 
        name, quantity, goodDescription.toString());
    }
}
