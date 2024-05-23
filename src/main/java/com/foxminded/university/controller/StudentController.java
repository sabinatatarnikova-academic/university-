package com.foxminded.university.controller;

import com.foxminded.university.utils.DefaultPage;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.model.entity.users.Student;
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
@RequestMapping("/student")
public class StudentController {

    private UserService userService;
    private PageUtils pageUtils;

    @GetMapping()
    public String showStudentsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        DefaultPage validatedParams = pageUtils.getValidatedPageParameters(pageStr, sizeStr);
        int page = validatedParams.getPageNumber();
        int size = validatedParams.getPageSize();
        Page<Student> studentPage = userService.findAllStudentsWithPagination(page, size);
        model.addAttribute("studentPage", studentPage);
        return "student";
    }
}
