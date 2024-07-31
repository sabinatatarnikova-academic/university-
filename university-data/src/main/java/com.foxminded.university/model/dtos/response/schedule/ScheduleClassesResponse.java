package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleClassesResponse {

    private List<ScheduleTimes> times;
    private List<DayOfWeek> days;

    @ToString.Exclude
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
