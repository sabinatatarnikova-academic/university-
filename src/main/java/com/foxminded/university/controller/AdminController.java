/*
package com.foxminded.university.controller;

import com.foxminded.university.utils.DefaultPage;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
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

@Controller
@AllArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {

    private UserService userService;
    private GroupService groupService;
    private StudyClassService studyClassService;
    private PageUtils pageUtils;

    @GetMapping
    public String showAllUsersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        DefaultPage validatedParams = pageUtils.getValidatedPageParameters(pageStr, sizeStr);
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
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName(user.getFirstName());
            studentDTO.setLastName(user.getLastName());
            studentDTO.setGroupId(user.getGroupId());
            userService.saveStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setFirstName(user.getFirstName());
            teacherDTO.setLastName(user.getLastName());
            userService.saveTeacher(teacherDTO);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam String id, Model model) {
        User user = userService.findUserById(id);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .username(user.getUsername())
                .password(user.getPassword())
                .repeatedPassword(user.getRepeatedPassword())
                .build();
        model.addAttribute("user", userDTO);
        model.addAttribute("groups", groupService.findAllGroupsWithPagination(0, Integer.MAX_VALUE));
        model.addAttribute("studyClasses", studyClassService.findAllClassesWithPagination(0, Integer.MAX_VALUE));
        return "edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute UserDTO user) {
        if (user.getUserType().equalsIgnoreCase("STUDENT")) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(user.getId());
            studentDTO.setFirstName(user.getFirstName());
            studentDTO.setLastName(user.getLastName());
            studentDTO.setGroupId(user.getGroupId());
            studentDTO.setUsername(user.getUsername());
            studentDTO.setPassword(user.getRepeatedPassword());
            userService.updateStudent(studentDTO);
        } else if (user.getUserType().equalsIgnoreCase("TEACHER")) {
            TeacherDTO teacherDTO = new TeacherDTO();
            teacherDTO.setId(user.getId());
            teacherDTO.setFirstName(user.getFirstName());
            teacherDTO.setLastName(user.getLastName());
            teacherDTO.setUsername(user.getUsername());
            teacherDTO.setPassword(user.getRepeatedPassword());
            teacherDTO.setStudyClassesIds(user.getStudyClassesIds());
            user.getStudyClassesIds().forEach(string -> {
                studyClassService.assignTeacherToClass(user.getId(), string);
            });
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
*/
