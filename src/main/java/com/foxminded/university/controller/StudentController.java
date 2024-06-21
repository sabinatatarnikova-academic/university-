package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("student")
public class StudentController {

    private UserService userService;

    @GetMapping()
    public String showStudentsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<StudentResponse> studentPage = userService.findAllStudentsWithPagination(page);
        model.addAttribute("studentPage", studentPage);
        return "student/student";
    }

    @GetMapping("/courses")
    public String showAllCoursesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<CourseDTO> coursePage = userService.showCoursesThatAssignedToStudent(username, page);
        model.addAttribute("coursesPage", coursePage);
        return "student/student_courses";
    }
}
