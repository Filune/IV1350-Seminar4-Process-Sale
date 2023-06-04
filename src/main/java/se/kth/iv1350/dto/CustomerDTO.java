package se.kth.iv1350.dto;

/**
 * Represents a customer.
 */
public class CustomerDTO {
    private final String customerID;

    /**
     * Creates a new instance representing the specified customer.
     * 
     * @param customerID ID of the customer.
     */
    public CustomerDTO(String customerID) {
        this.customerID = customerID;
    }

    /**
     * Returns the ID of the customer.  
     * 
     * @return The ID of the customer.
     */
    public String getCustomerID() {
        return customerID;
    }
}
