package com.caabr.connchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;


public class SimpleSocketTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ConnectionCheckResult hostAvailabilityCheck(String hostname, int tcpPort, boolean ignoreLocal) {
        Socket socket = new Socket();

        try {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            if (ignoreLocal && inetAddress.isSiteLocalAddress())
                return new ConnectionCheckResult(false, true, hostname + " (" + inetAddress.getHostAddress() + ") is a private network (not reachable over the internet)");
            socket.connect(new InetSocketAddress(hostname, tcpPort), 4000);
            return new ConnectionCheckResult(true, false, "");
        } catch (UnknownHostException ex) {
            return new ConnectionCheckResult(false, true, hostname + " didn't resolve in DNS");
        } catch (SocketTimeoutException ex) {
            return new ConnectionCheckResult(false, false, "Timeout");
        } catch (Exception ex) {
            log.error("Unknown error while performing host check", ex);
            return new ConnectionCheckResult(false, false, ex.getMessage());
        } finally {
            safeCloseSocket(socket);
        }

    }

    private void safeCloseSocket(Socket socket) {
        try {
            if (socket != null && socket.isConnected())
                socket.close();

        } catch (Exception e) {
            log.error("Error while closing socket", e);
        }

    }
}
