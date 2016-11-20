package com.caabr.connchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by caabr on 2016-11-17.
 */
public class DestinationUtils {


    /**
     * Gets the public ip address through ipify's api.
     *
     * @param useHttps If true, will use an https connection. If false, will use http.
     * @return The public ip address.
     * @throws IOException If there is an IO error.
     */
    public static String getPublicIp(boolean useHttps) throws IOException {
        URL ipify = useHttps ? new URL("https://api.ipify.org") : new URL("http://api.ipify.org");
        String ip = null;
        URLConnection conn;
        BufferedReader in = null;
        try {
            conn = ipify.openConnection();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ip = in.readLine();
        } finally {
            if (in != null)
                in.close();
        }
        return ip;
    }

    public boolean resolvesInDns(String hostName) {
        try {
            InetAddress.getByName(hostName);
            return true;

        } catch (UnknownHostException e) {
            return false;
        }
    }

    public boolean isPrivateNetwork(String hostName) throws UnknownHostException {
        return InetAddress.getByName(hostName).isSiteLocalAddress();
    }

    public int getPortFromURL(URL url) throws MalformedURLException {
        if (url.getPort() > 0) {
            return url.getPort();
        } else if (url.getDefaultPort() > 0) {
            return url.getDefaultPort();
        }

        throw new MalformedURLException("Could not extract port number from " + url.toString());


    }

    public HostPort extractHostPortFromUri(String uri) throws MalformedURLException {
        URL url;
        if (uri.startsWith("custom"))
            url = new URL(uri.substring(6));
        else
            url = new URL(uri);

        return new HostPort(url.getHost(), getPortFromURL(url));
    }
}
