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

    @GetMapping("/")
    public String getWelcomePage(){
        return "index";
    }

    @GetMapping("/student")
    public String showStudentsList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Student> studentPage = userService.findAllStudentsWithPagination(page, size);
        model.addAttribute("studentPage", studentPage);
        return "student";
    }

    @GetMapping("/teacher")
    public String showTeachersList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Teacher> teacherPage = userService.findAllTeachersWithPagination(page, size);
        model.addAttribute("teacherPage", teacherPage);
        return "teacher";
    }
}
