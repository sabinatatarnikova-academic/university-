package com.foxminded.university.controller;

import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class DefaultController {

    private UserService userService;
    private Utils utils;

    @GetMapping("/")
    public String getWelcomePage(){
        return "index";
    }

    @GetMapping("/student")
    public String showStudentsList(Model model, @RequestParam(defaultValue = "0") String pageStr, @RequestParam(defaultValue = "10") String sizeStr) {
        Page<Student> studentPage = userService.findAllStudentsWithPagination(utils.getResult(pageStr, sizeStr).get(0), utils.getResult(pageStr,sizeStr).get(1));
        model.addAttribute("studentPage", studentPage);
        return "student";
    }

    @GetMapping("/teacher")
    public String showTeachersList(Model model, @RequestParam(defaultValue = "0") String pageStr, @RequestParam(defaultValue = "10") String sizeStr) {
        Page<Teacher> teacherPage = userService.findAllTeachersWithPagination(utils.getResult(pageStr, sizeStr).get(0), utils.getResult(pageStr,sizeStr).get(1));
        model.addAttribute("teacherPage", teacherPage);
        return "teacher";
    }
}
