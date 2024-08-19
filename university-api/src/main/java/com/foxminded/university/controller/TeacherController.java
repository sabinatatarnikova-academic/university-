package com.foxminded.university.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("teacher")
public class TeacherController {

    private final UserService userService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final StudyClassService studyClassService;
    private final ScheduleService scheduleService;

    @GetMapping()
    public String showTeachersList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<TeacherResponse> teacherPage = userService.findAllTeachersWithPagination(page);
        model.addAttribute("teacherPage", teacherPage);
        return "teacher/teacher";
    }

    @GetMapping("/courses")
    public String showAllCoursesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<CourseDTO> coursePage = courseService.findAllCoursesWithPagination(page);
        model.addAttribute("coursesPage", coursePage);
        return "teacher/teacher_courses";
    }

    @GetMapping("/groups")
    public String showAllGroupsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<GroupAssignResponse> groups = groupService.findAllGroupsResponsesWithPagination(page);
        model.addAttribute("groupsPage", groups);
        return "teacher/teacher_groups";
    }

    @DeleteMapping("/classes/delete/{id}")
    public String deleteStudentFromGroup(@PathVariable("id") String id) {
        studyClassService.deleteTeacherFromStudyClass(id);
        return "redirect:/teacher";
    }

    @GetMapping("/classes/add")
    public String showStudyClasses(@RequestParam String id, Model model) {
        model.addAttribute("teacher", userService.findUserDTOById(id));
        model.addAttribute("allStudyClasses", studyClassService.findAllClasses());
        return "teacher/teacher_add-classes";
    }

    @PostMapping("/classes/add")
    public String addStudyClassToTeacher(@ModelAttribute TeacherClassUpdateRequest teacherClassUpdateRequest) {
        userService.updateTeacherWithStudyClasses(teacherClassUpdateRequest);
        return "redirect:/teacher";
    }

    @GetMapping("/courses/edit")
    public String showEditCourseForm(@RequestParam String id, Model model) {
        model.addAttribute("course", courseService.findCourseDTOById(id));
        model.addAttribute("allStudyClasses", studyClassService.findAllClasses());
        return "teacher/teacher_courses-edit";
    }

    @PostMapping("/courses/edit")
    public String editCourse(@ModelAttribute CourseTeacherRequest courseRequest) {
        courseService.updateCourse(courseRequest);
        return "redirect:/teacher/courses";
    }

    @GetMapping("/groups/edit")
    public String showEditGroupForm(@RequestParam String id, Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        GroupEditResponse data = studyClassService.getAllRequiredDataForGroupEdit(id, page);
        model.addAttribute("group", data.getGroup());
        model.addAttribute("students", data.getStudents());
        model.addAttribute("studyClasses", data.getStudyClasses());
        return "teacher/teacher_groups-edit";
    }

    @PostMapping("/groups/edit")
    public String editGroup(@ModelAttribute GroupRequest groupRequest) {
        groupService.updateGroup(groupRequest);
        return "redirect:/teacher/groups";
    }

    @GetMapping("/schedule")
    public String showScheduleClasses(@RequestParam String id,
                                      @RequestParam(value = "userDate", required = false) LocalDate userDate,
                                      Model model) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }

        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(id, userDate);

        model.addAttribute("times", data.getTimes());
        model.addAttribute("days", data.getDays());
        model.addAttribute("studyClasses", data.getScheduleByWeek());
        model.addAttribute("weekStart", data.getWeekStart());
        model.addAttribute("weekEnd", data.getWeekEnd());
        model.addAttribute("userDate", userDate);
        return "teacher/teacher_schedule";
    }

}
