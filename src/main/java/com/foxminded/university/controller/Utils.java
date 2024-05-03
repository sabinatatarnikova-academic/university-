package com.foxminded.university.controller;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class Utils {

    public Map<Integer, Integer> getResult(String pageStr, String sizeStr) {
        Map<Integer, Integer> result = new HashMap<>();
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
        result.put(0, page);
        result.put(1, size);
        return result;
    }
}
