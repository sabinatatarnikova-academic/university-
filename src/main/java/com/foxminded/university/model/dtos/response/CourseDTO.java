package com.foxminded.university.model.dtos.response;

import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

    private String id;
    private String name;
    private List<StudyClassResponse> studyClasses;
}
