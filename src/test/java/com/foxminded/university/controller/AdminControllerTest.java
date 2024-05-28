/*
package com.foxminded.university.controller;

import com.foxminded.university.config.AdminControllerConfig;
import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.DefaultPage;
import com.foxminded.university.utils.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

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
    private PageUtils pageUtils;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAllUsersList() throws Exception {
        Page<User> page = new PageImpl<>(Collections.singletonList(User.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        when(pageUtils.getValidatedPageParameters("0", "10")).thenReturn(DefaultPage.builder().pageNumber(0).pageSize(10).build());
        when(userService.findAllUsersWithPagination(0, 10)).thenReturn(page);

        mockMvc.perform(get("/admin/users").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("usersPage"))
                .andExpect(model().attribute("usersPage", page));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowAddUserForm() throws Exception {
        mockMvc.perform(get("/admin/users/new"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", new UserDTO()))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groupService.findAllGroupsWithPagination(0, 10)))
                .andExpect(status().isOk())
                .andExpect(view().name("add-user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddStudent() throws Exception {
        when(groupService.findGroupById("1")).thenReturn(new Group("1", "Math", new ArrayList<Student>(), new ArrayList<StudyClass>()));

        mockMvc.perform(post("/admin/users/new")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("userType", "STUDENT")
                        .param("groupId", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).saveStudent(any(StudentDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddTeacher() throws Exception {
        mockMvc.perform(post("/admin/users/new")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("userType", "TEACHER")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/users"));

        verify(userService, times(1)).saveTeacher(any(TeacherDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowEditUserForm() throws Exception {
        String userId = "1";
        User user = User.builder()
                .id(userId)
                .firstName("Bob")
                .lastName("Johnson")
                .userType("Student")
                .username("bob.johnson")
                .password("password")
                .repeatedPassword("password")
                .build();

        when(userService.findUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/admin/users/edit").param("id", userId))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", UserDTO.builder()
                        .id(userId)
                        .firstName("Bob")
                        .lastName("Johnson")
                        .userType("Student")
                        .username("bob.johnson")
                        .password("password")
                        .repeatedPassword("password")
                        .build()))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", groupService.findAllGroupsWithPagination(0, 10)))
                .andExpect(model().attributeExists("studyClasses"))
                .andExpect(model().attribute("studyClasses", studyClassService.findAllClassesWithPagination(0, 10)))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-user"));
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

        verify(userService, times(1)).updateStudent(any(StudentDTO.class));
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

        verify(userService, times(1)).updateTeacher(any(TeacherDTO.class));
        verify(studyClassService, times(1)).assignTeacherToClass("2", "1");
        verify(studyClassService, times(1)).assignTeacherToClass("2", "2");
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
}
*/
