package se.kth.iv1350.startup;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.LedgerSystem;
import se.kth.iv1350.integration.Printer;
import se.kth.iv1350.integration.TotalRevenueFileOutput;
import se.kth.iv1350.view.TotalRevenueView;
import se.kth.iv1350.view.View;

/**
 * Main sequence simulating a startup.
 */
public class Main {
    
    /**
     * Starts application and executes sample code.
     */
    public static void main(String[] args) {
        Printer printer = new Printer();
        LedgerSystem ledger = new LedgerSystem();
        Controller contr = new Controller(printer, ledger);
        contr.addObserver(new TotalRevenueFileOutput());
        contr.addObserver(new TotalRevenueView());
        View view = new View(contr);
        view.sample();
    }
}
