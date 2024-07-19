package com.foxminded.university.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageUtils {

    private static int DEFAULT_PAGE_INDEX;
    private static int DEFAULT_PAGE_SIZE;

    public static RequestPage createPage(String pageStr, String sizeStr) {
        int page = parseOrGetDefault(pageStr, DEFAULT_PAGE_INDEX);
        int size = parseOrGetDefault(sizeStr, DEFAULT_PAGE_SIZE);
        return new RequestPage(page, size);
    }

    public static RequestPage createPage(int page, int size) {
        return new RequestPage(page, size);
    }

    @Value("${app.pagination.default-page}")
    public void setDefaultPage(int defaultPage) {
        DEFAULT_PAGE_INDEX = defaultPage;
    }

    private static int parseOrGetDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Value("${app.pagination.default-size}")
    public void setDefaultSize(int defaultSize) {
        DEFAULT_PAGE_SIZE = defaultSize;
    }
}
