package com.foxminded.university.restcontroller;

import com.foxminded.university.config.StudentRestControllerConfig;
import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.StudyClassScheduleResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestController.class)
@ContextConfiguration(classes = {TestConfig.class, StudentRestControllerConfig.class})
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void testGetStudentsList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<StudentResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(StudentResponse.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(GroupFormation.builder().name("Group A").build())
                .build()));

        when(userService.findAllStudentsWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/student")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Charlie"))
                .andExpect(jsonPath("$.content[0].lastName").value("Williams"))
                .andExpect(jsonPath("$.content[0].group.name").value("Group A"));
    }

    @Test
    void testGetAllCoursesList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<CourseDTO> coursePage = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Group A")
                .build()));

        when(userService.showCoursesThatAssignedToStudent(page)).thenReturn(coursePage);

        mockMvc.perform(get("/api/v1/student/courses")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Group A"));
    }

    @Test
    void testGetScheduleClasses() throws Exception {
        LocalDate userDate = LocalDate.of(2024, 7, 1);
        LocalDate weekStart = userDate.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        ScheduleTimes time = ScheduleTimes.builder()
                .id("time")
                .name("First Period")
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(9, 30))
                .build();

        ZonedDateTime startTime = ZonedDateTime.of(2024, 7, 1, 8, 0, 0, 0, ZoneId.of("UTC"));

        StudyClassScheduleResponse studyClassScheduleResponse = StudyClassScheduleResponse.builder()
                .id("studyClassScheduleResponse")
                .startTime(startTime)
                .courseName("Math 101")
                .groupName("Group A")
                .teacherFirstName("John")
                .teacherLastName("Doe")
                .scheduleTime(time)
                .scheduleDay(DayOfWeek.MONDAY)
                .build();

        ScheduleViewResponse response = ScheduleViewResponse.builder()
                .times(Collections.singletonList(time))
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleByWeek(Collections.singletonList(studyClassScheduleResponse))
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .groupName("Group A")
                .build();

        when(scheduleService.getAllRequiredDataForViewingScheduleThatAssignedToStudent(any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/student/schedule")
                        .param("userDate", "2024-07-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.times[0].name").value("First Period"))
                .andExpect(jsonPath("$.scheduleByWeek[0].courseName").value("Math 101"))
                .andExpect(jsonPath("$.groupName").value("Group A"));
    }
}
