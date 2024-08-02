package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
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
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public void saveStudyClass(CreateStudyClassResponse studyClassResponse) {
        if ("ONLINE".equals(studyClassResponse.getClassType())) {
            OnlineClass onlineClass = onlineClassMapper.toEntity(studyClassResponse);
            studyClassRepository.save(onlineClass);
            log.info("Oline class was saved.");
        } else {
            OfflineClass offlineClass = offlineClassMapper.toEntity(studyClassResponse);
            studyClassRepository.save(offlineClass);
            log.info("Offline class was saved.");
        }
    }

    @Override
    public StudyClass findClassById(String classId) {
        Optional<StudyClass> studyClass = studyClassRepository.findById(classId);
        if (!studyClass.isPresent()) {
            log.error("StudyClass with id {} not found", classId);
            throw new EntityNotFoundException();
        }
        log.info("Founded the class with id {}", classId);
        return studyClass.get();
    }

    @Override
    public StudyClassRequest findClassDTOById(String classId) {
        StudyClassRequest dto = studyClassMapper.toDtoRequest(findClassById(classId));
        log.info("Entity studyClass converted to dto");
        return dto;
    }

    @Override
    @Transactional
    public void updateStudyClass(StudyClassRequest studyClassRequest) {
        StudyClass studyClass = findClassById(studyClassRequest.getId());
        if (studyClassRequest.getStartTime() != null) {
            studyClass.setStartTime((studyClassRequest.getStartTime()).atZone(ZoneId.of("UTC")));
        }
        if (studyClassRequest.getEndTime() != null) {
            studyClass.setEndTime((studyClassRequest.getEndTime()).atZone(ZoneId.of("UTC")));
        }
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
            Location location = locationService.findLocationById(studyClassRequest.getLocationId());
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
    @Transactional
    public void deleteTeacherFromStudyClass(String classId) {
        StudyClass studyClass = findClassById(classId);
        Teacher teacher = studyClass.getTeacher();
        studyClass.setTeacher(null);
        teacher.getStudyClasses().remove(studyClass);

        userRepository.save(teacher);
        studyClassRepository.save(studyClass);
    }

    @Override
    public Page<StudyClassResponse> findAllClassesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<StudyClass> studyClasses = studyClassRepository.findAll(pageable);
        List<StudyClassResponse> studyClassResponses = studyClasses.stream().map(studyClassMapper::toDto).collect(Collectors.toList());
        log.info("Found {} classes", studyClassResponses.size());
        return new PageImpl<>(studyClassResponses, pageable, studyClasses.getTotalElements());
    }

    @Override
    public List<StudyClassResponse> findAllClasses() {
        List<StudyClass> studyClasses = studyClassRepository.findAll();
        log.info("Found {} classes", studyClasses.size());
        List<StudyClassResponse> studyClassResponses = studyClasses.stream().map(studyClassMapper::toDto).collect(Collectors.toList());
        return studyClassResponses;
    }

    @Override
    @Transactional
    public EditStudyClassResponse getAllRequiredDataForStudyClassEdit() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseResponses = courses.stream().map(courseMapper::toDto).collect(Collectors.toList());
        List<GroupFormation> groups = groupService.findAllGroups();
        List<TeacherResponse> teachers = userService.findAllTeachers();
        List<LocationDTO> locations = locationService.findAllLocations();

        return EditStudyClassResponse.builder()
                .courses(courseResponses)
                .groups(groups)
                .teachers(teachers)
                .locations(locations)
                .build();
    }

    @Override
    @Transactional
    public GroupEditResponse getAllRequiredDataForGroupEdit(String id, RequestPage page) {
        GroupRequest group = groupService.findGroupDTOById(id);
        Page<StudentResponse> students = userService.findAllStudentsWithPagination(page);
        List<StudyClassResponse> classes = findAllClasses();

        return GroupEditResponse.builder()
                .group(group)
                .students(students)
                .studyClasses(classes)
                .build();
    }
}
