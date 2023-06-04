package se.kth.iv1350.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import se.kth.iv1350.model.PaymentObserver;
import se.kth.iv1350.utility.LogHandler;
import se.kth.iv1350.utility.Total;

/**
 * A class that implements the PaymentObserver interface and writes the total income to a file.
 */
public class TotalRevenueFileOutput implements PaymentObserver {
    private static final String FILE_NAME = "Process-Sale-TotalRevenue.log";
    private LogHandler logger = LogHandler.getInstance();

    /**
     * Updates the total income with the provided newTotalToAdd value and writes it to a file.
     *
     * @param newTotalToAdd The new total amount to add to the total income.
     */
    @Override
    public void updateTotalIncome(Total newTotalToAdd) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String content = String.format(
            "[%s] Total Income: %s%n", timestamp, newTotalToAdd.getTotalPriceWithTax().getAmount());

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(content);
        } catch (IOException exception) {
            logger.logException(exception);
            System.err.println("Failed to write total income to file: " + exception.getMessage());
        }
    }
}