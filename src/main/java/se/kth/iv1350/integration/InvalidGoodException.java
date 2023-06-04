package se.kth.iv1350.integration;

import se.kth.iv1350.dto.GoodDTO;
import se.kth.iv1350.utility.Amount;

/**
 * The exception that is thrown when an invalid good is encountered.
 */
public class InvalidGoodException extends Exception {

    private GoodDTO invalidGood;

    /**
     * Creates a new instance of the InvalidGoodException class with the specified message,
     * invalid good name, and invalid good quantity.
     *
     * @param message               The error message associated with the exception.
     * @param invalidGoodName       The name of the invalid good.
     * @param invalidGoodQuantity   The quantity of the invalid good.
     */
    public InvalidGoodException(String message, String invalidGoodName, Amount invalidGoodQuantity) {
        super(message);
        GoodDTO invalidGood = new GoodDTO(invalidGoodName, invalidGoodQuantity, new Amount(0));
        this.invalidGood = invalidGood;
    }

    /**
     * Retrieves the invalid good.
     *
     * @return The GoodDTO object representing the invalid good.
     */
    public GoodDTO getInvalidGood() {
        return invalidGood;
    }

}
