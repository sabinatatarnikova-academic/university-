package com.foxminded.university.service;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Location;
import com.foxminded.university.model.StudyClass;
import com.foxminded.university.model.User;
import com.foxminded.university.model.enums.Classroom;
import com.foxminded.university.model.enums.Department;
import com.foxminded.university.model.enums.UserType;

import java.time.LocalDateTime;
import java.util.List;

public interface UniversityManagementService {

    //! Course

    void saveCourse(String courseName, List<StudyClass> classes);

    Course findCourseById(String courseId);

    Course findCourseByName(String courseName);

    void updateCourse(String courseId, String courseName, List<StudyClass> classes);

    void deleteCourseById(String courseId);

    List<Course> findAllCoursesWithPagination(int pageNumber, int pageSize);

    //! Group

    void saveGroup(String groupName, List<User> users, List<StudyClass> classes);

    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);

    void updateGroup(String groupId, String groupName, List<User> users, List<StudyClass> classes);

    void deleteGroupById(String groupId);

    List<Group> findAllGroupsWithPagination(int pageNumber, int pageSize);

    //! Location

    void saveLocation(Department department, Classroom classroom, List<StudyClass> classes);

    Location findLocationById(String locationId);

    Location findLocationByDepartmentAndClassroom(Department department, Classroom classroom);

    void updateLocation(String locationId, Department department, Classroom classroom, List<StudyClass> classes);

    void deleteLocationById(String locationId);

    List<Location> findAllLocationsWithPagination(int pageNumber, int pageSize);

    //! User

    void saveUser(String firstName, String lastName, UserType userType, Group group, List<StudyClass> classes);

    User findUserById(String userId);

    void updateUser(String userId, String firstName, String lastName, UserType userType, Group group, List<StudyClass> classes);

    void deleteUserById(String userId);

    List<User> findAllUsersWithPagination(int pageNumber, int pageSize);

    //!Classes

    void saveClass(LocalDateTime startTime, LocalDateTime endTime, Location location, Course course, User user, Group group);

    StudyClass findClassById(String classId);

    void updateClass(String classId, LocalDateTime startTime, LocalDateTime endTime, Location location, Course course, User user, Group group);

    void deleteClassById(String classId);

    List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize);
}
