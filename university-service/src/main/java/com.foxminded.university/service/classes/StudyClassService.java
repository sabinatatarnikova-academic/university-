package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.plainClasses.StudyClass;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudyClassService {

    void saveStudyClass(CreateStudyClassResponse studyClassResponse);

    StudyClass findClassById(String classId);

    StudyClassRequest findClassDTOById(String classId);

    void updateStudyClass(StudyClassRequest studyClass);

    void updateStudyClass(StudyClass studyClass);

    void deleteClassById(String classId);

    void deleteTeacherFromStudyClass(String classId);

    Page<StudyClassResponse> findAllClassesWithPagination(RequestPage pageRequest);

    List<StudyClassResponse> findAllClasses();

    EditStudyClassResponse getAllRequiredDataForStudyClassEdit();

    GroupEditResponse getAllRequiredDataForGroupEdit(String id, RequestPage page);
}
