package com.foxminded.university.restcontroller;

import com.foxminded.university.config.LandingRestControllerConfig;
import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.service.user.UserTokenService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private UserTokenService tokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

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
    void generateToken_Success() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        User mockUser = User.builder()
                .id("1")
                .build();

        when(userService.findUserByUsername("username")).thenReturn(mockUser);
        when(tokenService.generateToken("1")).thenReturn("mocked-token");

        mockMvc.perform(MockMvcRequestBuilders.post("/api-v1-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"username\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-token"));
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
