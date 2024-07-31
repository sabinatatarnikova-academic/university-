package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.entity.ScheduleTimes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
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
    private ScheduleTimes scheduleTime;
    private DayOfWeek scheduleDay;
}
