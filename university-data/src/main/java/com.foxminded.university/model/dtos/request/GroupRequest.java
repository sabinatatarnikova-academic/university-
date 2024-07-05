package com.foxminded.university.model.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRequest {

    private String id;
    private String name;
    private List<String> students;
    private List<String> studyClasses;
}
