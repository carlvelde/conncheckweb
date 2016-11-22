package com.caabr.connchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;


public class SimpleSocketTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ConnectionCheckResult hostAvailabilityCheck(String hostname, int tcpPort, boolean ignoreLocal) {
        Socket socket = new Socket();
        InetAddress inetAddress = null;

        try {
            inetAddress = InetAddress.getByName(hostname);
            if (ignoreLocal && inetAddress.isSiteLocalAddress())
                return new ConnectionCheckResult(false, false, true, "Ignored. " + hostname + " (" + inetAddress.getHostAddress() + ") is in a private network.");
            socket.connect(new InetSocketAddress(hostname, tcpPort), 4000);
            return new ConnectionCheckResult(true, false, false, "");
        } catch (UnknownHostException ex) {
            return new ConnectionCheckResult(false, true, false, hostname + " didn't resolve in DNS (Check with ICC?)");
        } catch (SocketTimeoutException ex) {
            if (inetAddress != null && inetAddress.isSiteLocalAddress()) {
                return new ConnectionCheckResult(false, false, false, "Has WL? (Note: Local IP)");
            } else {
                return new ConnectionCheckResult(false, false, false, "Has WL?");
            }


        } catch (Exception ex) {
            log.error("Unknown error while performing host check", ex);
            return new ConnectionCheckResult(false, false, false, ex.getMessage());
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
