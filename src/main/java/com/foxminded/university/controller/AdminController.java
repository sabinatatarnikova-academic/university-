package com.foxminded.university.controller;

import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.users.User;
import com.foxminded.university.model.users.dtos.StudentDTO;
import com.foxminded.university.model.users.dtos.TeacherDTO;
import com.foxminded.university.model.users.dtos.UserDTO;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.user.UserService;
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

import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private GroupService groupService;
    private StudyClassService studyClassService;
    private ControllerUtils controllerUtils;

    @GetMapping
    public String showAllUsersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        Map<Integer, Integer> validatedParams = controllerUtils.getValidatedPageParameters(pageStr, sizeStr);
        int page = validatedParams.get(0);
        int size = validatedParams.get(1);
        Page<User> usersPage = userService.findAllUsersWithPagination(page, size);
        model.addAttribute("usersPage", usersPage);
        return "admin";
    }

    @GetMapping("/new-user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(0, Integer.MAX_VALUE));
        return "add-user";
    }

    @PostMapping("/new-user")
    public String addUser(@ModelAttribute UserDTO user) {
        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(user.getFirstName());
            studentDTO.setLastName(user.getLastName());
            studentDTO.setGroup(user.getGroup());
            userService.saveStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setFirstName(user.getFirstName());
            teacherDTO.setLastName(user.getLastName());
            userService.saveTeacher(teacherDTO);
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit-user")
    public String showEditUserForm(@RequestParam String id, Model model) {
        User user = userService.findUserById(id);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .username(user.getUsername())
                .password(user.getPassword())
                .rawPassword(user.getRawPassword())
                .build();
        model.addAttribute("user", userDTO);
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(0, Integer.MAX_VALUE));
        model.addAttribute("studyClasses", studyClassService.findAllClassesWithPagination(0, Integer.MAX_VALUE));
        return "edit-user";
    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute UserDTO user) {
        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(user.getId());
            studentDTO.setFirstName(user.getFirstName());
            studentDTO.setLastName(user.getLastName());
            studentDTO.setGroup(user.getGroup());
            studentDTO.setUsername(user.getUsername());
            studentDTO.setPassword(user.getRawPassword());
            userService.updateStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setId(user.getId());
            teacherDTO.setFirstName(user.getFirstName());
            teacherDTO.setLastName(user.getLastName());
            teacherDTO.setStudyClasses(user.getStudyClasses());
            teacherDTO.setUsername(user.getUsername());
            teacherDTO.setPassword(user.getRawPassword());
            for (StudyClass studyClass : user.getStudyClasses()) {
                studyClassService.assignTeacherToClass(user.getId(), studyClass.getId());
            }
            userService.updateTeacher(teacherDTO);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
