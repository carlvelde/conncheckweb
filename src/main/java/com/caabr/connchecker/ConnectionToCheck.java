package com.caabr.connchecker;

import java.util.List;

/**
 * Created by caabr on 2016-11-12.
 */
public class ConnectionToCheck {

    private final List<String> descriptions;
    private final HostPort hostPort;
    private final String message;
    private final boolean valid;

    public ConnectionToCheck(List<String> descriptions, HostPort hostPort) {
        this.descriptions = descriptions;
        this.hostPort = hostPort;
        this.valid = true;
        this.message = "";
    }

    public ConnectionToCheck(List<String> descriptions, HostPort hostPort, String message, boolean valid) {
        this.descriptions = descriptions;
        this.hostPort = hostPort;
        this.message = message;
        this.valid = valid;
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
}
