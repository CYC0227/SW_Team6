package com.example.see;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapsActivityTest {

    MapsActivity mapsActivity = new MapsActivity();

    @Test
    void 위치정보_확인가능(){
        boolean is_available = mapsActivity.checkLocationServicesStatus();
        assertTrue(is_available);
    }
}