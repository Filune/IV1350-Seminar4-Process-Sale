package se.kth.iv1350.integration;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.utility.Amount;

/**
 * Represents a composite discount that combines multiple discounts.
 */
public class CompositeDiscount implements Discount {
    private List<Discount> discounts;

    /**
     * Creates an instance of the CompositeDiscount class.
     */
    public CompositeDiscount() {
        discounts = new ArrayList<>();
    }

    /**
     * Adds a discount to the composite discount.
     *
     * @param discount The discount to be added.
     */
    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    /**
     * Removes a discount from the composite discount.
     *
     * @param discount The discount to be removed.
     */
    public void removeDiscount(Discount discount) {
        discounts.remove(discount);
    }

    /**
     * Calculates the total discount amount based on the provided total price.
     *
     * @param totalPrice The total price to calculate the discount from.
     * @return The total discount amount.
     */
    public Amount calculateDiscount(Amount totalPrice) {
        Amount totalDiscount = new Amount(0);
        for (Discount discount : discounts) {
            totalDiscount = new Amount(totalDiscount.getAmount() + discount.calculateDiscount(totalPrice).getAmount());
        }
        return totalDiscount;
    }
}
