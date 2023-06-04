package se.kth.iv1350.utility;

/**
 * The Amount class represents the amount of different entities,
 * such as money or quantities of goods.
 */
public class Amount {

    private final double amount;

    /**
     * Constructs an instance of the Amount class.
     * 
     * @param amount the amount to be represented by this instance
     */
    public Amount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the amount represented by this instance.
     * 
     * @return the amount represented by this instance
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Adds another amount to the amount represented by this instance.
     * 
     * @param otherAmount the amount to be added
     * @return a new instance of Amount representing the sum of the two amounts
     */
    public Amount addAmount(Amount otherAmount) {
        return new Amount(this.amount + otherAmount.amount);
    }

    /**
     * Subtracts another amount from the amount represented by this instance.
     * 
     * @param otherAmount the amount to be subtracted
     * @return a new instance of Amount representing the difference between the two amounts
     */
    public Amount subtractAmount(Amount otherAmount) {
        return new Amount(this.amount - otherAmount.amount);
    }

    /**
     * Multiplies the amount represented by this instance by another amount.
     * 
     * @param otherAmount the amount to multiply by
     * @return a new instance of Amount representing the product of the two amounts
     */
    public Amount multiplyAmount(Amount otherAmount) {
        return new Amount(this.amount * otherAmount.amount);
    }

    /**
     * Retrieves a string representation of the amount represented by this instance.
     * 
     * @return a string representation of the amount represented by this instance
     */
    @Override
    public String toString() {
        return Double.toString(amount);
    }
    
}
