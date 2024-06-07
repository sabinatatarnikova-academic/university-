package com.foxminded.university.controller;

import com.foxminded.university.config.TestControllerConfig;
import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.users.Student;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StudentController.class)
@Import({TestSecurityConfig.class, TestControllerConfig.class})
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testShowStudentList() throws Exception {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        Page<Student> page = new PageImpl<>(Collections.singletonList(Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .build()));
        when(userService.findAllStudentsWithPagination(validatedParams)).thenReturn(page);

        mockMvc.perform(get("/student").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("student"))
                .andExpect(model().attributeExists("studentPage"))
                .andExpect(model().attribute("studentPage", page));
    }
}
