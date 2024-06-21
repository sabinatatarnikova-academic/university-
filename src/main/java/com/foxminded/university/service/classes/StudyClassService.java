package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudyClassService {

    void saveStudyClass(StudyClassResponse studyClassResponse);

    StudyClass findClassById(String classId);

    void updateStudyClass(StudyClassRequest studyClass);

    void updateStudyClass(StudyClass studyClass);

    void deleteClassById(String classId);

    Page<StudyClassResponse> findAllClassesWithPagination(RequestPage pageRequest);

    List<StudyClassResponse> findAllClasses();
}
