package com.caabr.connchecker;

import java.net.*;

/**
 * Created by caabr on 2016-11-12.
 */
public class SimpleSocketTest {

    private static DestinationUtils destinationUtils = new DestinationUtils();

    public static ConnectionCheckResult hostAvailabilityCheck(String hostname, int tcpPort) {
        Socket socket = new Socket();

        try {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            if (inetAddress.isSiteLocalAddress())
                return new ConnectionCheckResult(false, true, hostname + " (" + inetAddress.getHostAddress() + ") is a private network (not reachable over the internet)");
            socket.connect(new InetSocketAddress(hostname, tcpPort), 4000);
            return new ConnectionCheckResult(true, false, "");
        } catch (UnknownHostException ex) {
            return new ConnectionCheckResult(false, true, hostname + " didn't resolve in DNS");
        } catch (SocketTimeoutException ex) {
            return new ConnectionCheckResult(false, false, "Timeout");
        } catch (Exception ex) {
            return new ConnectionCheckResult(false, false, ex.getMessage());
        } finally {
            safeCloseSocket(socket);
        }

    }

    private static void safeCloseSocket(Socket socket) {
        try {
            if (socket != null && socket.isConnected())
                socket.close();

        } catch (Exception e) {
            //Ignore
        }

    }
}
