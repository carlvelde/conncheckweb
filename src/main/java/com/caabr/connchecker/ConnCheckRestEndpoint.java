package com.caabr.connchecker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caabr on 2016-11-11.
 */

@RestController
public class ConnCheckRestEndpoint {

    @RequestMapping("/testconn")
    public ConnectionCheckResult testConnection(@RequestParam(value = "host") String hostname, @RequestParam(value = "port") int port) {

        return SimpleSocketTest.hostAvailabilityCheck(hostname, port);

    }

}
