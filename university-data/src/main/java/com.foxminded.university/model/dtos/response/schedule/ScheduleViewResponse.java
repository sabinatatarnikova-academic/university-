package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.enums.ScheduleDay;
import com.foxminded.university.model.enums.ScheduleTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleViewResponse {

    private List<ScheduleTime> times;
    private List<ScheduleDay> days;
    private List<StudyClassScheduleResponse> scheduleByWeek;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String groupName;
}
