package com.foxminded.university.service.course;

import com.foxminded.university.model.entity.Course;
import com.foxminded.university.utils.RequestPage;

import java.util.List;

public interface CourseService {

    void saveCourse(Course course);

    Course findCourseById(String courseId);

    Course findCourseByName(String courseName);

    void updateCourse(Course course);

    void deleteCourseById(String courseId);

    List<Course> findAllCoursesWithPagination(RequestPage pageRequest);
}
