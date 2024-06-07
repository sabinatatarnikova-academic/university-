package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {

    private UserService userService;
    private GroupService groupService;
    private StudyClassService studyClassService;
    private TeacherMapper teacherMapper;
    private StudentMapper studentMapper;

    @GetMapping
    public String showAllUsersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage validatedParams = PageUtils.createPage(pageStr, sizeStr);
        Page<User> usersPage = userService.findAllUsersWithPagination(validatedParams);
        model.addAttribute("usersPage", usersPage);
        return "admin";
    }

    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(Integer.MAX_VALUE));
        model.addAttribute("user", new UserDTO());
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(validatedParams));
        return "add-user";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute UserDTO user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam String id, Model model) {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(Integer.MAX_VALUE));
        User user = userService.findUserById(id);
        model.addAttribute("user", user.getUserType().equals("TEACHER") ? teacherMapper.toDto((Teacher) user) : studentMapper.toDto((Student) user));
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(validatedParams));
        model.addAttribute("allStudyClasses", studyClassService.findAllClassesWithPagination(validatedParams));
        return "edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserFormDTO userFormDTO) {
        if (userFormDTO.getUserType().equalsIgnoreCase("STUDENT")) {
            userService.updateStudent(userFormDTO);
        } else if (userFormDTO.getUserType().equalsIgnoreCase("TEACHER")) {
            userService.updateTeacher(userFormDTO);
        }
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
