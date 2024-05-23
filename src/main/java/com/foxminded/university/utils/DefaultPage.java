package com.foxminded.university.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefaultPage {

    private int pageNumber;
    private int pageSize;
}
