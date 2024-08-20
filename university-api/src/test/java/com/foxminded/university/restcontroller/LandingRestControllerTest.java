package com.foxminded.university.restcontroller;

import com.foxminded.university.config.LandingRestControllerConfig;
import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LandingRestController.class)
@ContextConfiguration(classes = {TestConfig.class, LandingRestControllerConfig.class})
class LandingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void testGetWelcomePage() throws Exception {
        Page<CourseDTO> pageDtoImpl = new PageImpl<>(Collections.singletonList(CourseDTO.builder()
                .name("Name")
                .studyClasses(new ArrayList<>())
                .build()));
        RequestPage page = PageUtils.createPage(0, 10);
        when(courseService.findAllCoursesWithPagination(page)).thenReturn(pageDtoImpl);

        mockMvc.perform(get("/api/v1/")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Name"));
    }

    @Test
    void testShowLoginPage_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/login"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Please log in"));
    }

    @Test
    void testShowLoginPage_AlreadyLoggedIn() throws Exception {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/api/v1/login"))
                .andExpect(status().isOk())
                .andExpect(content().string("Already logged in"));
    }
}
