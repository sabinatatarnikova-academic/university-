package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.enums.ScheduleDay;
import com.foxminded.university.model.enums.ScheduleTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyClassScheduleResponse {

    private String id;
    private ZonedDateTime startTime;
    private String courseName;
    private String groupName;
    private String teacherFirstName;
    private String teacherLastName;
    private ScheduleTime scheduleTime;
    private ScheduleDay scheduleDay;
}
