package com.foxminded.university.controller;

import com.foxminded.university.config.TestControllerConfig;
import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeacherController.class)
@Import({TestSecurityConfig.class, TestControllerConfig.class})
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

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
