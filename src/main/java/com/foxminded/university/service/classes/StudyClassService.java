package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.classes.StudyClass;

import java.util.List;

public interface StudyClassService {

    void saveOnlineClass(OnlineClassDTO studyClass);

    void saveOfflineClass(OfflineClassDTO studyClass);

    StudyClass findClassById(String classId);

    void updateOnlineClass(OnlineClassDTO studyClass, TeacherDTO teacher);

    void updateOfflineClass(OfflineClassDTO studyClass, TeacherDTO teacher);

    void deleteClassById(String classId);

    List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize);

}
