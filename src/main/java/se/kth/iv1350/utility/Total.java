package se.kth.iv1350.utility;

import se.kth.iv1350.integration.Good;

/**
 * Represents the total amount for goods, including tax.
 */
public class Total {
    
    private Amount runningTotal;
    private Amount tax;

    /**
     * Creates a new instance of the Total class.
     */
    public Total() {
        this.runningTotal = new Amount(0);
        this.tax = new Amount(0);
    }

    /**
     * Gets the current running total.
     * 
     * @return The current running total.
     */
    public Amount getRunningTotal() {
        return runningTotal;
    }

    /**
     * Gets the current tax amount.
     * 
     * @return The current tax amount.
     */
    public Amount getTax() {
        return tax;
    }

    /**
     * Gets the total price, including tax.
     * 
     * @return The total price, including tax.
     */
    public Amount getTotalPriceWithTax() {
        return runningTotal.addAmount(tax);
    }

    /**
     * Updates the total for a specific good.
     * 
     * @param good The good whose total should be updated.
     */
    public void updateTotalGood(Good good) {
        if (good == null) {
            return;
        }
        
        Amount quantityOfGoods = good.getQuantity();
        Amount goodPrice = good.getGoodDescription().getPrice();
        Amount goodTax = good.getGoodDescription().getTax();
    
        Amount totalGoodPrice = calculateTotalPrice(quantityOfGoods, goodPrice);
        Amount totalGoodTax = calculateTotalTax(quantityOfGoods, goodTax);
    
        this.tax = this.tax.addAmount(totalGoodTax);
        this.runningTotal = this.runningTotal.addAmount(totalGoodPrice);
    }
    
    private Amount calculateTotalPrice(Amount quantity, Amount price) {
        return quantity.multiplyAmount(price);
    }
    
    private Amount calculateTotalTax(Amount quantity, Amount tax) {
        return quantity.multiplyAmount(tax);
    }
    
    /**
     * Updates the total with the given Total object.
     * 
     * @param total The Total object to update the current total with.
     */
    public void updateTotal(Total total){
        this.runningTotal = this.runningTotal.addAmount(total.getRunningTotal());
        this.tax = this.tax.addAmount(total.getTax());
    }
}