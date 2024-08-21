package com.foxminded.university.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.config.AdminRestControllerConfig;
import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.DateRange;
import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.dtos.response.users.UserDTO;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminRestController.class)
@ContextConfiguration(classes = {TestConfig.class, AdminRestControllerConfig.class})
class AdminRestControllerTest {

    WebClient webClient;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private GroupService groupService;
    @MockBean
    private StudyClassService studyClassService;
    @MockBean
    private CourseService courseService;
    @MockBean
    private ScheduleService scheduleService;
    @MockBean
    private GlobalStudyClassesService globalStudyClassesService;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllUsersList() throws Exception {
        Page<UserDTO> pageDtoImpl = new PageImpl<>(Collections.singletonList(UserDTO.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(userService.findAllUsersWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/admin/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Bob"))
                .andExpect(jsonPath("$.content[0].lastName").value("Johnson"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddUser() throws Exception {
        UserDTO userResponse = UserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        mockMvc.perform(post("/api/v1/admin/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userResponse))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService, times(1)).saveUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditUser() throws Exception {
        UserFormRequest userFormRequest = UserFormRequest.builder()
                .id("1")
                .firstName("Jane")
                .lastName("Doe")
                .build();

        mockMvc.perform(put("/api/v1/admin/users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFormRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/users/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUserById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllCoursesList() throws Exception {
        Page<CourseDTO> pageDtoImpl = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Course1")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(courseService.findAllCoursesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/admin/courses")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Course1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddCourse() throws Exception {
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Course1")
                .build();

        mockMvc.perform(post("/api/v1/admin/courses/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(courseService, times(1)).saveCourse(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditCourse() throws Exception {
        CourseRequest courseRequest = CourseRequest.builder()
                .id("1")
                .name("Course1")
                .build();

        mockMvc.perform(put("/api/v1/admin/courses/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(courseService, times(1)).updateCourse((CourseRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/courses/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(courseService, times(1)).deleteCourseById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllStudyClassList() throws Exception {
        Page<StudyClassResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(StudyClassResponse.builder()
                .id("1")
                .classType("ONLINE")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(studyClassService.findAllClassesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/admin/classes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].classType").value("ONLINE"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddStudyClass() throws Exception {
        CreateStudyClassResponse studyClassResponse = CreateStudyClassResponse.builder()
                .classType("ONLINE")
                .build();

        mockMvc.perform(post("/api/v1/admin/classes/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studyClassResponse))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(studyClassService, times(1)).saveStudyClass(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditStudyClass() throws Exception {
        StudyClassRequest studyClassRequest = StudyClassRequest.builder()
                .id("1")
                .classType("ONLINE")
                .build();

        mockMvc.perform(put("/api/v1/admin/classes/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studyClassRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(studyClassService, times(1)).updateStudyClass((StudyClassRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudyClass() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/classes/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(studyClassService, times(1)).deleteClassById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllGroupsList() throws Exception {
        Page<GroupFormation> pageDtoImpl = new PageImpl<>(Collections.singletonList(GroupFormation.builder()
                .name("Group1")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(groupService.findAllGroupsWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/admin/groups")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Group1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddGroup() throws Exception {
        GroupFormation groupFormation = GroupFormation.builder()
                .name("Group1")
                .build();

        mockMvc.perform(post("/api/v1/admin/groups/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupFormation))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).saveGroup(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditGroup() throws Exception {
        GroupRequest groupRequest = GroupRequest.builder()
                .id("1")
                .name("Group1")
                .build();

        mockMvc.perform(put("/api/v1/admin/groups/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).updateGroup(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteGroup() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/groups/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).deleteGroupById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudentFromGroup() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/groups/students/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).deleteStudentFromGroupById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudyClassFromGroup() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/groups/classes/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).deleteStudyClassFromGroupById("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowSchedulesList() throws Exception {
        Page<ViewScheduleResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(ViewScheduleResponse.builder()
                .groupName("Group1")
                .dateRange(DateRange.builder().startDate(LocalDate.of(2024, 1, 1)).endDate(LocalDate.of(2024, 6, 1)).build())
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(scheduleService.findAllSchedulesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/admin/schedule")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].groupName").value("Group1"))
                .andExpect(jsonPath("$.content[0].dateRange.startDate").value("2024-01-01"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveSchedule() throws Exception {
        ScheduleCreateRequest scheduleCreateRequest = ScheduleCreateRequest.builder()
                .groupId("1")
                .dateRange(DateRange.builder().startDate(LocalDate.of(2024, 1, 1)).endDate(LocalDate.of(2024, 6, 1)).build())
                .build();
        when(scheduleService.addSchedule(any())).thenReturn("1");

        mockMvc.perform(post("/api/v1/admin/schedule/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scheduleCreateRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(scheduleService, times(1)).addSchedule(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddScheduleClasses() throws Exception {
        ScheduleClassesResponse scheduleClassesResponse = ScheduleClassesResponse.builder()
                .scheduleId("1")
                .groupName("Group1")
                .build();
        when(scheduleService.getAllRequiredDataForAddingClassesToSchedule("1")).thenReturn(scheduleClassesResponse);

        mockMvc.perform(get("/api/v1/admin/schedule/classes/add")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value("1"))
                .andExpect(jsonPath("$.groupName").value("Group1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddSchedule() throws Exception {
        GlobalStudyClassRequest globalStudyClassRequest = GlobalStudyClassRequest.builder()
                .id("1")
                .scheduleId("1")
                .build();

        mockMvc.perform(post("/api/v1/admin/schedule/classes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(globalStudyClassRequest)))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(globalStudyClassesService, times(1)).parseScheduleListToGlobalClasses(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowScheduleClasses() throws Exception {
        ScheduleViewResponse scheduleViewResponse = ScheduleViewResponse.builder()
                .groupName("Group1")
                .weekStart(LocalDate.of(2024, 1, 1))
                .weekEnd(LocalDate.of(2024, 1, 7))
                .build();
        when(scheduleService.getAllRequiredDataForViewingSchedule(anyString(), any(LocalDate.class)))
                .thenReturn(scheduleViewResponse);

        mockMvc.perform(get("/api/v1/admin/schedule/view")
                        .param("id", "1")
                        .param("userDate", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("Group1"))
                .andExpect(jsonPath("$.weekStart").value("2024-01-01"))
                .andExpect(jsonPath("$.weekEnd").value("2024-01-07"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditScheduleClassesForm() throws Exception {
        ScheduleClassesResponse scheduleClassesResponse = ScheduleClassesResponse.builder()
                .scheduleId("1")
                .groupName("Group1")
                .build();
        when(scheduleService.getAllRequiredDataForAddingClassesToSchedule("1")).thenReturn(scheduleClassesResponse);

        mockMvc.perform(get("/api/v1/admin/schedule/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value("1"))
                .andExpect(jsonPath("$.groupName").value("Group1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditSchedule() throws Exception {
        GlobalStudyClassRequest globalStudyClassRequest = GlobalStudyClassRequest.builder()
                .id("1")
                .scheduleId("1")
                .build();

        mockMvc.perform(post("/api/v1/admin/schedule/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singletonList(globalStudyClassRequest)))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(globalStudyClassesService, times(1)).parseScheduleListToGlobalClasses(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteSchedule() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/schedule/delete/{id}", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(scheduleService, times(1)).deleteSchedule("1");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteGlobalClass() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/schedule/class/delete")
                        .param("id", "1")
                        .param("scheduleId", "1")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(globalStudyClassesService, times(1)).deleteGlobalClass("1");
    }
}
