package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
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
public class StudyClassServiceImpl implements StudyClassService {

    private final StudyClassRepository studyClassRepository;
    private final OfflineClassMapper offlineClassMapper;
    private final OnlineClassMapper onlineClassMapper;
    private final StudyClassMapper studyClassMapper;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional
    public void saveOnlineClass(OnlineClassDTO studyClass) {
        studyClassRepository.save(onlineClassMapper.toEntity(studyClass));
        log.info("Saved online class: startTime - {}, endTime - {}, courses - {}, group - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup());
    }

    @Override
    @Transactional
    public void saveOfflineClass(OfflineClassDTO studyClass) {
        studyClassRepository.save(offlineClassMapper.toEntity(studyClass));
        log.info("Saved offline class: startTime - {}, endTime - {}, courses - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup(), studyClass.getLocation());
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
    public void updateStudyClass(StudyClassDTO studyClassDTO, TeacherDTO teacher) {
        StudyClass studyClass = studyClassMapper.toEntity(studyClassDTO);
        studyClass.setTeacher(teacherMapper.toEntity(teacher));
        studyClassRepository.save(studyClass);
        log.info("Updated study class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}", studyClassDTO.getStartTime(), studyClassDTO.getEndTime(), studyClassDTO.getCourse(), teacher, studyClassDTO.getGroup());
    }

    @Override
    public void deleteClassById(String classId) {
        studyClassRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public List<StudyClass> findAllClassesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Page<StudyClass> pageResult = studyClassRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} classes", pageResult.getTotalPages());
        return pageResult.toList();
    }

    @Override
    public List<StudyClass> findAllClasses() {
        List<StudyClass> studyClasses = studyClassRepository.findAll();
        log.info("Found {} classes", studyClasses.size());
        return studyClasses;
    }
}
