package com.foxminded.university.utils;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class PageUtilsTest {

    private final PageUtils pageUtils = new PageUtils();

    @Test
    void testGetResultWhenValidCase() {
        int firstExpected = 5;
        int secondExpected = 15;
        RequestPage result = pageUtils.getValidatedPageParameters("5", "15");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }

    @Test
    void testGetResultWhenFullInvalidCase() {
        int firstExpected = 0;
        int secondExpected = 10;
        RequestPage result = pageUtils.getValidatedPageParameters("test", "invalid");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }

    @Test
    void testGetResultWhenPageInvalidValue() {
        int firstExpected = 0;
        int secondExpected = 15;
        RequestPage result = pageUtils.getValidatedPageParameters("test", "15");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }
    @Test
    void testGetResultWhenSizeInvalidValue() {
        int firstExpected = 4;
        int secondExpected = 10;
        RequestPage result = pageUtils.getValidatedPageParameters("4", "invalid");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }
}
