package com.foxminded.university.restcontroller;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.classes.CreateStudyClassRequest;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.request.users.UserRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
@Validated
public class AdminRestController {

    private final UserService userService;
    private final GroupService groupService;
    private final StudyClassService studyClassService;
    private final CourseService courseService;
    private final GlobalStudyClassesService globalStudyClassesService;
    private final ScheduleService scheduleService;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
    }

    @GetMapping("/users")
    public Page<UserResponse> showAllUsersList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return userService.findAllUsersWithPagination(page);
    }

    @PostMapping("/users/new")
    public void addUser(@RequestBody UserRequest user) {
        userService.saveUser(user);
    }

    @PutMapping("/users/edit")
    public void editUser(@RequestBody UserFormRequest userFormRequest) {
        userService.updateUser(userFormRequest);
    }

    @DeleteMapping("/users/delete/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/courses")
    public Page<CourseDTO> showAllCoursesList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return courseService.findAllCoursesWithPagination(page);
    }

    @PostMapping("/courses/new")
    public void addCourse(@RequestBody CourseDTO course) {
        courseService.saveCourse(course);
    }

    @PutMapping("/courses/edit")
    public void editCourse(@RequestBody CourseRequest courseRequest) {
        courseService.updateCourse(courseRequest);
    }

    @DeleteMapping("/courses/delete/{id}")
    public void deleteCourse(@PathVariable("id") String id) {
        courseService.deleteCourseById(id);
    }

    @GetMapping("/classes")
    public Page<StudyClassResponse> showAllStudyClassList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return studyClassService.findAllClassesWithPagination(page);
    }

    @GetMapping("/classes/new")
    public CreateStudyClassRequest showAddStudyClassForm() {
        return new CreateStudyClassRequest();
    }

    @PostMapping("/classes/new")
    public void addClass(@RequestBody CreateStudyClassResponse studyClass) {
        studyClassService.saveStudyClass(studyClass);
    }

    @PutMapping("/classes/edit")
    public void editStudyClass(@RequestBody StudyClassRequest studyClass) {
        studyClassService.updateStudyClass(studyClass);
    }

    @DeleteMapping("/classes/delete/{id}")
    public void deleteStudyClass(@PathVariable("id") String id) {
        studyClassService.deleteClassById(id);
    }

    @GetMapping("/groups")
    public Page<GroupFormation> showAllGroupsList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return groupService.findAllGroupsWithPagination(page);
    }

    @DeleteMapping("/groups/students/delete/{id}")
    public void deleteStudentFromGroup(@PathVariable("id") String id) {
        groupService.deleteStudentFromGroupById(id);
    }

    @DeleteMapping("/groups/classes/delete/{id}")
    public void deleteStudyClassFromGroup(@PathVariable("id") String id) {
        groupService.deleteStudyClassFromGroupById(id);
    }

    @PostMapping("/groups/new")
    public void addGroup(@RequestBody GroupFormation groupFormation) {
        groupService.saveGroup(groupFormation);
    }

    @PutMapping("/groups/edit")
    public void editGroup(@RequestBody GroupRequest groupRequest) {
        groupService.updateGroup(groupRequest);
    }

    @DeleteMapping("/groups/delete/{id}")
    public void deleteGroup(@PathVariable("id") String id) {
        groupService.deleteGroupById(id);
    }

    @GetMapping("/schedule")
    public Page<ViewScheduleResponse> showSchedulesList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return scheduleService.findAllSchedulesWithPagination(page);
    }

    @PostMapping("/schedule/new")
    public String saveSchedule(@Valid @RequestBody ScheduleCreateRequest globalStudyClassRequest) {
        return scheduleService.addSchedule(globalStudyClassRequest);
    }

    @GetMapping("/schedule/classes/add")
    public ScheduleClassesResponse showAddScheduleClasses(@RequestParam String id) {
        return scheduleService.getAllRequiredDataForAddingClassesToSchedule(id);
    }

    @PostMapping("/schedule/classes/add")
    public void addSchedule(@RequestBody List<GlobalStudyClassRequest> globalStudyClassRequests) {
        globalStudyClassesService.parseScheduleListToGlobalClasses(globalStudyClassRequests);
    }

    @GetMapping("/schedule/view")
    public ScheduleViewResponse showScheduleClasses(@RequestParam String id,
                                      @RequestParam(value = "userDate", required = false) LocalDate userDate) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }
        return scheduleService.getAllRequiredDataForViewingSchedule(id, userDate);
    }

    @GetMapping("/schedule/edit")
    public ScheduleClassesResponse showEditScheduleClassesForm(@RequestParam String id) {
        return scheduleService.getAllRequiredDataForAddingClassesToSchedule(id);
    }

    @PostMapping("/schedule/edit")
    public void editSchedule(@RequestBody List<GlobalStudyClassRequest> globalStudyClassRequests) {
        addSchedule(globalStudyClassRequests);
    }

    @DeleteMapping("/schedule/delete/{id}")
    public void deleteSchedule(@PathVariable("id") String id) {
        scheduleService.deleteSchedule(id);
    }

    @DeleteMapping("/schedule/class/delete")
    public String deleteGlobalClass(@RequestParam String id, @RequestParam String scheduleId) {
        globalStudyClassesService.deleteGlobalClass(id);
        return scheduleId;
    }
}
