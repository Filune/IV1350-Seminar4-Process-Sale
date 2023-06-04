package se.kth.iv1350.integration;

import java.time.LocalDateTime;
import java.util.HashMap;

import se.kth.iv1350.model.Register;
import se.kth.iv1350.model.Sale;


/**
 * This class handles the interaction with an external accounting system.
 */
public class LedgerSystem {

    private HashMap<LocalDateTime, Sale> accountingLog = new HashMap<>();
    private InventoryManager inventoryManager;
    private Register register;

    /**
     * Constructs a new instance of the LedgerSystem class.
     */
    public LedgerSystem() {
        inventoryManager = new InventoryManager();
        register = new Register();
    }

    /**
     * Gets the external inventory manager used by this instance of the LedgerSystem.
     * 
     * @return The external inventory manager.
     */
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }


    /**
     * Gets the register used by this instance of the LedgerSystem.
     * 
     * @return The register.
     */
    public Register getRegister() {
        return register;
    }

    /**
     * Saves the time of a sale in the accounting log.
     * 
     * @param sale The sale whose time is to be saved.
     */
    public void saveTimeOfSale(Sale sale){
        LocalDateTime currentTime = LocalDateTime.now();
        accountingLog.put(currentTime, sale);
    }
}
