package com.foxminded.university.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = PageUtils.class)
class PageUtilsTest {

    @Test
    void testGetResultWhenValidCase() {
        int firstExpected = 5;
        int secondExpected = 15;
        RequestPage result = PageUtils.createPage("5", "15");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }

    @Test
    void testGetResultWhenFullInvalidCase() {
        int firstExpected = 0;
        int secondExpected = 10;
        RequestPage result = PageUtils.createPage("test", "invalid");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }

    @Test
    void testGetResultWhenPageInvalidValue() {
        int firstExpected = 0;
        int secondExpected = 15;

        RequestPage result = PageUtils.createPage("test", "15");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }
    @Test
    void testGetResultWhenSizeInvalidValue() {
        int firstExpected = 4;
        int secondExpected = 10;

        RequestPage result = PageUtils.createPage("4", "invalid");
        assertEquals(firstExpected, result.getPageNumber());
        assertEquals(secondExpected, result.getPageSize());
    }
}
