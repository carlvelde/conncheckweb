package com.caabr.connchecker;

/**
 * Created by caabr on 2016-11-17.
 */
public class HostPort {

    private final String host;
    private final int port;

    public HostPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
