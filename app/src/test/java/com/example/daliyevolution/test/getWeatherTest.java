package com.example.daliyevolution.test;

import com.example.daliyevolution.util.getWeather;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class getWeatherTest {

    @Test
    public void getJSON() {
        assertNotNull(getWeather.getJSON("Canberra"));
    }
}