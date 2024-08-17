package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@AllArgsConstructor
public class LandingController {

    private final CourseService courseService;

    @GetMapping("/api/")
    public Page<CourseDTO> getWelcomePage(@RequestParam(value = "page", defaultValue = "0") String pageStr,
                                          @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return courseService.findAllCoursesWithPagination(page);
    }

    @GetMapping("/api/login")
    public ResponseEntity<String> showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in");
        }
        return ResponseEntity.ok("Already logged in");
    }
}
