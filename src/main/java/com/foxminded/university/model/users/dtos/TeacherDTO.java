package com.foxminded.university.model.users.dtos;

import com.foxminded.university.model.classes.StudyClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private String id;
    private String firstName;
    private String lastName;
    private List<StudyClass> studyClasses;
}
