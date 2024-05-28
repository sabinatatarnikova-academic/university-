package com.foxminded.university.model.dtos.classes;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.LocationDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfflineClassDTO {

    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CourseDTO course;
    private TeacherDTO teacher;
    private GroupDTO group;
    private LocationDTO location;
}
