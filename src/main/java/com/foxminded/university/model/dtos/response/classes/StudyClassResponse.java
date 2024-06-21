package com.foxminded.university.model.dtos.response.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudyClassResponse {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classType;
    private String courseId;
    private String courseName;
    private String groupId;
    private String groupName;
    private String teacherId;
    private String teacherFirstName;
    private String teacherLastName;
}
