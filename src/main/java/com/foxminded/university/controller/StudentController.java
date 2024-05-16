package com.foxminded.university.controller;

import com.foxminded.university.model.users.Student;
import com.foxminded.university.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private UserService userService;
    private ControllerUtils controllerUtils;

    @GetMapping()
    public String showStudentsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        Map<Integer, Integer> validatedPageParameters = controllerUtils.getValidatedPageParameters(pageStr, sizeStr);
        int page = validatedPageParameters.get(0);
        int size = validatedPageParameters.get(1);
        Page<Student> studentPage = userService.findAllStudentsWithPagination(page, size);
        model.addAttribute("studentPage", studentPage);
        return "student";
    }
}
