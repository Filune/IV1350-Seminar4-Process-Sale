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
     * The total parameter is written to file.
     *
     * @param totalToWriteToFile The new total amount to add to the total income.
     */
    @Override
    public void updateTotalIncome(Total totalToWriteToFile) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String content = String.format(
            "[%s] Total Income: %s%n", timestamp, totalToWriteToFile.getTotalPriceWithTax().getAmount());

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(content);
        } catch (IOException exception) {
            logger.logException(exception);
        }
    }
}