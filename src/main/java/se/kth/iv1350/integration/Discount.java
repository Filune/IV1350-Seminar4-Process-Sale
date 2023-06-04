package se.kth.iv1350.integration;

import se.kth.iv1350.utility.Amount;

/**
 * Represents a discount.
 */
public interface Discount {

    /**
     * Calculates the discount amount based on the provided total price.
     *
     * @param totalPrice The total price to calculate the discount from.
     * @return The discount amount.
     */
    Amount calculateDiscount(Amount totalPrice);
}
