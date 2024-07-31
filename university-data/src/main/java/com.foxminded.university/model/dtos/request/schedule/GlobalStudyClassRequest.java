package com.foxminded.university.model.dtos.request.schedule;

import com.foxminded.university.model.enums.Regularity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalStudyClassRequest {

    private String id;
    private String scheduleId;
    private DayOfWeek scheduleDay;
    private String scheduleTimeId;
    private Regularity regularity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String courseId;
    private String teacherId;
    private String groupName;
    private String groupId;
    private String classType;
    private String locationId;
    private String url;
    private String userZone;
}
