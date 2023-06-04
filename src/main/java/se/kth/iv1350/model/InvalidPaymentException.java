package se.kth.iv1350.model;

import se.kth.iv1350.utility.Amount;

/**
 * Exception thrown when the payment amount is invalid or insufficient.
 */
public class InvalidPaymentException extends Exception {

    private Amount paidAmt;
    private Amount totalCostOfGoodsWithVAT;

    public InvalidPaymentException(String message, Amount paidAmt, Amount totalCostOfGoodsWithVAT) {
        super(message);
        this.paidAmt = paidAmt;
        this.totalCostOfGoodsWithVAT = totalCostOfGoodsWithVAT;
    }

    public Amount getPaidAmount() {
        return paidAmt;
    }

    public Amount getTotalCostOfGoodsWithVAT() {
        return totalCostOfGoodsWithVAT;
    }

}
