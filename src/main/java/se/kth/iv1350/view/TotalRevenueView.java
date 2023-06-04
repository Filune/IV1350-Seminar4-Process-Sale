package se.kth.iv1350.view;

import se.kth.iv1350.model.PaymentObserver;
import se.kth.iv1350.utility.Total;

/**
 * A view class that implements the PaymentObserver interface to display the total income.
 */
public class TotalRevenueView implements PaymentObserver {
    
    /**
     * Displays the total.
     * 
     * @param totalToDisplay The new total amount to add to the total income.
     */
    @Override
    public void updateTotalIncome(Total totalToDisplay) {
        System.out.println("DISPLAY FOR TotalRevenueView: " + totalToDisplay.getTotalPriceWithTax().getAmount());
    }
}
