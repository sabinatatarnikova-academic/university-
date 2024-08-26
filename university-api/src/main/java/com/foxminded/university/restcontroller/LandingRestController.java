package com.foxminded.university.restcontroller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.service.user.UserTokenService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LandingRestController {

    private final CourseService courseService;
    private final UserTokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/api/v1/")
    public Page<CourseDTO> getWelcomePage(@RequestParam(value = "page", defaultValue = "0") String pageStr,
                                          @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return courseService.findAllCoursesWithPagination(page);
    }

    @GetMapping("/api-token")
    public ResponseEntity<String> generateToken(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (authenticate.isAuthenticated()) {
                User user = userService.findUserByUsername(username);
                return ResponseEntity.ok().body(tokenService.generateToken(user.getId()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
