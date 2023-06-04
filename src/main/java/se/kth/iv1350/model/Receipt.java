package se.kth.iv1350.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a receipt generated from a sale.
 */
public class Receipt {
    
    private Sale sale;

    /**
     * Creates a new receipt from a given sale.
     * 
     * @param sale The sale to generate the receipt from.
     */
    public Receipt(Sale sale) {
        this.sale = sale;
    }

    /**
     * Returns a formatted string representation of the receipt.
     * 
     * @return The receipt as a string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        addHeader(sb);
        addSaleTime(sb);
        addSaleItems(sb);
        addFooter(sb);
        return sb.toString();
    }

    private void addHeader(StringBuilder sb) {
        sb.append("+-----------------------+\n");
        sb.append(String.format("| %-23s|\n", "RECEIPT"));
        sb.append("+-----------------------+\n");
    }
    
    private void addSaleTime(StringBuilder sb) {
        LocalDateTime saleTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        sb.append(String.format("| %-23s |\n", "Sale Time: " + saleTime.format(formatter)));
    }
    
    private void addSaleItems(StringBuilder sb) {
        sb.append("+-----------------------+\n");
        sb.append(String.format("| %-23s |\n", "Goods"));
        sb.append("+-----------------------+\n");
        sb.append(sale.toString());
    }
    
    private void addFooter(StringBuilder sb) {
        sb.append("+-----------------------+\n");
        sb.append(String.format("| %-23s |\n", "Thank you for shopping"));
        sb.append("+-----------------------+\n");
    }
}
