package com.foxminded.university.model.dtos.response;

import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResponse {

    private String id;

    private String name;

    @EqualsAndHashCode.Exclude
    private List<StudentResponse> students;

    @EqualsAndHashCode.Exclude
    private List<StudyClassResponse> studyClasses;
}
