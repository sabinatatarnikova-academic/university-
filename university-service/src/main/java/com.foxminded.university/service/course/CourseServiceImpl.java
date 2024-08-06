package com.foxminded.university.service.course;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.request.CourseTeacherRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudyClassService studyClassService;

    @Override
    public void saveCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        courseRepository.save(course);
        log.info("Saved course with name - {}", courseDTO.getName());
    }

    @Override
    @Transactional
    public void updateCourse(CourseRequest courseDTO) {
        Course course = findCourseById(courseDTO.getId());
        course.setName(courseDTO.getName());
        List<String> studyClassesIds = courseDTO.getStudyClassesIds();
        updateCourseStudyClasses(studyClassesIds, course);
        courseRepository.save(course);
        log.info("Updated course with id - {}, name - {}", course.getId(), course.getName());
    }

    @Override
    public void updateCourse(CourseTeacherRequest courseDTO) {
        Course course = findCourseById(courseDTO.getId());
        List<String> studyClassesIds = courseDTO.getStudyClassesIds();
        updateCourseStudyClasses(studyClassesIds, course);
        courseRepository.save(course);
        log.info("Updated course with id - {}, name - {}", course.getId(), course.getName());
    }

    private void updateCourseStudyClasses(List<String> studyClassesIds, Course course) {
        studyClassesIds.stream()
                .map(classId -> {
                    StudyClass studyClass = studyClassService.findClassById(classId);
                    studyClass.setCourse(course);
                    studyClassService.updateStudyClass(studyClass);
                    course.getStudyClasses().add(studyClass);
                    return studyClass;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Course findCourseById(String courseId) {
        Optional <Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            log.error("Course with id {} not found", courseId);
            throw new EntityNotFoundException();
        }
        log.info("Founded the course with id {}", courseId);
        return course.get();
    }

    @Override
    public CourseRequest findCourseDTOById(String courseId) {
        CourseRequest dto = courseMapper.toDtoRequest(findCourseById(courseId));
        log.info("Entity course converted to dto");
        return dto;
    }

    @Override
    public Course findCourseByName(String courseName) {
        Optional <Course> course = courseRepository.findCourseByName(courseName);
        if (!course.isPresent()) {
            log.error("Course with id {} not found", courseName);
            throw new EntityNotFoundException();
        }
        log.info("Founded the course with id {}", courseName);
        return course.get();
    }

    @Override
    public void deleteCourseById(String courseId) {
        courseRepository.deleteById(courseId);
        log.info("Deleted course with id - {}", courseId);
    }

    @Override
    public Page<CourseDTO> findAllCoursesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Course> courses = courseRepository.findAll(pageable);
        List<CourseDTO> courseResponses = courses.stream().map(courseMapper::toDto).toList();
        Page<CourseDTO> pageResult = new PageImpl<>(courseResponses, pageable, courses.getTotalElements());
        log.info("Found {} courses", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public List<CourseDTO> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseResponses = courses.stream().map(courseMapper::toDto).toList();
        log.info("Found {} courses", courseResponses.size());
        return courseResponses;
    }
}
