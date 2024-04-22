package com.foxminded.university.service.studyClasses;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Location;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.users.User;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyClassService {
    void saveOnlineClass(LocalDateTime startTime, LocalDateTime endTime, Course course, User teacher, Group group, String url);

    void saveOfflineClass(LocalDateTime startTime, LocalDateTime endTime, Course course, User teacher, Group group, Location location);

    StudyClass findClassById(String classId);

    void updateOnlineClass(String classId,LocalDateTime startTime, LocalDateTime endTime, Course course, User teacher, Group group, String url);

    void updateOfflineClass(String classId,LocalDateTime startTime, LocalDateTime endTime, Course course, User teacher, Group group, Location location);

    void deleteClassById(String classId);

    List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize);
}
