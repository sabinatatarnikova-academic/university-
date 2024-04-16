package com.foxminded.university.service;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Location;
import com.foxminded.university.model.StudyClass;
import com.foxminded.university.model.User;
import com.foxminded.university.model.enums.Classroom;
import com.foxminded.university.model.enums.Department;
import com.foxminded.university.model.enums.UserType;
import com.foxminded.university.repository.ClassRepository;
import com.foxminded.university.repository.CourseRepository;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.repository.LocationRepository;
import com.foxminded.university.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultUniversityManagementService implements UniversityManagementService {

    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

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

    //! Group
    @Override
    public void saveGroup(String groupName, List<User> users, List<StudyClass> classes) {
        log.info("Adding new group: group name - {}, classes - {}", groupName, classes);
        Group group = new Group(groupName,users, classes);
        groupRepository.save(group);
        log.info("Saved group with name - {}, classes - {}", groupName, classes);
    }

    @Override
    public Group findGroupById(String groupId) {
        log.info("Searching for group with id {}", groupId);
        Group group = groupRepository.findById(groupId).get();
        log.info("Founded the group with id {}", groupId);
        return group;
    }

    @Override
    public Group findGroupByName(String groupName) {
        log.info("Searching for group with name {}", groupName);
        Group group = groupRepository.findGroupByGroupName(groupName).get();
        log.info("Founded the group with id {}", groupName);
        return group;
    }

    @Override
    public void updateGroup(String groupId, String groupName, List<User> users, List<StudyClass> classes) {
        log.info("Updating group with id {} and name {}", groupId, groupName);
        Group group = new Group(groupId, groupName,users, classes);
        groupRepository.save(group);
        log.info("Updated group with id - {}, name - {}", groupId, groupName);
    }

    @Override
    public void deleteGroupById(String groupId) {
        log.info("Deleting group with id {}", groupId);
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    public List<Group> findAllGroupsWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for group with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Group> pageResult = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} groups", pageResult.getTotalPages());
        return pageResult.toList();
    }

    @Override
    public void saveLocation(Department department, Classroom classroom, List<StudyClass> classes) {
        log.info("Adding new location: department - {}, classroom - {}, classes - {}", department, classroom, classes);
        Location location = new Location(department, classroom, classes);
        locationRepository.save(location);
        log.info("Saved location: department - {}, classroom - {}, classes - {}",  department, classroom, classes);
    }

    @Override
    public Location findLocationById(String locationId) {
        log.info("Searching for location with id {}", locationId);
        Location location = locationRepository.findById(locationId).get();
        log.info("Founded the location with id {}", locationId);
        return location;
    }

    @Override
    public Location findLocationByDepartmentAndClassroom(Department department, Classroom classroom) {
        log.info("Searching for location with department - {} and classroom - {}", department, classroom);
        Location location = locationRepository.findLocationByDepartmentAndClassroom(department,classroom).get();
        log.info("Founded the location with department - {} and classroom - {}", department, classroom);
        return location;
    }

    @Override
    public void updateLocation(String locationId, Department department, Classroom classroom, List<StudyClass> classes) {
        log.info("Updating location with id {}, department - {}, classroom - {}, classes - {}", locationId, department, classroom, classes);
        Location location = new Location(locationId, department, classroom, classes);
        locationRepository.save(location);
        log.info("Updated location with id {}, department - {}, classroom - {}, classes - {}", locationId, department, classroom, classes);
    }

    @Override
    public void deleteLocationById(String locationId) {
        log.info("Deleting location with id {}", locationId);
        locationRepository.deleteById(locationId);
        log.info("Deleted location with id - {}", locationId);
    }

    @Override
    public List<Location> findAllLocationsWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for location with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Location> pageResult = locationRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} locations", pageResult.getTotalPages());
        return pageResult.toList();
    }

    @Override
    public void saveUser(String firstName, String lastName, UserType userType, Group group, List<StudyClass> classes) {
        log.info("Adding new user: firstName - {}, lastName - {}, userType - {}, group - {}, classes - {}", firstName, lastName, userType, group, classes);
        User user =  User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userType(userType)
                .group(group)
                .studyStudyClasses(classes)
                .build();
        userRepository.save(user);
        log.info("Saved user: firstName - {}, lastName - {}, userType - {}, group - {}, classes - {}", firstName, lastName, userType, group, classes);
    }

    @Override
    public User findUserById(String userId) {
        log.info("Searching for user with id {}", userId);
        User user = userRepository.findById(userId).get();
        log.info("Founded the user with id {}", userId);
        return user;
    }

    @Override
    public void updateUser(String userId, String firstName, String lastName, UserType userType,Group group, List<StudyClass> classes) {
        log.info("Updating user with id {}, firstName - {}, lastName - {}, userType - {}, group - {}, classes - {}",userId, firstName, lastName, userType, group, classes);
        User user = new User(userId, firstName, lastName, userType, group, classes);
        userRepository.save(user);
        log.info("Updated user with id {}, firstName - {}, lastName - {}, userType - {}, group - {}, classes - {}",userId, firstName, lastName, userType, group, classes);
    }

    @Override
    public void deleteUserById(String userId) {
        log.info("Deleting user with id {}", userId);
        userRepository.deleteById(userId);
        log.info("Deleted user with id - {}", userId);
    }

    @Override
    public List<User> findAllUsersWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for user with page size {} and pageSize {}", pageNumber, pageSize);
        Page<User> pageResult = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult.toList();
    }

    @Override
    public void saveClass( LocalDateTime startTime, LocalDateTime endTime, Location location, Course courses, User teacher, Group group){
        log.info("Adding new class: startTime - {}, endTime - {}, location - {}, courses - {}, teacher - {}, group - {}", startTime, endTime, location, courses, teacher, group);
        StudyClass studyClass =  StudyClass.builder()
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .course(courses)
                .user(teacher)
                .group(group)
                .build();
        classRepository.save(studyClass);
        log.info("Saved class: startTime - {}, endTime - {}, location - {}, courses - {}, teacher - {}, group - {}", startTime, endTime, location, courses, teacher, group);
    }

    @Override
    public  StudyClass findClassById(String classId) {
        log.info("Searching for class with id {}", classId);
        StudyClass studyClass = classRepository.findById(classId).get();
        log.info("Founded the class with id {}", classId);
        return studyClass;
    }

    @Override
    public void updateClass(String classId, LocalDateTime startTime, LocalDateTime endTime, Location location, Course courses, User teacher, Group group) {
        log.info("Updating class with id {}, startTime - {}, endTime - {}, location - {}, courses - {}, teacher - {}, group - {}",classId, startTime, endTime, location, courses, teacher, group);
        StudyClass studyClass = StudyClass.builder()
                .id(classId)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .course(courses)
                .user(teacher)
                .group(group)
                .build();
        classRepository.save(studyClass);
        log.info("Updated class with id {}, startTime - {}, endTime - {}, location - {}, courses - {}, teacher - {}, group - {}",classId, startTime, endTime, location, courses, teacher, group);
    }

    @Override
    public  void deleteClassById(String classId) {
        log.info("Deleting class with id {}", classId);
        classRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public  List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for class with page size {} and pageSize {}", pageNumber, pageSize);
        Page<StudyClass> pageResult = classRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} classs", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
