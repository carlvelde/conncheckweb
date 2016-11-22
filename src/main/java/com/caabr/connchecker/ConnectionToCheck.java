package com.caabr.connchecker;

import java.util.List;

/**
 * Created by caabr on 2016-11-12.
 */
public class ConnectionToCheck {

    private final int rowNum;
    private final List<String> descriptions;
    private final HostPort hostPort;
    private final String message;
    private final boolean valid;

    public ConnectionToCheck(int rowNum, List<String> descriptions, HostPort hostPort) {
        this.rowNum = rowNum;
        this.descriptions = descriptions;
        this.hostPort = hostPort;
        this.valid = true;
        this.message = "";
    }

    public ConnectionToCheck(int rowNum, List<String> descriptions, String message) {
        this.rowNum = rowNum;
        this.descriptions = descriptions;
        this.message = message;
        this.hostPort = null;
        this.valid = false;
    }

    public ConnectionToCheck(int rowNum, List<String> descriptions, HostPort hostPort, String message, boolean valid) {
        this.rowNum = rowNum;
        this.descriptions = descriptions;
        this.hostPort = hostPort;
        this.message = message;
        this.valid = valid;
    }

    public int getRowNum() {
        return rowNum;
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public HostPort getHostPort() {
        return hostPort;
    }

    public String getHost() {
        return hostPort.getHost();
    }

    public int getPort() {
        return hostPort.getPort();
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ConnectionToCheck{" +
                "descriptions=" + descriptions +
                ", hostPort=" + hostPort +
                ", message='" + message + '\'' +
                ", valid=" + valid +
                '}';
    }
}
