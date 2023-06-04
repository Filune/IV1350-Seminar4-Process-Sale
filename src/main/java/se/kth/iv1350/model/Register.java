package se.kth.iv1350.model;

import se.kth.iv1350.utility.Amount;

/**
 * Represents a cash register.
 */
public class Register {
    private Amount balance;
    
    /**
     * Creates a new instance of Register.
     * Initialises the balance to 0.
     */
    public Register() {
        this.balance = new Amount(0);
    }

    /**
     * Returns the current balance of the register.
     * 
     * @return the current balance
     */
    public Amount getBalance() {
        return balance;
    }

    /**
     * Adds the specified payment to the balance.
     * 
     * @param payment the payment to be added
     */
    public void addPayment(Payment payment) {
        balance = balance.addAmount(payment.getPaidAmount());
    }
    
}
