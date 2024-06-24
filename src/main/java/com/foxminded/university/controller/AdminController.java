package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.classes.CreateStudyClassRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
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

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final GroupService groupService;
    private final StudyClassService studyClassService;
    private final StudyClassMapper studyClassMapper;
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final LocationService locationService;

    @GetMapping()
    public String usersCoursesDecision() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String showAllUsersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<UserResponse> usersPage = userService.findAllUsersWithPagination(page);
        model.addAttribute("usersPage", usersPage);
        return "admin/user/admin_users";
    }

    @GetMapping("/users/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserResponse());
        return "admin/user/add-user";
    }

    @PostMapping("/users/new")
    public String addUser(@ModelAttribute UserResponse user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit")
    public String showEditUserForm(@RequestParam String id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", userMapper.toDto(user));
        model.addAttribute("groups", groupService.findAllGroups());
        model.addAttribute("allStudyClasses", studyClassService.findAllClasses());
        return "admin/user/edit-user";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute UserFormRequest userFormRequest) {
        userService.updateUser(userFormRequest);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/courses")
    public String showAllCoursesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<CourseDTO> coursesPage = courseService.findAllCoursesWithPagination(page);
        model.addAttribute("coursesPage", coursesPage);
        return "admin/course/admin_courses";
    }

    @GetMapping("/courses/new")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new CourseDTO());
        return "admin/course/add_course";
    }

    @PostMapping("/courses/new")
    public String addCourse(@ModelAttribute CourseDTO course) {
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit")
    public String showEditCourseForm(@RequestParam String id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", courseMapper.toDto(course));
        model.addAttribute("allStudyClasses", studyClassService.findAllClasses());
        return "admin/course/edit_course";
    }

    @PostMapping("/courses/edit")
    public String editCourse(@ModelAttribute CourseRequest courseRequest) {
        courseService.updateCourse(courseRequest);
        return "redirect:/admin/courses";
    }

    @DeleteMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") String id) {
        courseService.deleteCourseById(id);
        return "redirect:/admin/courses";
    }

    @GetMapping("/classes")
    public String showAllStudyClassList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<StudyClassResponse> studyClassPage = studyClassService.findAllClassesWithPagination(page);
        model.addAttribute("studyClassPage", studyClassPage);
        return "admin/studyClass/admin_class";
    }

    @GetMapping("/classes/new")
    public String showAddStudyClassForm(Model model) {
        model.addAttribute("class", new CreateStudyClassRequest());
        return "admin/studyClass/add_class";
    }

    @PostMapping("/classes/new")
    public String addClass(@ModelAttribute CreateStudyClassResponse studyClass) {
        studyClassService.saveStudyClass(studyClass);
        return "redirect:/admin/classes";
    }

    @GetMapping("/classes/edit")
    public String showEditStudyClassForm(@RequestParam String id, Model model) {
        StudyClass studyClass = studyClassService.findClassById(id);
        model.addAttribute("class", studyClassMapper.toDto(studyClass));
        model.addAttribute("courses", courseService.findAllCourses());
        model.addAttribute("groups", groupService.findAllGroups());
        model.addAttribute("teachers", userService.findAllTeachers());
        model.addAttribute("locations", locationService.findAllLocations());
        return "admin/studyClass/edit_class";
    }

    @PostMapping("/classes/edit")
    public String editStudyClass(@ModelAttribute StudyClassRequest studyClass) {
        studyClassService.updateStudyClass(studyClass);
        return "redirect:/admin/classes";
    }

    @DeleteMapping("/classes/delete/{id}")
    public String deleteStudyClass(@PathVariable("id") String id) {
        studyClassService.deleteClassById(id);
        return "redirect:/admin/classes";
    }
}
