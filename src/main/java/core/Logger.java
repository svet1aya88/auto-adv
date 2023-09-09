package core;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private static org.apache.logging.log4j.Logger serviceLogger = LogManager.getRootLogger();

    public static void warn(String message) {
        serviceLogger.warn(message);
    }

    public static void warn(String format, Object arg1, Object arg2) {
        serviceLogger.warn(format, arg1, arg2);
    }

    public static void warn(String format, Object arg) {
        serviceLogger.warn(format, arg);
    }

    public static void info(String message) {
        serviceLogger.info(message);
    }

    public static void info(String format, Object arg1, Object arg2) {
        serviceLogger.info(format, arg1, arg2);
    };

    public static void info(String format, Object arg) {
        serviceLogger.info(format, arg);
    }

    public static void info(String format, Object... arguments) {
        serviceLogger.info(format, arguments);
    }

    public static void debug(String message) {
        serviceLogger.debug(message);
    }

    public static void debug(String format, Object arg) {
        serviceLogger.debug(format, arg);
    }

    public static void debug(String format, Object arg1, Object arg2) {
        serviceLogger.debug(format, arg1, arg2);
    }

    public static void error(String message) {
        serviceLogger.error(message);
    }

    public static void error(String format, Object arg1, Object arg2) {
        serviceLogger.error(format, arg1, arg2);
    }

    public static void error(String format, Object arg) {
        serviceLogger.error(format, arg);
    }
}
