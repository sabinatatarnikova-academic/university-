package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudyClassServiceImpl implements StudyClassService {

    private final StudyClassRepository studyClassRepository;
    private final OfflineClassMapper offlineClassMapper;
    private final OnlineClassMapper onlineClassMapper;
    private final StudyClassMapper studyClassMapper;
    private final GroupService groupService;
    private final UserService userService;
    private final LocationService locationService;
    private final CourseRepository courseRepository;

    @Override
    public void saveStudyClass(StudyClassResponse studyClassResponse) {
        if (studyClassResponse.getClassType().equals("ONLINE")) {
            OnlineClass onlineClass = onlineClassMapper.toEntity(studyClassResponse);
            studyClassRepository.save(onlineClass);
        } else {
            OfflineClass offlineClass = offlineClassMapper.toEntity(studyClassResponse);
            studyClassRepository.save(offlineClass);
        }
    }

    @Override
    public StudyClass findClassById(String classId) {
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
    public void updateStudyClass(StudyClassRequest studyClassRequest) {
        StudyClass studyClass = findClassById(studyClassRequest.getId());
        studyClass.setStartTime(studyClassRequest.getStartTime());
        studyClass.setEndTime(studyClassRequest.getEndTime());
        User teacher = userService.findUserById(studyClassRequest.getTeacherId());
        studyClass.setTeacher((Teacher) teacher);
        Optional<Course> course = courseRepository.findById(studyClassRequest.getCourseId());
        if (course.isPresent()) {
            studyClass.setCourse(course.get());
        } else {
            log.warn("Course not found");
        }
        Group group = groupService.findGroupById(studyClassRequest.getGroupId());
        studyClass.setGroup(group);

        if (studyClass instanceof OnlineClass) {
            ((OnlineClass) studyClass).setUrl(studyClassRequest.getUrl());
        } else {
            Location location = locationService.findLocationById(studyClassRequest.getLocation());
            ((OfflineClass) studyClass).setLocation(location);
        }
        studyClassRepository.save(studyClass);
        log.info("Updated study class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}", studyClass.getStartTime(), studyClass.getEndTime(), course.get().getName(), teacher.getId(), group.getName());
    }

    @Override
    public void updateStudyClass(StudyClass studyClass) {
        studyClassRepository.save(studyClass);
        log.info("Updated study class: id - {}", studyClass.getId());
    }

    @Override
    public void deleteClassById(String classId) {
        studyClassRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public Page<StudyClassResponse> findAllClassesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<StudyClass> studyClasses = studyClassRepository.findAll();
        List<StudyClassResponse> studyClassResponses = studyClasses.stream().map(studyClassMapper::toDto).collect(Collectors.toList());
        log.info("Found {} classes", studyClassResponses.size());
        return new PageImpl<>(studyClassResponses, pageable, studyClassResponses.size());
    }

    @Override
    public List<StudyClassResponse> findAllClasses() {
        List<StudyClass> studyClasses = studyClassRepository.findAll();
        log.info("Found {} classes", studyClasses.size());
        List<StudyClassResponse> studyClassResponses = studyClasses.stream().map(studyClassMapper::toDto).collect(Collectors.toList());
        return studyClassResponses;
    }
}
