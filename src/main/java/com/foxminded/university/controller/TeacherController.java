package com.foxminded.university.controller;

import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.service.user.UserService;
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

    private UserService userService;
    private ControllerUtils controllerUtils;

    @GetMapping()
    public String showTeachersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        int page = controllerUtils.getValidatedPageParameters(pageStr, sizeStr).get(0);
        int size = controllerUtils.getValidatedPageParameters(pageStr, sizeStr).get(1);
        Page<Teacher> teacherPage = userService.findAllTeachersWithPagination(page, size);
        model.addAttribute("teacherPage", teacherPage);
        return "teacher";
    }
}
