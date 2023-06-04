package se.kth.iv1350.model;

import se.kth.iv1350.utility.Total;

/**
 * The PaymentObserver interface represents an observer that receives updates on the total income.
 */
public interface PaymentObserver {

    /**
     * This method is called by the subject (Payment) to notify the observer of a total income update.
     *
     * @param totalIncome The updated total income.
     */
    void updateTotalIncome(Total totalIncome);
}
