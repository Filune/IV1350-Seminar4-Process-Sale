package se.kth.iv1350.view;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.controller.OperationFailedException;
import se.kth.iv1350.integration.InvalidGoodException;
import se.kth.iv1350.integration.TotalRevenueFileOutput;
import se.kth.iv1350.model.InvalidPaymentException;
import se.kth.iv1350.utility.Amount;
import se.kth.iv1350.utility.LogHandler;

/**
 * A dummy interface for the application Process Sale.
 */
public class View {
    private Controller contr;
    private ErrorMessageHandler errorMsgHandler = new ErrorMessageHandler();
    private LogHandler logger = LogHandler.getInstance();
    
    private static final List<String> LIST_OF_GOODS = Arrays.asList("dbOffline", "Applef", "Apple", "Hamburger", "Cucumber");
    private static final int AMOUNT_OF_GOODS = LIST_OF_GOODS.size();
    private static final int MIN_QUANTITY_OF_EACH_GOOD = 1;
    private static final int MAX_QUANTITY_OF_EACH_GOOD = 7;
    private static final double PAID_AMOUNT = 3000;


    /**
     * Constructor creates an instance of View.
     * 
     * @param contr This is the class that handles all public function calls.
     */
    public View(Controller contr) {
        this.contr = contr;
    }

    /**
     * Simulation of inputs to the system by a cashier.
     */
    public void sample() {
        print("---Cashier starts a new sale---");
        contr.startSale();

        print("---Cashier enters item identifier---");

        scanGoods();

        print("---Cashier displays total with VAT---\n" + contr.displayTotalWithVAT());  
    
        Amount paidAmount = new Amount(PAID_AMOUNT);
        print("---Cashier enters the paid amount---\n");
        try {
            print("Entered amount is: " + paidAmount + "\n" + contr.enterPaidAmount(paidAmount));
        } catch (InvalidPaymentException exc) {
            errorMsgHandler.showErrorMsg(
                "The amount entered is less than the cost of the goods. Payment cancelled."+
                "\nPaid amount is " + exc.getPaidAmount() + " but total cost with VAT is " +
                exc.getTotalCostOfGoodsWithVAT() + ".\n");
        }

        //illegalStateExceptionSimulation();
    }

    private void print(String output) {
        System.out.println(output + "\n");
    }

    private void scanGoods() {
        for (int i = 0; i < AMOUNT_OF_GOODS; i++) {
            try {
            String output = contr.searchForGood(LIST_OF_GOODS.get(i), 
                new Amount(randomQuantityInRange(MIN_QUANTITY_OF_EACH_GOOD, MAX_QUANTITY_OF_EACH_GOOD)));
            print(output);
            } catch (InvalidGoodException exception) {
                errorMsgHandler.showErrorMsg("Invalid good has been scanned: " + exception.getInvalidGood().getName() + "\n");
            } catch (OperationFailedException exception) {
                writeToLogAndUI("Failed to scan good. Please try again.\n", exception);
            } catch (Exception exception) {
                writeToLogAndUI("Failed to scan good. Please try again.\n", exception);
            }
        }
    }

    private int randomQuantityInRange(int minQuantity, int maxQuantity) {
        Random random = new Random();
        return random.nextInt(minQuantity, maxQuantity + 1);
    }

    private void writeToLogAndUI(String uiMsg, Exception exc) {
        errorMsgHandler.showErrorMsg(uiMsg);
        logger.logException(exc);
    }

    public void illegalStateExceptionSimulation() {
        try {
            contr.displayTotal();
        } catch (IllegalStateException exc) {
            errorMsgHandler.showErrorMsg("Start the sale before scanning a good.");
        }

        contr.displayTotal();
    }
}
