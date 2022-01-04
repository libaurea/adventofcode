package de.libaurea.adventofcode.common;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private boolean debug;

    public Logger() {
        this(false);
    }

    public Logger(boolean debug) {
        this.debug = debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void toggleDebug() {
        this.debug = !debug;
    }

    @SuppressWarnings("java:S106")
    public void log(Level level, String... message) {
        for (String m : message) {
            if (level != Level.DEBUG || debug) {
                System.out.println(String.format(level.format, m));
            }
        }
    }

    public void debug(String... message) {
        log(Level.DEBUG, message);
    }

    public void info(String... message) {
        log(Level.INFO, message);
    }

    public void warn(String... message) {
        log(Level.WARNING, message);
    }

    public void error(String... message) {
        log(Level.ERROR, message);
    }

    public enum Level {
        DEBUG(ANSI_BLUE + "debug: " + ANSI_RESET + "%s"),
        INFO(ANSI_GREEN + "info: " + ANSI_RESET + "%s"),
        WARNING(ANSI_YELLOW + "warn: " + ANSI_RESET + "%s"),
        ERROR(ANSI_RED + "error: " + ANSI_RESET + "%s");

        private final String format;

        Level(String format) {
            this.format = format;
        }
    }
}
