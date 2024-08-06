package com.foxminded.university.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageUtils {

    private static int defaultPageIndex;
    private static int defaultPageSize;

    @Value("${app.pagination.default-page}")
    private int defaultPageIndexProperty;

    @Value("${app.pagination.default-size}")
    private int defaultPageSizeProperty;

    public static RequestPage createPage(String pageStr, String sizeStr) {
        int page = parseOrGetDefault(pageStr, defaultPageIndex);
        int size = parseOrGetDefault(sizeStr, defaultPageSize);
        return new RequestPage(page, size);
    }

    @PostConstruct
    private void init() {
        defaultPageIndex = defaultPageIndexProperty;
        defaultPageSize = defaultPageSizeProperty;
    }

    public static RequestPage createPage(int page, int size) {
        return new RequestPage(page, size);
    }

    private static int parseOrGetDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
