package com.foxminded.university.model.dtos.request.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyClassRequest {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classType;
    private String courseId;
    private String groupId;
    private String teacherId;
    private String locationId;
    private String url;
}
