package com.foxminded.university.model.dtos.classes;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudyClassDTO {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CourseDTO course;
    private GroupDTO group;
}
