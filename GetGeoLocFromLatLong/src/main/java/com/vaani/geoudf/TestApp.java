package com.vaani.geoudf;


import java.io.FileInputStream;

public class TestApp {
    public static void main(String[] args) throws Exception{
        ReverseGeoCode reverseGeoCode = ReverseGeoCode.getInstance();
        reverseGeoCode.init();
        long before = System.currentTimeMillis();
        String lat = "42.4642";
        String lng ="13.2584";

        //init("/Users/kchandra/Downloads/US/US.txt");

        System.out.println(reverseGeoCode.nearestPlace(lat,lng));
        System.out.println(System.currentTimeMillis() - before);
    }
}
