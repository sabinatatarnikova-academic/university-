package com.foxminded.university.restcontroller;

import com.foxminded.university.model.dtos.request.CourseTeacherRequest;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.users.TeacherClassUpdateRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherRestController {

    private final UserService userService;
    private final GroupService groupService;
    private final StudyClassService studyClassService;
    private final CourseService courseService;
    private final ScheduleService scheduleService;

    @GetMapping()
    public Page<TeacherResponse> getTeachersList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return userService.findAllTeachersWithPagination(page);
    }

    @GetMapping("/courses")
    public Page<CourseDTO> getAllCoursesList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return courseService.findAllCoursesWithPagination(page);
    }

    @GetMapping("/groups")
    public Page<GroupAssignResponse> getAllGroupsList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return groupService.findAllGroupsResponsesWithPagination(page);
    }

    @DeleteMapping("/classes/delete/{id}")
    public void deleteStudentFromGroup(@PathVariable("id") String id) {
        studyClassService.deleteTeacherFromStudyClass(id);
    }

    @PostMapping("/classes/add")
    public void addStudyClassToTeacher(@RequestBody TeacherClassUpdateRequest teacherClassUpdateRequest) {
        userService.updateTeacherWithStudyClasses(teacherClassUpdateRequest);
    }

    @PostMapping("/courses/edit")
    public void editCourse(@RequestBody CourseTeacherRequest courseRequest) {
        courseService.updateCourse(courseRequest);
    }

    @GetMapping("/groups/edit")
    public GroupEditResponse showEditGroupForm(@RequestParam String id, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return studyClassService.getAllRequiredDataForGroupEdit(id, page);
    }

    @PostMapping("/groups/edit")
    public void editGroup(@RequestBody GroupRequest groupRequest) {
        groupService.updateGroup(groupRequest);
    }

    @GetMapping("/schedule")
    public ScheduleViewResponse showScheduleClasses(@RequestParam String id,
                                                    @RequestParam(value = "userDate", required = false) LocalDate userDate) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }
        return scheduleService.getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(id, userDate);
    }
}
