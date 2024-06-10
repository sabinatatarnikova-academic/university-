package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.RequestPage;

import java.util.List;

public interface StudyClassService {

    void saveOnlineClass(OnlineClassDTO studyClass);

    void saveOfflineClass(OfflineClassDTO studyClass);

    StudyClass findClassById(String classId);

    void updateStudyClass(StudyClassDTO studyClass, TeacherDTO teacher);

    void deleteClassById(String classId);

    List<StudyClass> findAllClassesWithPagination(RequestPage pageRequest);

    List<StudyClass> findAllClasses();
}
