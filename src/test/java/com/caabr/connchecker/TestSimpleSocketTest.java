package com.caabr.connchecker;

/**
 * Created by caabr on 2016-11-17.
 */
public class TestSimpleSocketTest {

    public static void main(String[] args) {

        System.out.println(SimpleSocketTest.hostAvailabilityCheck("www.google.com", 80));
        System.out.println(SimpleSocketTest.hostAvailabilityCheck("Exciter.local", 80));
        System.out.println(SimpleSocketTest.hostAvailabilityCheck("172.168.0.1", 80));
        System.out.println(SimpleSocketTest.hostAvailabilityCheck("wwww.asjkjklasd.se", 80));
        System.out.println(SimpleSocketTest.hostAvailabilityCheck("www.google.com", 4444));

    }
}
