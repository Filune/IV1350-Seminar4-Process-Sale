package se.kth.iv1350.model;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.utility.Amount;
import se.kth.iv1350.utility.Total;

/**
 * Handles payment made by customer.
 */
public class Payment {
    private Amount paidAmount;
    private Total total;
    private List<PaymentObserver> observers;

    /**
     * Creates an instance of Payment with the specified paid amount and total.
     * 
     * @param paidAmount The amount paid by the customer.
     * @param total      The total amount to be paid, including tax.
     */
    public Payment(Amount paidAmount, Total total) {
        this.paidAmount = paidAmount;
        this.total = total;
        this.observers = new ArrayList<>();
    }

    /**
     * Retrieves the amount paid by the customer.
     * 
     * @return The amount paid by the customer.
     */
    public Amount getPaidAmount() {
        return paidAmount;
    }

    /**
     * Calculates and returns the total price with tax.
     * 
     * @return The total price with tax.
     */
    public Amount getTotal() {
        return total.getTotalPriceWithTax();
    }

    /**
     * Calculates and returns the change that the customer should receive.
     * 
     * @return The change to be given to the customer.
     */
    public Amount getChange() {
        notifyObservers();
        return paidAmount.subtractAmount(total.getTotalPriceWithTax());
    }

    /**
     * Adds observers to the Payment.
     *
     * @param observers The observers to be added.
     */
    public void addObserver(List<PaymentObserver> observers) {
        this.observers.addAll(observers);
    }

    /**
     * Removes observers from the Payment.
     *
     * @param observers The observers to be added.
     */
    public void removeObserver(List<PaymentObserver> observers) {
        this.observers.removeAll(observers);
    }

    private void notifyObservers() {
        for (PaymentObserver obs : observers) {
            obs.updateTotalIncome(total);
        }
    }

}
