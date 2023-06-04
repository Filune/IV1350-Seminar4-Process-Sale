package se.kth.iv1350.integration;

import java.util.HashMap;
import java.util.Map;

import se.kth.iv1350.dto.CustomerDTO;
import se.kth.iv1350.utility.Amount;

/**
 * Represents a catalogue of discounts for different customers.
 */
public class DiscountCatalogue {
    private Map<CustomerDTO, Discount> discountDatabase;
    private static final Amount VAT_RATE_25 = new Amount(0.25);
    private static final Amount VAT_RATE_12 = new Amount(0.12);
    private static final Amount VAT_RATE_06 = new Amount(0.06);

    /**
     * Creates a new instance of the DiscountCatalogue class.
     * Initializes the discount database and adds initial discounts.
     */
    public DiscountCatalogue() {
        discountDatabase = new HashMap<>();
        initialiseDiscounts();
    }

    private void initialiseDiscounts() {
        Discount discount1 = new SingleDiscount(VAT_RATE_25);
        Discount discount2 = new SingleDiscount(VAT_RATE_12); 
        Discount discount3 = new SingleDiscount(VAT_RATE_06);

        CompositeDiscount compositeDiscount = new CompositeDiscount();
        compositeDiscount.addDiscount(discount1);
        compositeDiscount.addDiscount(discount2);

        discountDatabase.put(new CustomerDTO("395931524"), compositeDiscount);
        discountDatabase.put(new CustomerDTO("421949249"), discount2);
        discountDatabase.put(new CustomerDTO("492149219"), discount3);
    }

    /**
     * Calculates the discount amount for a specific customer and total cost of goods.
     *
     * @param customerID The ID of the customer.
     * @param totalCostOfGoods The total cost of goods.
     * @return The discount amount.
     */
    public Amount calculateDiscount(CustomerDTO customerID, Amount totalCostOfGoods) {
        // Retrieve the discount for the given customer ID
        Discount discount = discountDatabase.get(customerID);

        if (discount == null) {
            return new Amount(0); // No discount for the customer
        }

        // Calculate and return the discount amount
        return discount.calculateDiscount(totalCostOfGoods);
    }
}
