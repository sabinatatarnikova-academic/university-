package com.foxminded.university.utils;

import org.springframework.stereotype.Service;

@Service
public class PageUtils {

    public DefaultPage getValidatedPageParameters(String pageStr, String sizeStr) {
        int page;
        int size;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            page = 0;
        }
        try {
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            size = 10;
        }
        return new DefaultPage(page, size);
    }
}
