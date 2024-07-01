package com.foxminded.university.service.course;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    void saveCourse(CourseDTO course);

    Course findCourseById(String courseId);

    Course findCourseByName(String courseName);

    void updateCourse(CourseRequest course);

    void deleteCourseById(String courseId);

    Page<CourseDTO> findAllCoursesWithPagination(RequestPage pageRequest);

    List<CourseDTO> findAllCourses();
}
