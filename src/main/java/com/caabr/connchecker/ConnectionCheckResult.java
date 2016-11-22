package com.caabr.connchecker;

/**
 * Created by caabr on 2016-11-11.
 */
public class ConnectionCheckResult {

    private final boolean success;
    private final boolean warning;
    private final boolean ignored;
    private final String message;

    public ConnectionCheckResult(boolean success, boolean warning, boolean ignored, String message) {
        this.success = success;
        this.warning = warning;
        this.ignored = ignored;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isWarning() {
        return warning;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ConnectionCheckResult{" +
                "success=" + success +
                ", warning=" + warning +
                ", message='" + message + '\'' +
                '}';
    }
}
