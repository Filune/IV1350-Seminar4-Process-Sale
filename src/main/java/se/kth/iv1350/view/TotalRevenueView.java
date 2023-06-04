package se.kth.iv1350.view;

import se.kth.iv1350.model.PaymentObserver;
import se.kth.iv1350.utility.Total;

/**
 * A view class that implements the PaymentObserver interface to display the total income.
 */
public class TotalRevenueView implements PaymentObserver {
    
    private Total totalIncome;

    /**
     * Constructs a TotalRevenueView object.
     * Initializes the totalIncome to a new Total object.
     */
    public TotalRevenueView(){
        totalIncome = new Total();
    }
    
    /**
     * Updates the total income with the provided newTotalToAdd value.
     * Calculates the updated total income by adding the newTotalToAdd to the current total income.
     * Displays the updated total income on the console.
     * 
     * @param newTotalToAdd The new total amount to add to the total income.
     */
    @Override
    public void updateTotalIncome(Total newTotalToAdd) {
        this.totalIncome.updateTotal(newTotalToAdd);
        System.out.println("DISPLAY FOR TotalRevenueView: " + totalIncome.getTotalPriceWithTax().getAmount());
    }
}
