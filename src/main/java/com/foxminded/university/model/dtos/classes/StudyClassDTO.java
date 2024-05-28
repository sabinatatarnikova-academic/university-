package com.foxminded.university.model.dtos.classes;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyClassDTO {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CourseDTO course;
    private GroupDTO group;
    private LocationDTO location;
    private String url;
}
