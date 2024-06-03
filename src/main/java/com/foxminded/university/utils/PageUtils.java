package com.foxminded.university.utils;

import org.springframework.stereotype.Service;

@Service
public class PageUtils {

    private static int parseOrGetDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public RequestPage getValidatedPageParameters(String pageStr, String sizeStr) {
        int page = parseOrGetDefault(pageStr, 0);
        int size = parseOrGetDefault(sizeStr, 10);
        return new RequestPage(page, size);
    }
}
