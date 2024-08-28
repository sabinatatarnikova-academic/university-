package com.foxminded.university.restcontroller;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentRestController {

    private final UserService userService;
    private final ScheduleService scheduleService;

    @GetMapping()
    public Page<StudentResponse> getStudentsList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return userService.findAllStudentsWithPagination(page);
    }

    @GetMapping("/courses")
    public Page<CourseDTO> getAllCoursesList(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "10") String sizeStr) {
        RequestPage page = PageUtils.createPage(pageStr, sizeStr);
        return userService.showCoursesThatAssignedToStudent(page);
    }

    @GetMapping("/schedule")
    public ScheduleViewResponse getScheduleClasses(@RequestParam(value = "userDate", required = false) LocalDate userDate) {
        if (userDate == null) {
            userDate = LocalDate.now();
        }
        return scheduleService.getAllRequiredDataForViewingScheduleThatAssignedToStudent(userDate);
    }
}
