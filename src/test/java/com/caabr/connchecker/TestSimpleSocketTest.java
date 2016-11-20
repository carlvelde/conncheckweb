package com.caabr.connchecker;

import org.junit.Test;



public class TestSimpleSocketTest {

    @Test
    public void run() {

        SimpleSocketTest simpleSocketTest = new SimpleSocketTest();
        System.out.println(simpleSocketTest.hostAvailabilityCheck("www.google.com", 80, true));
        System.out.println(simpleSocketTest.hostAvailabilityCheck("Exciter.local", 80, true));
        System.out.println(simpleSocketTest.hostAvailabilityCheck("172.168.0.1", 80, true));
        System.out.println(simpleSocketTest.hostAvailabilityCheck("172.168.0.1", 80, false));
        System.out.println(simpleSocketTest.hostAvailabilityCheck("wwww.asjkjklasd.se", 80, true));
        System.out.println(simpleSocketTest.hostAvailabilityCheck("www.google.com", 4444, true));

    }
}
