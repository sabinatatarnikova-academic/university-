package com.foxminded.university.controller;

import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.CourseTeacherRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.users.TeacherClassUpdateRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeacherController.class)
@Import(TestSecurityConfig.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    WebClient webClient;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private StudyClassService studyClassService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        this.webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void testShowTeacherList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<TeacherResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(TeacherResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        when(userService.findAllTeachersWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/teacher").param("pageDtoImpl", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher"))
                .andExpect(model().attributeExists("teacherPage"))
                .andExpect(model().attribute("teacherPage", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/teacher");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Bob"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Johnson"));
    }

    @Test
    void testShowAllCoursesList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<CourseDTO> coursePage = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Course A")
                .build()));

        when(courseService.findAllCoursesWithPagination(page)).thenReturn(coursePage);

        mockMvc.perform(get("/teacher/courses").param("coursePage", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_courses"))
                .andExpect(model().attributeExists("coursesPage"))
                .andExpect(model().attribute("coursesPage", coursePage));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/teacher/courses");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Course A"));
    }

    @Test
    void testShowAllGroupsList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<GroupAssignResponse> groupPage = new PageImpl<>(Collections.singletonList(GroupAssignResponse.builder()
                .name("Group A")
                .build()));

        when(groupService.findAllGroupsResponsesWithPagination(page)).thenReturn(groupPage);

        mockMvc.perform(get("/teacher/groups").param("groupsPage", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_groups"))
                .andExpect(model().attributeExists("groupsPage"))
                .andExpect(model().attribute("groupsPage", groupPage));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/teacher/groups");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Group A"));
    }

    @Test
    void testDeleteStudentFromGroup() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/teacher/classes/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher"));

        verify(studyClassService, times(1)).deleteTeacherFromStudyClass(id);
    }

    @Test
    void testShowStudyClasses() throws Exception {
        String userId = "1";
        UserFormRequest user = UserFormRequest.builder()
                .id(userId)
                .firstName("Bob")
                .lastName("Johnson")
                .userType("TEACHER")
                .build();

        List<StudyClassResponse> studyClasses = List.of(
                StudyClassResponse.builder()
                        .id("1")
                        .courseName("Course A")
                        .groupName("Group A")
                        .build()
        );

        when(userService.findUserDTOById(userId)).thenReturn(user);
        when(studyClassService.findAllClasses()).thenReturn(studyClasses);

        mockMvc.perform(get("/teacher/classes/add").param("id", userId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_add-classes"));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/teacher/classes/add?id=1");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Course A"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Group A"));
    }

    @Test
    void tesAddStudyClassToTeacher() throws Exception {
        String id = "1";
        Teacher teacher = Teacher.builder()
                .id(id)
                .firstName("test")
                .lastName("test")
                .userType("TEACHER")
                .studyClasses(new ArrayList<>())
                .build();
        when(userService.findUserById(id)).thenReturn(teacher);

        mockMvc.perform(post("/teacher/classes/add")
                        .param("id", "1")
                        .param("studyClassesIds", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher"));

        verify(userService, times(1)).updateTeacherWithStudyClasses((TeacherClassUpdateRequest) any());
    }

    @Test
    void testShowEditCourseForm() throws Exception {
        String courseId = "1";
        CourseRequest course = CourseRequest.builder()
                .id(courseId)
                .name("test")
                .build();
        when(courseService.findCourseDTOById(courseId)).thenReturn(course);

        mockMvc.perform(get("/teacher/courses/edit").param("id", courseId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_courses-edit"));
    }

    @Test
    void testEditCourse() throws Exception {
        String courseId = "1";
        Course course = Course.builder()
                .id(courseId)
                .name("test")
                .studyClasses(new ArrayList<>())
                .build();
        when(courseService.findCourseById(courseId)).thenReturn(course);

        mockMvc.perform(post("/teacher/courses/edit")
                        .param("id", "id")
                        .param("studyClasses", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher/courses"));

        verify(courseService, times(1)).updateCourse((CourseTeacherRequest) any());
    }

    @Test
    void testShowEdiGroupForm() throws Exception {
        String groupId = "1";
        GroupRequest group = GroupRequest.builder()
                .id(groupId)
                .name("ONLINE")
                .build();
        RequestPage page = PageUtils.createPage(0, 10);
        Page<StudentResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(StudentResponse.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(GroupFormation.builder().name("Group A").build())
                .build()));
        List<StudyClassResponse> studyClassResponses = new ArrayList<>(Collections.singletonList(
                StudyClassResponse.builder()
                        .id("1")
                        .courseName("Course A")
                        .groupName("ONLINE")
                        .build()));
        GroupEditResponse editResponse = GroupEditResponse.builder()
                .group(group)
                .students(pageDtoImpl)
                .studyClasses(studyClassResponses)
                .build();

        when(studyClassService.getAllRequiredDataForGroupEdit(groupId, page)).thenReturn(editResponse);

        mockMvc.perform(get("/teacher/groups/edit").param("id", groupId).param("page", "0").param("size", "10").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_groups-edit"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/teacher/groups/edit?id=1");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Charlie"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Williams"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Course A"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("ONLINE"));
    }

    @Test
    void testEditGroup() throws Exception {
        String groupId = "1";
        Group group = Group.builder()
                .id(groupId)
                .name("ONLINE")
                .build();
        when(groupService.findGroupById(groupId)).thenReturn(group);

        mockMvc.perform(post("/teacher/groups/edit")
                        .param("id", "id")
                        .param("name", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teacher/groups"));

        verify(groupService, times(1)).updateGroup((GroupRequest) any());
    }
}
