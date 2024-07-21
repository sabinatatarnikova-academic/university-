package com.foxminded.university.controller;

import com.foxminded.university.config.TestSecurityConfig;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
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
@Import(TestSecurityConfig.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testShowStudentList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<StudentResponse> pageDtoImpl = new PageImpl<>(Collections.singletonList(StudentResponse.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(GroupFormation.builder().name("Group A").build())
                .build()));

        when(userService.findAllStudentsWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/student").param("pageDtoImpl", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student"))
                .andExpect(model().attributeExists("studentPage"))
                .andExpect(model().attribute("studentPage", pageDtoImpl));
    }

    @Test
    void testShowAllCoursesList() throws Exception {
        RequestPage page = PageUtils.createPage(0, 10);
        Page<CourseDTO> coursePage = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Group A")
                .build()));

        when(userService.showCoursesThatAssignedToStudent(page)).thenReturn(coursePage);

        mockMvc.perform(get("/student/courses").param("coursePage", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/student_courses"));
    }
}
