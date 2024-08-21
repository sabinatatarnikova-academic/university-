package com.foxminded.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxminded.university.config.AdminControllerConfig;
import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.DateRange;
import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.request.classes.CreateStudyClassRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.StudyClassScheduleResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.enums.Regularity;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = {TestSecurityConfig.class, AdminControllerConfig.class})
class AdminControllerTest {

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

    WebClient webClient;

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

        mockMvc.perform(get("/admin/users").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/admin_users"))
                .andExpect(model().attributeExists("usersPage"))
                .andExpect(model().attribute("usersPage", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/users");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Bob"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Johnson"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddUserForm() throws Exception {
        mockMvc.perform(get("/admin/users/new"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", new UserDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/add-user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddUser() throws Exception {
        mockMvc.perform(post("/admin/users/new")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("userType", "STUDENT")
                        .param("groupId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).saveUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditUserForm() throws Exception {
        String userId = "1";
        UserFormRequest user = UserFormRequest.builder()
                .id(userId)
                .firstName("Bob")
                .lastName("Johnson")
                .userType("STUDENT")
                .username("bob.johnson")
                .password("password")
                .build();
        when(userService.findUserDTOById(userId)).thenReturn(user);

        mockMvc.perform(get("/admin/users/edit").param("id", userId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/edit-user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditStudent() throws Exception {
        when(groupService.findGroupById("1")).thenReturn(new Group("1", "Math", new ArrayList<Student>(), new ArrayList<StudyClass>(), new Schedule()));

        mockMvc.perform(post("/admin/users/edit")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("userType", "STUDENT")
                        .param("groupId", "1")
                        .param("username", "username")
                        .param("password", "password")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditTeacher() throws Exception {
        mockMvc.perform(post("/admin/users/edit")
                        .param("id", "2")
                        .param("firstName", "Jane")
                        .param("lastName", "Smith")
                        .param("userType", "TEACHER")
                        .param("username", "jane.smith")
                        .param("repeatedPassword", "password")
                        .param("studyClassesIds", "1")
                        .param("studyClassesIds", "2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).updateUser(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        String userId = "1";
        mockMvc.perform(delete("/admin/users/delete/{id}", userId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllCoursesList() throws Exception {
        Page<CourseDTO> pageDtoImpl = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Name")
                .studyClasses(new ArrayList<>())
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(courseService.findAllCoursesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/courses").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course/admin_courses"))
                .andExpect(model().attributeExists("coursesPage"))
                .andExpect(model().attribute("coursesPage", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/courses");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Name"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddCourseForm() throws Exception {
        mockMvc.perform(get("/admin/courses/new"))
                .andExpect(model().attributeExists("course"))
                .andExpect(model().attribute("course", new CourseDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course/add_course"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddCourse() throws Exception {
        mockMvc.perform(post("/admin/courses/new")
                        .param("id", "id")
                        .param("name", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses"));

        verify(courseService, times(1)).saveCourse(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditCourseForm() throws Exception {
        String courseId = "1";
        CourseRequest course = CourseRequest.builder()
                .id(courseId)
                .name("test")
                .build();
        when(courseService.findCourseDTOById(courseId)).thenReturn(course);

        mockMvc.perform(get("/admin/courses/edit").param("id", courseId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course/edit_course"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditCourse() throws Exception {
        String courseId = "1";
        Course course = Course.builder()
                .id(courseId)
                .name("test")
                .studyClasses(new ArrayList<>())
                .build();
        when(courseService.findCourseById(courseId)).thenReturn(course);

        mockMvc.perform(post("/admin/courses/edit")
                        .param("id", "id")
                        .param("name", "test")
                        .param("studyClasses", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses"));

        verify(courseService, times(1)).updateCourse((CourseRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteCourse() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/courses/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/courses"));

        verify(courseService, times(1)).deleteCourseById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllStudyClassesList() throws Exception {
        Page<StudyClassResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(OnlineClassResponse.builder()
                .startTime((LocalDateTime.of(2024, 4, 23, 11, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 12, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .classType("ONLINE")
                .url("www.test.com")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(studyClassService.findAllClassesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/classes").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studyClass/admin_class"))
                .andExpect(model().attributeExists("studyClassPage"))
                .andExpect(model().attribute("studyClassPage", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/classes");
        assertThat(htmlPage.getBody().getTextContent(), containsString("ONLINE"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("www.test.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddStudyClassForm() throws Exception {
        mockMvc.perform(get("/admin/classes/new"))
                .andExpect(model().attributeExists("class"))
                .andExpect(model().attribute("class", new CreateStudyClassRequest()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studyClass/add_class"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddStudyClass() throws Exception {
        mockMvc.perform(post("/admin/classes/new")
                        .param("id", "id")
                        .param("classType", "ONLINE")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/classes"));

        verify(studyClassService, times(1)).saveStudyClass(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEdiStudyClassForm() throws Exception {
        String studyClassId = "1";
        StudyClassRequest studyClass = StudyClassRequest.builder()
                .id(studyClassId)
                .classType("ONLINE")
                .url("www.test.com")
                .build();

        when(studyClassService.findClassDTOById(studyClassId)).thenReturn(studyClass);
        when(studyClassService.getAllRequiredDataForStudyClassEdit()).thenReturn(new EditStudyClassResponse());

        mockMvc.perform(get("/admin/classes/edit")
                        .param("id", studyClassId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studyClass/edit_class"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditOnlineStudyClass() throws Exception {
        String classId = "1";
        StudyClass studyClass = OnlineClass.builder()
                .id(classId)
                .classType("ONLINE")
                .url("www.test.com")
                .build();
        when(studyClassService.findClassById(classId)).thenReturn(studyClass);

        mockMvc.perform(post("/admin/classes/edit")
                        .param("id", "id")
                        .param("classType", "test")
                        .param("url", "www.test.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/classes"));

        verify(studyClassService, times(1)).updateStudyClass((StudyClassRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditOfflineStudyClass() throws Exception {
        String classId = "1";
        StudyClass studyClass = OfflineClass.builder()
                .id(classId)
                .classType("ONLINE")
                .location(Location.builder()
                        .id("id")
                        .department("test")
                        .classroom("101")
                        .build())
                .build();
        when(studyClassService.findClassById(classId)).thenReturn(studyClass);

        mockMvc.perform(post("/admin/classes/edit")
                        .param("id", "id")
                        .param("classType", "test")
                        .param("location", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/classes"));

        verify(studyClassService, times(1)).updateStudyClass((StudyClassRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudyClass() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/classes/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/classes"));

        verify(studyClassService, times(1)).deleteClassById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllGroupsList() throws Exception {
        Page<GroupFormation> pageDtoImpl = new PageImpl<>(Collections.singletonList(GroupFormation.builder()
                .name("Group")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(groupService.findAllGroupsWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/groups").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/admin_groups"))
                .andExpect(model().attributeExists("groupsPage"))
                .andExpect(model().attribute("groupsPage", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/groups");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Group"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllStudentsAssignedToGroupList() throws Exception {
        Group group = Group.builder().id("1").name("test").build();
        List<StudentResponse> pageDtoImpl = Collections.singletonList(StudentResponse.builder()
                .id("1")
                .firstName("Test")
                .build());
        when(groupService.findGroupById("1")).thenReturn(group);
        when(groupService.findAllStudentsAssignedToGroup("1")).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/groups/students").param("id", "1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/group_students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("students", pageDtoImpl))
                .andExpect(model().attribute("group", group));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/groups/students?id=1");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Test"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllClassesAssignedToGroupList() throws Exception {
        Group group = Group.builder().id("1").name("test").build();
        List<StudyClassResponse> pageDtoImpl = Collections.singletonList(StudyClassResponse.builder()
                .id("1")
                .groupName("Group")
                .courseName("Course")
                .classType("ONLINE")
                .build());
        when(groupService.findGroupById("1")).thenReturn(group);
        when(groupService.findAllStudyClassesAssignedToGroup("1")).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/groups/classes").param("id", "1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/group_classes"))
                .andExpect(model().attributeExists("classes"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("classes", pageDtoImpl))
                .andExpect(model().attribute("group", group));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/groups/classes?id=1");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Group"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Course"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("ONLINE"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddGroupForm() throws Exception {
        mockMvc.perform(get("/admin/groups/new"))
                .andExpect(model().attributeExists("group"))
                .andExpect(model().attribute("group", new GroupFormation()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/add_group"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddGroup() throws Exception {
        mockMvc.perform(post("/admin/groups/new")
                        .param("id", "id")
                        .param("name", "name")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/groups"));

        verify(groupService, times(1)).saveGroup(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
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

        mockMvc.perform(get("/admin/groups/edit").param("id", groupId).param("page", "0").param("size", "10").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/edit_group"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attribute("students", pageDtoImpl));

        HtmlPage htmlPage = webClient.getPage("http://localhost:8080/admin/groups/edit?id=1");
        assertThat(htmlPage.getBody().getTextContent(), containsString("Charlie"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Williams"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("Course A"));
        assertThat(htmlPage.getBody().getTextContent(), containsString("ONLINE"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditGroup() throws Exception {
        String groupId = "1";
        Group group = Group.builder()
                .id(groupId)
                .name("ONLINE")
                .build();
        when(groupService.findGroupById(groupId)).thenReturn(group);

        mockMvc.perform(post("/admin/groups/edit")
                        .param("id", "id")
                        .param("name", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/groups"));

        verify(groupService, times(1)).updateGroup((GroupRequest) any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteGroup() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/groups/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/groups"));

        verify(groupService, times(1)).deleteGroupById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudentFromGroup() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/groups/students/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/groups"));

        verify(groupService, times(1)).deleteStudentFromGroupById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteStudyClassFromGroup() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/groups/classes/delete/{id}", id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/groups"));

        verify(groupService, times(1)).deleteStudyClassFromGroupById(id);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowSchedulesList() throws Exception {
        Page<ViewScheduleResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(ViewScheduleResponse.builder()
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 10, 1))
                        .endDate(LocalDate.of(2024, 12, 1)).build())
                .groupName("Group A")
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(scheduleService.findAllSchedulesWithPagination(page)).thenReturn(pageDtoImpl);


        mockMvc.perform(get("/admin/schedule").param("page", "0").param("size", "10").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/schedule/schedules_list"))
                .andExpect(model().attributeExists("schedulesPage"))
                .andExpect(model().attribute("schedulesPage", pageDtoImpl));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddSchedule() throws Exception {
        List<GroupFormation> groupFormationList = new ArrayList<GroupFormation>(Collections.singletonList(GroupFormation.builder()
                .name("Group")
                .build())) {
        };
        when(groupService.findAllGroupsWithoutSchedule()).thenReturn(groupFormationList);

        ScheduleCreateRequest expectedScheduleCreateRequest = ScheduleCreateRequest.builder()
                .dateRange(new DateRange())
                .build();

        mockMvc.perform(get("/admin/schedule/new"))
                .andExpect(model().attributeExists("schedule"))
                .andExpect(model().attribute("schedule", expectedScheduleCreateRequest))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groupFormationList))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/schedule/schedule_add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testSaveSchedule() throws Exception {
        String scheduleId = "new-schedule-id";
        when(scheduleService.addSchedule(any(ScheduleCreateRequest.class))).thenReturn(scheduleId);

        mockMvc.perform(post("/admin/schedule/new")
                        .param("startDate", "2024-07-01")
                        .param("endDate", "2024-08-11")
                        .param("groupId", "d15b0018-47e9-4281-9dc8-a5aaf2bdf951")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/schedule/classes/add?id=" + scheduleId));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddScheduleClasses() throws Exception {
        ScheduleTimes time1 = ScheduleTimes.builder()
                .id("time1")
                .name("First lecture")
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(9, 30))
                .build();

        ScheduleClassesResponse response = ScheduleClassesResponse.builder()
                .times(Arrays.asList(time1))
                .days(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
                .scheduleId("schedule1")
                .groupId("group1")
                .groupName("Group A")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courses(Arrays.asList(new CourseDTO("course1", "Mathematics", null)))
                .teachers(Arrays.asList(new TeacherResponse()))
                .locations(Arrays.asList(new LocationDTO("location1", "Main Building", "Room 101")))
                .build();

        when(scheduleService.getAllRequiredDataForAddingClassesToSchedule(anyString())).thenReturn(response);

        mockMvc.perform(get("/admin/schedule/classes/add")
                        .param("id", "schedule1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/schedule/schedule_add_classes"))
                .andExpect(model().attribute("times", response.getTimes()))
                .andExpect(model().attribute("days", response.getDays()))
                .andExpect(model().attribute("globalClass", new GlobalStudyClassRequest()))
                .andExpect(model().attribute("scheduleId", response.getScheduleId()))
                .andExpect(model().attribute("groupId", response.getGroupId()))
                .andExpect(model().attribute("groupName", response.getGroupName()))
                .andExpect(model().attribute("startDate", response.getDateRange().getStartDate()))
                .andExpect(model().attribute("endDate", response.getDateRange().getEndDate()))
                .andExpect(model().attribute("courses", response.getCourses()))
                .andExpect(model().attribute("teachers", response.getTeachers()))
                .andExpect(model().attribute("locations", response.getLocations()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddSchedule() throws Exception {
        GlobalStudyClassRequest request1 = GlobalStudyClassRequest.builder()
                .id("1")
                .scheduleId("schedule1")
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTimeId("time1")
                .regularity(Regularity.EACH_WEEK)
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courseId("course1")
                .teacherId("teacher1")
                .groupName("Group A")
                .groupId("group1")
                .classType("ONLINE")
                .locationId("location1")
                .url("http://example.com")
                .userZone("UTC")
                .build();

        GlobalStudyClassRequest request2 = GlobalStudyClassRequest.builder()
                .id("2")
                .scheduleId("schedule1")
                .scheduleDay(DayOfWeek.WEDNESDAY)
                .scheduleTimeId("time2")
                .regularity(Regularity.EACH_WEEK)
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courseId("course2")
                .teacherId("teacher2")
                .groupName("Group B")
                .groupId("group2")
                .classType("OFFLINE")
                .locationId("location2")
                .url("http://example.com")
                .userZone("UTC")
                .build();

        List<GlobalStudyClassRequest> requestList = Arrays.asList(request1, request2);

        doNothing().when(globalStudyClassesService).parseScheduleListToGlobalClasses(requestList);

        mockMvc.perform(post("/admin/schedule/classes/add")
                        .param("id", "schedule1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/schedule"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowScheduleClasses() throws Exception {
        LocalDate userDate = LocalDate.of(2024, 7, 1);
        LocalDate weekStart = userDate.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = userDate.with(DayOfWeek.SUNDAY);

        ScheduleTimes time = ScheduleTimes.builder()
                .id("time")
                .name("First lecture")
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(9, 30))
                .build();

        ZonedDateTime startTime = ZonedDateTime.of(2024, 7, 1, 8, 0, 0, 0, ZoneId.of("UTC"));

        StudyClassScheduleResponse class1 = StudyClassScheduleResponse.builder()
                .id("class1")
                .startTime(startTime)
                .courseName("Math 101")
                .groupName("Group A")
                .teacherFirstName("John")
                .teacherLastName("Doe")
                .scheduleTime(time)
                .scheduleDay(DayOfWeek.MONDAY)
                .build();

        ScheduleViewResponse response = ScheduleViewResponse.builder()
                .times(Arrays.asList(time))
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleByWeek(Arrays.asList(class1))
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .groupName("Group A")
                .build();

        when(scheduleService.getAllRequiredDataForViewingSchedule(any(String.class), any(LocalDate.class)))
                .thenReturn(response);

        mockMvc.perform(get("/admin/schedule/view")
                        .param("id", "schedule1")
                        .param("userDate", "2024-07-01"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/schedule/schedule_view"))
                .andExpect(model().attribute("times", response.getTimes()))
                .andExpect(model().attribute("days", response.getDays()))
                .andExpect(model().attribute("studyClasses", response.getScheduleByWeek()))
                .andExpect(model().attribute("groupName", response.getGroupName()))
                .andExpect(model().attribute("weekStart", response.getWeekStart()))
                .andExpect(model().attribute("weekEnd", response.getWeekEnd()))
                .andExpect(model().attribute("userDate", userDate))
                .andExpect(model().attribute("scheduleId", "schedule1"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditScheduleClassesForm() throws Exception {
        ScheduleTimes time = ScheduleTimes.builder()
                .id("time")
                .name("First lecture")
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(9, 30))
                .build();

        StudyClass studyClass = StudyClass.builder()
                .course(Course.builder().name("Course").build())
                .build();

        GlobalStudyClass globalClass = GlobalStudyClass.builder()
                .id("globalClass1")
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTime(time)
                .regularity(Regularity.EACH_WEEK)
                .studyClasses(Arrays.asList(studyClass))
                .build();

        CourseDTO course = CourseDTO.builder()
                .id("course1")
                .name("Mathematics")
                .build();

        TeacherResponse teacher = TeacherResponse.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        LocationDTO location = LocationDTO.builder()
                .id("location1")
                .department("Main Building")
                .classroom("Room 101")
                .build();

        ScheduleClassesResponse response = ScheduleClassesResponse.builder()
                .times(Arrays.asList(time))
                .days(Arrays.asList(DayOfWeek.values()))
                .globalStudyClasses(Arrays.asList(globalClass))
                .scheduleId("schedule1")
                .groupId("group1")
                .groupName("Group A")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courses(Arrays.asList(course))
                .teachers(Arrays.asList(teacher))
                .locations(Arrays.asList(location))
                .build();

        when(scheduleService.getAllRequiredDataForAddingClassesToSchedule(anyString()))
                .thenReturn(response);

        mockMvc.perform(get("/admin/schedule/edit")
                        .param("id", "schedule1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/schedule/schedule_edit"))
                .andExpect(model().attribute("times", response.getTimes()))
                .andExpect(model().attribute("days", response.getDays()))
                .andExpect(model().attribute("globalClasses", response.getGlobalStudyClasses()))
                .andExpect(model().attribute("globalClass", new GlobalStudyClassRequest()))
                .andExpect(model().attribute("scheduleId", response.getScheduleId()))
                .andExpect(model().attribute("groupId", response.getGroupId()))
                .andExpect(model().attribute("groupName", response.getGroupName()))
                .andExpect(model().attribute("startDate", response.getDateRange().getStartDate()))
                .andExpect(model().attribute("endDate", response.getDateRange().getEndDate()))
                .andExpect(model().attribute("courses", response.getCourses()))
                .andExpect(model().attribute("teachers", response.getTeachers()))
                .andExpect(model().attribute("locations", response.getLocations()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testEditSchedule() throws Exception {
        GlobalStudyClassRequest request1 = GlobalStudyClassRequest.builder()
                .id("1")
                .scheduleId("schedule1")
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTimeId("time1")
                .regularity(Regularity.EACH_WEEK)
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courseId("course1")
                .teacherId("teacher1")
                .groupName("Group A")
                .groupId("group1")
                .classType("ONLINE")
                .locationId("location1")
                .url("http://example.com")
                .userZone("UTC")
                .build();

        GlobalStudyClassRequest request2 = GlobalStudyClassRequest.builder()
                .id("2")
                .scheduleId("schedule1")
                .scheduleDay(DayOfWeek.WEDNESDAY)
                .scheduleTimeId("time2")
                .regularity(Regularity.EACH_WEEK)
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .courseId("course2")
                .teacherId("teacher2")
                .groupName("Group B")
                .groupId("group2")
                .classType("OFFLINE")
                .locationId("location2")
                .url("http://example.com")
                .userZone("UTC")
                .build();

        List<GlobalStudyClassRequest> requestList = Arrays.asList(request1, request2);

        doNothing().when(globalStudyClassesService).parseScheduleListToGlobalClasses(requestList);

        mockMvc.perform(post("/admin/schedule/edit")
                        .param("id", "schedule1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/schedule"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteSchedule() throws Exception {
        String userId = "1";
        mockMvc.perform(delete("/admin/schedule/delete/{id}", userId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/schedule"));

        verify(scheduleService, times(1)).deleteSchedule(userId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteGlobalClass() throws Exception {
        String id = "1";
        mockMvc.perform(delete("/admin/schedule/class/delete")
                        .param("id", "1")
                        .param("scheduleId", "schedule1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/schedule/edit?id=schedule1"));

        verify(globalStudyClassesService, times(1)).deleteGlobalClass(id);
    }
}
