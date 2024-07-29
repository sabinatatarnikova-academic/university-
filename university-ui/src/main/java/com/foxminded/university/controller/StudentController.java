package com.foxminded.university.controller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("student")
public class StudentController {

    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping()
    public String showStudentsList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<StudentResponse> studentPage = userService.findAllStudentsWithPagination(page);
        model.addAttribute("studentPage", studentPage);
        return "student/student";
    }

    @GetMapping("/courses")
    public String showAllCoursesList(Model model, @RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        Page<CourseDTO> coursePage = userService.showCoursesThatAssignedToStudent(page);
        model.addAttribute("coursesPage", coursePage);
        return "student/student_courses";
    }

    @GetMapping("/schedule")
    public String showScheduleClasses(@RequestParam(value = "userDate", required = false) LocalDate userDate,
                                      Model model) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }

        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingScheduleThatAssignedToStudent(userDate);

        model.addAttribute("times", data.getTimes());
        model.addAttribute("days", data.getDays());
        model.addAttribute("studyClasses", data.getScheduleByWeek());
        model.addAttribute("groupName", data.getGroupName());
        model.addAttribute("weekStart", data.getWeekStart());
        model.addAttribute("weekEnd", data.getWeekEnd());
        model.addAttribute("userDate", userDate);
        return "student/student_schedule";
    }
}
