package se.kth.iv1350.utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Takes care of logging exceptions to a file. Is a singleton class.
 */
public class LogHandler {
    private static final LogHandler INSTANCE = new LogHandler();
    private static final String LOG_FILE = "Process-Sale-exceptions.log";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger logger;

    private LogHandler() {
        logger = Logger.getLogger("ExceptionLogger");
        configureLogger();
    }

    /**
     * Gets the singleton instance of LogHandler.
     *
     * @return The LogHandler instance.
     */
    public static LogHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Logs the exception to a file, including the message, date, time, and stack trace.
     *
     * @param exception The exception to be logged.
     */
    public void logException(Exception exception) {
        LocalDateTime now = LocalDateTime.now();
        String logMessage = String.format(
            "[%s] %s: %s%n", now.format(DATE_TIME_FORMATTER), exception.getClass().getSimpleName(), exception.getMessage());
        logger.log(Level.SEVERE, logMessage, exception);
    }

    private void configureLogger() {
        try {
            Logger rootLogger = Logger.getLogger("");
            rootLogger.removeHandler(rootLogger.getHandlers()[0]);
    
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
}
