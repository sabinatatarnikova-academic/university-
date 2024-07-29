package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
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
public class ScheduleClassesResponse {

    private List<ScheduleTime> times;
    private List<ScheduleDay> days;
    private List<GlobalStudyClass> globalStudyClasses;
    private String scheduleId;
    private String groupId;
    private String groupName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CourseDTO> courses;
    private List<TeacherResponse> teachers;
    private List<LocationDTO> locations;
}
