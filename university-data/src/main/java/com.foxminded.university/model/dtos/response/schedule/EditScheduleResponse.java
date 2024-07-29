package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.enums.Regularity;
import com.foxminded.university.model.enums.ScheduleDay;
import com.foxminded.university.model.enums.ScheduleTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditScheduleResponse {

    private String id;
    private String globalClassId;
    private ScheduleTime scheduleTime;
    private ScheduleDay scheduleDay;
    private String groupId;
    private String courseId;
    private String teacherId;
    private Regularity regularity;
    private String classType;
    private String url;
    private String locationId;
    private String userZone;
}
