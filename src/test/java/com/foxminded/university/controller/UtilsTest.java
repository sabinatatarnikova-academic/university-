package com.foxminded.university.controller;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

class UtilsTest {

    private final Utils utils = new Utils();

    @Test
    void testGetResultWhenValidCase() {
        Integer firstExpected = 5;
        Integer secondExpected = 15;
        Map<Integer, Integer> result = utils.getResult("5", "15");
        assertEquals(firstExpected, result.get(0));
        assertEquals(secondExpected, result.get(1));
    }

    @Test
    void testGetResultWhenFullInvalidCase() {
        Integer firstExpected = 0;
        Integer secondExpected = 10;
        Map<Integer, Integer> result = utils.getResult("test", "invalid");
        assertEquals(firstExpected, result.get(0));
        assertEquals(secondExpected, result.get(1));
    }

    @Test
    void testGetResultWhenPageInvalidValue() {
        Integer firstExpected = 0;
        Integer secondExpected = 15;
        Map<Integer, Integer> result = utils.getResult("test", "15");
        assertEquals(firstExpected, result.get(0));
        assertEquals(secondExpected, result.get(1));
    }
    @Test
    void testGetResultWhenSizeInvalidValue() {
        Integer firstExpected = 4;
        Integer secondExpected = 10;
        Map<Integer, Integer> result = utils.getResult("4", "invalid");
        assertEquals(firstExpected, result.get(0));
        assertEquals(secondExpected, result.get(1));
    }
}
