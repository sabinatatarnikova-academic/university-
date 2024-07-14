package com.foxminded.university.controller;

import com.foxminded.university.config.AdminControllerConfig;
import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.classes.CreateStudyClassRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

@WebMvcTest(AdminController.class)
@Import({TestSecurityConfig.class, AdminControllerConfig.class})
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
    private LocationService locationService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllUsersList() throws Exception {
        Page<UserResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(UserResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        when(userService.findAllUsersWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/users").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/admin_users"))
                .andExpect(model().attributeExists("usersPage"))
                .andExpect(model().attribute("usersPage", pageDtoImpl));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddUserForm() throws Exception {
        mockMvc.perform(get("/admin/users/new"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", new UserResponse()))
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
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
        when(groupService.findGroupById("1")).thenReturn(new Group("1", "Math", new ArrayList<Student>(), new ArrayList<StudyClass>()));

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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        when(courseService.findAllCoursesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/courses").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/course/admin_courses"))
                .andExpect(model().attributeExists("coursesPage"))
                .andExpect(model().attribute("coursesPage", pageDtoImpl));
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

        verify(courseService, times(1)).updateCourse(any());
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        when(studyClassService.findAllClassesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/classes").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/studyClass/admin_class"))
                .andExpect(model().attributeExists("studyClassPage"))
                .andExpect(model().attribute("studyClassPage", pageDtoImpl));
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        when(groupService.findAllGroupsWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/admin/groups").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/group/admin_groups"))
                .andExpect(model().attributeExists("groupsPage"))
                .andExpect(model().attribute("groupsPage", pageDtoImpl));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllStudentsAssignedToGroupList() throws Exception {
        Group group = Group.builder().id("1").name("test").build();
        List<StudentResponse> pageDtoImpl = Collections.singletonList(StudentResponse.builder()
                .id("1")
                .firstName("tEST")
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
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllClassesAssignedToGroupList() throws Exception {
        Group group = Group.builder().id("1").name("test").build();
        List<StudyClassResponse> pageDtoImpl = Collections.singletonList(StudyClassResponse.builder()
                .id("1")
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        Page<StudentResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(StudentResponse.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(GroupFormation.builder().name("Group A").build())
                .build()));
        List<StudyClassResponse> studyClassResponses = new ArrayList<>(Collections.singletonList(StudyClassResponse.builder().build()));
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
}
