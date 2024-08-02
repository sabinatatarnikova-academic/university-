package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.entity.ScheduleTimes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleViewResponse {

    private List<ScheduleTimes> times;
    private List<DayOfWeek> days;
    private List<StudyClassScheduleResponse> scheduleByWeek;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String groupName;
}
