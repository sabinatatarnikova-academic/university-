package com.foxminded.university.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.config.TeacherRestControllerConfig;
import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.CourseTeacherRequest;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.users.TeacherClassUpdateRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherRestController.class)
@ContextConfiguration(classes = {TestConfig.class, TeacherRestControllerConfig.class})
class TeacherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private StudyClassService studyClassService;

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTeachersList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<TeacherResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(TeacherResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        when(userService.findAllTeachersWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/teacher")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Bob"))
                .andExpect(jsonPath("$.content[0].lastName").value("Johnson"));
    }

    @Test
    void testGetAllCoursesList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<CourseDTO> coursePage = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Course A")
                .build()));

        when(courseService.findAllCoursesWithPagination(page)).thenReturn(coursePage);

        mockMvc.perform(get("/api/v1/teacher/courses")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Course A"));
    }

    @Test
    void testGetAllGroupsList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<GroupAssignResponse> groupPage = new PageImpl<>(Collections.singletonList(GroupAssignResponse.builder()
                .name("Group A")
                .build()));

        when(groupService.findAllGroupsResponsesWithPagination(page)).thenReturn(groupPage);

        mockMvc.perform(get("/api/v1/teacher/groups")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Group A"));
    }

    @Test
    void testDeleteStudentFromGroup() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/api/v1/teacher/classes/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(studyClassService, times(1)).deleteTeacherFromStudyClass(id);
    }

    @Test
    void testAddStudyClassToTeacher() throws Exception {
        TeacherClassUpdateRequest request = TeacherClassUpdateRequest.builder()
                .id("1")
                .studyClassesIds(Collections.singletonList("1"))
                .build();

        mockMvc.perform(post("/api/v1/teacher/classes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateTeacherWithStudyClasses(request);
    }

    @Test
    void testEditCourse() throws Exception {
        CourseTeacherRequest courseRequest = CourseTeacherRequest.builder()
                .id("1")
                .build();

        mockMvc.perform(post("/api/v1/teacher/courses/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(courseService, times(1)).updateCourse(courseRequest);
    }

    @Test
    void testShowEditGroupForm() throws Exception {
        String groupId = "1";
        RequestPage page = PageUtils.createPage(0, 10);
        GroupEditResponse editResponse = GroupEditResponse.builder()
                .group(GroupRequest.builder().id(groupId).name("Group A").build())
                .build();

        when(studyClassService.getAllRequiredDataForGroupEdit(groupId, page)).thenReturn(editResponse);

        mockMvc.perform(get("/api/v1/teacher/groups/edit")
                        .param("id", groupId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.group.name").value("Group A"));
    }

    @Test
    void testEditGroup() throws Exception {
        GroupRequest groupRequest = GroupRequest.builder()
                .id("1")
                .name("Updated Group")
                .build();

        mockMvc.perform(post("/api/v1/teacher/groups/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(groupService, times(1)).updateGroup(groupRequest);
    }

    @Test
    void testShowScheduleClasses() throws Exception {
        LocalDate userDate = LocalDate.of(2024, 7, 1);
        ScheduleViewResponse response = ScheduleViewResponse.builder()
                .groupName("Group A")
                .build();

        when(scheduleService.getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(anyString(), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/teacher/schedule")
                        .param("id", "teacher1")
                        .param("userDate", "2024-07-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("Group A"));
    }
}
