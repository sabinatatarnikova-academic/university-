package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.classes.CreateStudyClassRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final GroupService groupService;
    private final StudyClassService studyClassService;
    private final CourseService courseService;
    private final GlobalStudyClassesService globalStudyClassesService;
    private final ScheduleService scheduleService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>("Resource not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

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
        model.addAttribute("user", userService.findUserDTOById(id));
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
        model.addAttribute("course", courseService.findCourseDTOById(id));
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
        EditStudyClassResponse data = studyClassService.getAllRequiredDataForStudyClassEdit();
        model.addAttribute("class", studyClassService.findClassDTOById(id));
        model.addAttribute("courses", data.getCourses());
        model.addAttribute("groups", data.getGroups());
        model.addAttribute("teachers", data.getTeachers());
        model.addAttribute("locations", data.getLocations());
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

    @GetMapping("/groups")
    public String showAllGroupsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<GroupFormation> groupsPage = groupService.findAllGroupsWithPagination(page);
        model.addAttribute("groupsPage", groupsPage);
        return "admin/group/admin_groups";
    }

    @GetMapping("/groups/students")
    public String showAllStudentsAssignedToGroup(@RequestParam String id, Model model) {
        Group group = groupService.findGroupById(id);
        List<StudentResponse> studentsAssignedToGroup = groupService.findAllStudentsAssignedToGroup(id);
        model.addAttribute("students", studentsAssignedToGroup);
        model.addAttribute("group", group);
        return "admin/group/group_students";
    }

    @DeleteMapping("/groups/students/delete/{id}")
    public String deleteStudentFromGroup(@PathVariable("id") String id) {
        groupService.deleteStudentFromGroupById(id);
        return "redirect:/admin/groups";
    }

    @GetMapping("/groups/classes")
    public String showAllClassesAssignedToGroup(@RequestParam String id, Model model) {
        Group group = groupService.findGroupById(id);
        List<StudyClassResponse> classesAssignedToGroup = groupService.findAllStudyClassesAssignedToGroup(id);
        model.addAttribute("classes", classesAssignedToGroup);
        model.addAttribute("group", group);
        return "admin/group/group_classes";
    }

    @DeleteMapping("/groups/classes/delete/{id}")
    public String deleteStudyClassFromGroup(@PathVariable("id") String id) {
        groupService.deleteStudyClassFromGroupById(id);
        return "redirect:/admin/groups";
    }

    @GetMapping("/groups/new")
    public String showAddGroupForm(Model model) {
        model.addAttribute("group", new GroupFormation());
        return "admin/group/add_group";
    }

    @PostMapping("/groups/new")
    public String addGroup(@ModelAttribute GroupFormation groupFormation) {
        groupService.saveGroup(groupFormation);
        return "redirect:/admin/groups";
    }

    @GetMapping("/groups/edit")
    public String showEditGroupForm(@RequestParam String id, Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        GroupEditResponse data = studyClassService.getAllRequiredDataForGroupEdit(id, page);
        model.addAttribute("group", data.getGroup());
        model.addAttribute("students", data.getStudents());
        model.addAttribute("studyClasses", data.getStudyClasses());
        return "admin/group/edit_group";
    }

    @PostMapping("/groups/edit")
    public String editGroup(@ModelAttribute GroupRequest groupRequest) {
        groupService.updateGroup(groupRequest);
        return "redirect:/admin/groups";
    }

    @DeleteMapping("/groups/delete/{id}")
    public String deleteGroup(@PathVariable("id") String id) {
        groupService.deleteGroupById(id);
        return "redirect:/admin/groups";
    }

    @GetMapping("/schedule")
    public String showSchedulesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<ViewScheduleResponse> schedules = scheduleService.findAllSchedulesWithPagination(page);
        model.addAttribute("schedulesPage", schedules);
        return "admin/schedule/schedules_list";
    }

    @GetMapping("/schedule/new")
    public String showAddSchedule(Model model) {
        model.addAttribute("schedule", new ScheduleCreateRequest());
        model.addAttribute("groups", groupService.findAllGroupsWithoutSchedule());
        return "admin/schedule/schedule_add";
    }

    @PostMapping("/schedule/new")
    public String addSchedule(@ModelAttribute ScheduleCreateRequest globalStudyClassRequest) {
        String id = scheduleService.addSchedule(globalStudyClassRequest);
        return "redirect:/admin/schedule/classes/add?id=" + id;
    }

    @GetMapping("/schedule/classes/add")
    public String showAddScheduleClasses(@RequestParam String id, Model model) {
        ScheduleClassesResponse data = scheduleService.getAllRequiredDataForAddingClassesToSchedule(id);

        model.addAttribute("times", data.getTimes());
        model.addAttribute("days", data.getDays());
        model.addAttribute("globalClass", new GlobalStudyClassRequest());
        model.addAttribute("scheduleId", data.getScheduleId());
        model.addAttribute("groupId", data.getGroupId());
        model.addAttribute("groupName", data.getGroupName());
        model.addAttribute("startDate", data.getStartDate());
        model.addAttribute("endDate", data.getEndDate());
        model.addAttribute("courses", data.getCourses());
        model.addAttribute("teachers", data.getTeachers());
        model.addAttribute("locations", data.getLocations());
        return "admin/schedule/schedule_add_classes";
    }

    @PostMapping("/schedule/classes/add")
    public String addSchedule(@RequestParam String id, @RequestBody List<GlobalStudyClassRequest> globalStudyClassRequests) {
        globalStudyClassesService.parseScheduleListToGlobalClasses(globalStudyClassRequests);
        return "redirect:/admin/schedule";
    }

    @GetMapping("/schedule/view")
    public String showScheduleClasses(@RequestParam String id,
                                      @RequestParam(value = "userDate", required = false) LocalDate userDate,
                                      Model model) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }

        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingSchedule(id, userDate);

        model.addAttribute("times", data.getTimes());
        model.addAttribute("days", data.getDays());
        model.addAttribute("studyClasses", data.getScheduleByWeek());
        model.addAttribute("groupName", data.getGroupName());
        model.addAttribute("weekStart", data.getWeekStart());
        model.addAttribute("weekEnd", data.getWeekEnd());
        model.addAttribute("userDate", userDate);
        model.addAttribute("scheduleId", id);
        return "admin/schedule/schedule_view";
    }

    @GetMapping("/schedule/edit")
    public String showEditScheduleClassesForm(@RequestParam String id, Model model) {
        ScheduleClassesResponse data = scheduleService.getAllRequiredDataForAddingClassesToSchedule(id);

        model.addAttribute("times", data.getTimes());
        model.addAttribute("days", data.getDays());
        model.addAttribute("globalClasses", data.getGlobalStudyClasses());
        model.addAttribute("globalClass", new GlobalStudyClassRequest());
        model.addAttribute("scheduleId", data.getScheduleId());
        model.addAttribute("groupId", data.getGroupId());
        model.addAttribute("groupName", data.getGroupName());
        model.addAttribute("startDate", data.getStartDate());
        model.addAttribute("endDate", data.getEndDate());
        model.addAttribute("courses", data.getCourses());
        model.addAttribute("teachers", data.getTeachers());
        model.addAttribute("locations", data.getLocations());
        return "admin/schedule/schedule_edit";
    }

    @PostMapping("/schedule/edit")
    public String editSchedule(@RequestParam String id, @RequestBody List<GlobalStudyClassRequest> globalStudyClassRequests) {
        globalStudyClassesService.parseScheduleListToGlobalClasses(globalStudyClassRequests);
        return "redirect:/admin/schedule";
    }

    @DeleteMapping("/schedule/delete/{id}")
    public String deleteSchedule(@PathVariable("id") String id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/admin/schedule";
    }

    @DeleteMapping("/schedule/class/delete")
    public String deleteGlobalClass(@RequestParam String id, @RequestParam String scheduleId) {
        globalStudyClassesService.deleteGlobalClass(id);
        return "redirect:/admin/schedule/edit?id=" + scheduleId;
    }
}
