package se.kth.iv1350.dto;

import se.kth.iv1350.utility.Amount;

/**
 * Represents a goodDTO.
 */
public class GoodDTO {
    private final String name;
    private final Amount price;
    private final Amount tax;

    /**
     * Creates a new instance representing the specified customer.
     * 
     * @param name Name of the good.
     * @param price Price of the good.
     * @param tax Tax rate for the good.
     */
    public GoodDTO(String name, Amount price, Amount tax) {
        this.name = name;
        this.price = price;
        this.tax = tax;
    }

    /**
     * Returns the price of the good.
     * 
     * @return the price of the good.
     */
    public Amount getPrice() {
        return price;
    }

    /**
     * Returns the name of the good.
     * 
     * @return the name of the good.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tax rate for the good.
     * 
     * @return the tax rate for the good.
     */
    public Amount getTax() {
        return tax;
    }

    /**
     * Returns a string representation of the good.
     * 
     * @return a string with name, price and VAT.
     */
    @Override
    public String toString() {
        return String.format("Good: %-15s Price: %-15s VAT: %-15s", name, price, tax);
    }
}
