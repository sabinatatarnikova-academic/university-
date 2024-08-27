package com.foxminded.university.restcontroller;

import com.foxminded.university.model.dtos.request.LoginRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/api-v1-token")
    public ResponseEntity<String> generateToken(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (authenticate.isAuthenticated()) {
                User user = userService.findUserByUsername(loginRequest.getUsername());
                return ResponseEntity.ok().body(tokenService.generateToken(user.getId()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
