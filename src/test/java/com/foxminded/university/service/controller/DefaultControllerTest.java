package com.foxminded.university.service.controller;

import com.foxminded.university.controller.DefaultController;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DefaultController.class)
class DefaultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetWelcomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testShowStudentList() throws Exception {
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        Page<Student> page = new PageImpl<>(Collections.singletonList(Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .build()));
        when(userService.findAllStudentsWithPagination(0, 10)).thenReturn(page);

        mockMvc.perform(get("/student").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("student"))
                .andExpect(model().attributeExists("studentPage"))
                .andExpect(model().attribute("studentPage", page));
    }

    @Test
    void testShowTeacherList() throws Exception {
        Page<Teacher> page = new PageImpl<>(Collections.singletonList(Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build()));
        when(userService.findAllTeachersWithPagination(0, 10)).thenReturn(page);

        mockMvc.perform(get("/teacher").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher"))
                .andExpect(model().attributeExists("teacherPage"))
                .andExpect(model().attribute("teacherPage", page));
    }
}
