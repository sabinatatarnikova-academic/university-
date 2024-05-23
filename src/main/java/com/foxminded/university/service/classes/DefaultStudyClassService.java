package com.foxminded.university.service.classes;

import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.ConverterDtoToEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultStudyClassService implements StudyClassService{

    private final StudyClassRepository studyClassRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final LocationService locationService;
    private final ConverterDtoToEntity converter;

    @Override
    @Transactional
    public void assignTeacherToClass(String teacherId, String classId) {
        Optional<StudyClass> studyClassOptional = studyClassRepository.findById(classId);
        User teacher = userService.findUserById(teacherId);
        if (!studyClassOptional.isPresent()) {
            log.error("StudyClass with id {} not found", classId);
            throw new NoSuchElementException();
        }
        StudyClass studyClass = studyClassOptional.get();
        studyClass.setTeacher((Teacher) teacher);
        studyClassRepository.save(studyClass);
    }

    @Override
    @Transactional
    public void saveOnlineClass(OnlineClassDTO studyClass) {
        Course course = courseService.findCourseById(studyClass.getCourseId());
        Teacher teacher = (Teacher) userService.findUserById(studyClass.getTeacherId());
        Group group = groupService.findGroupById(studyClass.getGroupId());
        log.debug("Adding new online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, studyClass.getUrl());
        studyClassRepository.save(converter.convertOnlineClassDtoToEntity(studyClass, course, teacher, group));
        log.info("Saved online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, studyClass.getUrl());
    }

    @Override
    @Transactional
    public void saveOfflineClass(OfflineClassDTO studyClass) {
        Course course = courseService.findCourseById(studyClass.getCourseId());
        Teacher teacher = (Teacher) userService.findUserById(studyClass.getTeacherId());
        Group group = groupService.findGroupById(studyClass.getGroupId());
        Location location = locationService.findLocationById(studyClass.getLocationId());
        log.debug("Adding new offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, location);
        studyClassRepository.save(converter.convertOfflineClassDtoToEntity(studyClass, course, teacher, group, location));
        log.info("Saved offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(),  course, teacher, group, location);
    }

    @Override
    public StudyClass findClassById(String classId) {
        log.debug("Searching for class with id {}", classId);
        Optional<StudyClass> studyClass = studyClassRepository.findById(classId);
        if (!studyClass.isPresent()) {
            log.error("StudyClass with id {} not found", classId);
            throw new NoSuchElementException();
        }
        log.info("Founded the class with id {}", classId);
        return studyClass.get();
    }

    @Override
    @Transactional
    public void updateOnlineClass(OnlineClassDTO studyClass) {
        Course course = courseService.findCourseById(studyClass.getCourseId());
        Teacher teacher = (Teacher) userService.findUserById(studyClass.getTeacherId());
        Group group = groupService.findGroupById(studyClass.getGroupId());
        log.debug("Updating new online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, studyClass.getUrl());
        studyClassRepository.save(converter.convertOnlineClassDtoToEntity(studyClass, course, teacher, group));
        log.info("Updated online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, studyClass.getUrl());
    }

    @Override
    public void updateOfflineClass(OfflineClassDTO studyClass) {
        Course course = courseService.findCourseById(studyClass.getCourseId());
        Teacher teacher = (Teacher) userService.findUserById(studyClass.getTeacherId());
        Group group = groupService.findGroupById(studyClass.getGroupId());
        Location location = locationService.findLocationById(studyClass.getLocationId());
        log.debug("Updating  offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, location);
        studyClassRepository.save(converter.convertOfflineClassDtoToEntity(studyClass, course, teacher, group, location));
        log.info("Updated offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), course, teacher, group, location);
    }

    @Override
    public void deleteClassById(String classId) {
        log.debug("Deleting class with id {}", classId);
        studyClassRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize) {
        log.debug("Searching for class with page size {} and pageSize {}", pageNumber, pageSize);
        Page<StudyClass> pageResult = studyClassRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} classs", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
