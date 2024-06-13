package com.foxminded.university.service.course;

import com.foxminded.university.model.entity.Course;
import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.utils.RequestPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
        log.info("Saved course with name - {}, classes - {}",course.getName(),course.getStudyClasses());
    }

    @Override
    public Course findCourseById(String courseId) {
        Optional <Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            log.error("Course with id {} not found", courseId);
            throw new NoSuchElementException();
        }
        log.info("Founded the course with id {}", courseId);
        return course.get();
    }

    @Override
    public Course findCourseByName(String courseName) {
        Optional <Course> course = courseRepository.findCourseByName(courseName);
        if (!course.isPresent()) {
            log.error("Course with id {} not found", courseName);
            throw new NoSuchElementException();
        }
        log.info("Founded the course with id {}", courseName);
        return course.get();
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.save(course);
        log.info("Updated course with id - {}, name - {}", course.getId(), course.getName());
    }

    @Override
    public void deleteCourseById(String courseId) {
        courseRepository.deleteById(courseId);
        log.info("Deleted course with id - {}", courseId);
    }

    @Override
    public List<Course> findAllCoursesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Page<Course> pageResult = courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} courses", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
