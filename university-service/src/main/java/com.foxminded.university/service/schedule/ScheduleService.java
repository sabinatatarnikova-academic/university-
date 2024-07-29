package com.foxminded.university.service.schedule;

import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.StudyClassScheduleResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    String addSchedule(ScheduleCreateRequest globalStudyClassRequest);

    Schedule findScheduleById(String id);

    void saveSchedule(Schedule schedule);

    List<StudyClassScheduleResponse> paginateScheduleByWeek(String id, LocalDate startDateOfWeek, LocalDate endDateOfWeek);

    Page<ViewScheduleResponse> findAllSchedulesWithPagination(RequestPage requestPage);

    void deleteSchedule(String id);

    ScheduleClassesResponse getAllRequiredDataForAddingClassesToSchedule(String id);

    ScheduleViewResponse getAllRequiredDataForViewingSchedule(String id, LocalDate userDate);

    ScheduleViewResponse getAllRequiredDataForViewingScheduleThatAssignedToStudent(LocalDate userDate);

    ScheduleViewResponse getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(String teacherId, LocalDate userDate);
}
