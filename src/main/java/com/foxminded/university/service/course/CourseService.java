package com.foxminded.university.service.course;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.classes.StudyClass;

import java.util.List;

public interface CourseService {
    void saveCourse(String courseName, List<StudyClass> classes);

    Course findCourseById(String courseId);

    Course findCourseByName(String courseName);

    void updateCourse(String courseId, String courseName, List<StudyClass> classes);

    void deleteCourseById(String courseId);

    List<Course> findAllCoursesWithPagination(int pageNumber, int pageSize);
}
