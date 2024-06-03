package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import com.foxminded.university.utils.mappers.users.UserMapper;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {

    private UserService userService;
    private GroupService groupService;
    private StudyClassService studyClassService;
    private PageUtils pageUtils;
    private StudyClassMapper studyClassMapper;
    private TeacherMapper teacherMapper;
    private StudentMapper studentMapper;
    private UserMapper userMapper;

    @GetMapping
    public String showAllUsersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage validatedParams = pageUtils.getValidatedPageParameters(pageStr, sizeStr);
        int page = validatedParams.getPageNumber();
        int size = validatedParams.getPageSize();
        Page<User> usersPage = userService.findAllUsersWithPagination(page, size);
        model.addAttribute("usersPage", usersPage);
        return "admin";
    }

    @GetMapping("/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(0, Integer.MAX_VALUE));
        return "add-user";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute UserDTO user) {
        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
            StudentDTO studentDTO = studentMapper.toDto(user);
            userService.saveStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            TeacherDTO teacherDTO = teacherMapper.toDto(user);
            userService.saveTeacher(teacherDTO);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam String id, Model model) {
        User user = userService.findUserById(id);
        UserDTO userDTO = userMapper.toDto(user);
        model.addAttribute("user", userDTO);
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(0, Integer.MAX_VALUE));
        model.addAttribute("allStudyClasses", studyClassService.findAllClassesWithPagination(0, Integer.MAX_VALUE));
        return "edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserDTO user) {
        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
           StudentDTO studentDTO = studentMapper.toDto(user);
            userService.updateStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            List<StudyClassDTO> studyClassDTOs = user.getStudyClassesIds().stream()
                    .map(id -> studyClassMapper.toDto(studyClassService.findClassById(id)))
                    .collect(Collectors.toList());
            TeacherDTO teacherDTO = teacherMapper.toDto(user, studyClassDTOs);
            userService.updateTeacher(teacherDTO);
        }
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
