package com.foxminded.university.controller;

import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.service.course.CourseService;
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

@WebMvcTest(TeacherController.class)
@Import(TestSecurityConfig.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CourseService courseService;

    @Test
    void testShowTeacherList() throws Exception {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
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
    }

    @Test
    void testShowAllCoursesList() throws Exception {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(10));
        Page<CourseDTO> coursePage = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Group A")
                .build()));

        when(courseService.findAllCoursesWithPagination(page)).thenReturn(coursePage);

        mockMvc.perform(get("/teacher/courses").param("coursePage", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher/teacher_courses"))
                .andExpect(model().attributeExists("coursesPage"))
                .andExpect(model().attribute("coursesPage", coursePage));
    }
}
