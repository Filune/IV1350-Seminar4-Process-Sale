package se.kth.iv1350.model;

import java.util.HashMap;

import se.kth.iv1350.integration.Good;
import se.kth.iv1350.utility.Total;

/**
 * Class representing a sale.
 */
public class Sale {
    private Total total;
    private HashMap<String, Good> goods = new HashMap<>();


    /**
     * Creates a new instance of Sale.
     */
    public Sale() {
        this.total = new Total();
    }

    /**
     * Retrieves the total of the sale.
     * 
     * @return The total of the sale.
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Retrieves the goods.
     * 
     * @return Returns the goods.
     */
    public HashMap<String, Good> getGoods(){
        return goods;
    }

    private boolean goodExistsInItemCatalogue(Good good) {
        return goods.containsKey(good.getName());
    }

    private void updateQuantityOfGoods(Good good) {
        Good existingGood = goods.get(good.getName());
        existingGood.increaseQuantityOfGood(good.getQuantity());
        goods.put(existingGood.getName(), existingGood);
    }

    private void addNewItem(Good good) {
        goods.put(good.getName(), good);
    }
    
    private void updateTotal(Good good) {
        total.updateTotalGood(good);
    }

    /**
     * Updates good if it already exists in current sale. Else, adds it.
     * 
     * @param good The good to be updated in the current sale.
     */
    public void updateSale(Good good) {
        if (goodExistsInItemCatalogue(good)) {
            updateQuantityOfGoods(good);
            updateTotal(good);
        } else {
            addNewItem(good);
            updateTotal(good);
        }

    }

     /**
     * Returns a string with the description of a good.
     * 
     * @param good The good for which the description is returned.
     * @return The description of the good as a string.
     */
    public String presentGoodDescription(Good good) {
        return good.getGoodDescription().toString();
    }

    /**
     * Overrides Java's default toString method and returns a string
     * representation of the sale.
     * 
     * @return A string representation of the sale, including price, quantity, tax,
     *         and total for all goods.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Good good : goods.values()) {
            appendGoodDescription(sb, good);
        }
        appendPriceInformation(sb);
        return sb.toString();
    }
    
    private void appendGoodDescription(StringBuilder sb, Good good) {
        sb.append(good.getGoodDescription())
          .append("\tQuantity: ")
          .append(good.getQuantity())
          .append("\n");
    }
    
    private void appendPriceInformation(StringBuilder sb) {
        sb.append("Price: ")
          .append(total.getRunningTotal())
          .append("\n")
          .append("VAT: ")
          .append(total.getTax())
          .append("\n")
          .append("Grand total: ")
          .append(total.getTotalPriceWithTax())
          .append("\n");
    }
}
