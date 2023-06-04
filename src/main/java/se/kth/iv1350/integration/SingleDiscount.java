package se.kth.iv1350.integration;

import se.kth.iv1350.utility.Amount;

/**
 * Represents a single discount rate applied to the total price.
 */
public class SingleDiscount implements Discount {
    private Amount discountRate;

    /**
     * Creates a new instance of the SingleDiscount class with the specified discount rate.
     *
     * @param discountRate The discount rate to be applied.
     */
    public SingleDiscount(Amount discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * Calculates the discount amount based on the total price.
     *
     * @param totalPrice The total price to calculate the discount for.
     * @return The discount amount.
     */
    public Amount calculateDiscount(Amount totalPrice) {
        return new Amount(totalPrice.getAmount() * discountRate.getAmount());
    }
}
