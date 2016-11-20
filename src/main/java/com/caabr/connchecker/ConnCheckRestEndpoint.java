package com.caabr.connchecker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnCheckRestEndpoint {

    private final SimpleSocketTest simpleSocketTest = new SimpleSocketTest();

    @RequestMapping("/testconn")
    public ConnectionCheckResult testConnection(@RequestParam(value = "host") String hostname, @RequestParam(value = "port") int port, @RequestParam boolean ignoreLocal) {

        return simpleSocketTest.hostAvailabilityCheck(hostname, port, ignoreLocal);

    }

}
