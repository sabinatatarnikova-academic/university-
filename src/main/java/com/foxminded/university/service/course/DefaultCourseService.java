package com.foxminded.university.service.course;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultCourseService implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public void saveCourse(String courseName, List<StudyClass> classes) {
        log.info("Adding new course: course name - {}, classes - {}", courseName, classes);
        Course course = new Course(courseName, classes);
        courseRepository.save(course);
        log.info("Saved course with name - {}, classes - {}", courseName, classes);
    }

    @Override
    public Course findCourseById(String courseId) {
        log.info("Searching for course with id {}", courseId);
        Course course = courseRepository.findById(courseId).get();
        log.info("Founded the course with id {}", courseId);
        return course;
    }

    @Override
    public Course findCourseByName(String courseName) {
        log.info("Searching for course with name {}", courseName);
        Course course = courseRepository.findCourseByName(courseName).get();
        log.info("Founded the course with id {}", courseName);
        return course;
    }

    @Override
    public void updateCourse(String courseId, String courseName, List<StudyClass> classes) {
        log.info("Updating course with id {} and name {}", courseId, courseName);
        Course course = new Course(courseId, courseName, classes);
        courseRepository.save(course);
        log.info("Updated course with id - {}, name - {}", courseId, courseName);
    }

    @Override
    public void deleteCourseById(String courseId) {
        log.info("Deleting course with id {}", courseId);
        courseRepository.deleteById(courseId);
        log.info("Deleted course with id - {}", courseId);
    }

    @Override
    public List<Course> findAllCoursesWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for course with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Course> pageResult = courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} courses", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
