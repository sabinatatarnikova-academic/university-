package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("teacher")
public class TeacherController {

    private final UserService userService;
    private final CourseService courseService;

    @GetMapping()
    public String showTeachersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<TeacherResponse> teacherPage = userService.findAllTeachersWithPagination(page);
        model.addAttribute("teacherPage", teacherPage);
        return "teacher/teacher";
    }

    @GetMapping("/courses")
    public String showAllCoursesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<CourseDTO> coursePage = courseService.findAllCoursesWithPagination(page);
        model.addAttribute("coursesPage", coursePage);
        return "teacher/teacher_courses";
    }
}
