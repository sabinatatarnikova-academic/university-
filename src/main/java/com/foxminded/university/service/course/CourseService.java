package com.foxminded.university.service.course;

import com.foxminded.university.model.Course;

import java.util.List;

public interface CourseService {

    void saveCourse(Course course);

    Course findCourseById(String courseId);

    Course findCourseByName(String courseName);

    void updateCourse(Course course);

    void deleteCourseById(String courseId);

    List<Course> findAllCoursesWithPagination(int pageNumber, int pageSize);
}
