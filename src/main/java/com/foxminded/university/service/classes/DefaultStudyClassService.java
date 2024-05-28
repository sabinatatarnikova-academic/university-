package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.OnlineClassMapper;
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
public class DefaultStudyClassService implements StudyClassService{

    private final StudyClassRepository studyClassRepository;
    private final OfflineClassMapper offlineClassMapper;
    private final OnlineClassMapper onlineClassMapper;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional
    public void saveOnlineClass(OnlineClassDTO studyClass) {
        log.debug("Adding new online class: startTime - {}, endTime - {}, courses - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup(), studyClass.getUrl());
        studyClassRepository.save(onlineClassMapper.toEntity(studyClass));
        log.info("Saved online class: startTime - {}, endTime - {}, courses - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup(), studyClass.getUrl());
    }

    @Override
    @Transactional
    public void saveOfflineClass(OfflineClassDTO studyClass) {
        log.debug("Adding new offline class: startTime - {}, endTime - {}, courses - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup(), studyClass.getLocation());
        studyClassRepository.save(offlineClassMapper.toEntity(studyClass));
        log.info("Saved offline class: startTime - {}, endTime - {}, courses - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getGroup(), studyClass.getLocation());
    }

    @Override
    public StudyClass findClassById(String classId) {
        log.debug("Searching for class with id {}", classId);
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
    public void updateOnlineClass(OnlineClassDTO studyClass, TeacherDTO teacher) {
        log.debug("Updating new online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), teacher, studyClass.getGroup(), studyClass.getUrl());
        OnlineClass onlineClass = onlineClassMapper.toEntity(studyClass);
        onlineClass.setTeacher(teacherMapper.toEntity(teacher));
        studyClassRepository.save(onlineClass);
        log.info("Updated online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), teacher, studyClass.getGroup(), studyClass.getUrl());
    }

    @Override
    public void updateOfflineClass(OfflineClassDTO studyClass, TeacherDTO teacher) {
        log.debug("Updating  offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), teacher, studyClass.getGroup(), studyClass.getLocation());
        OfflineClass offlineClass = offlineClassMapper.toEntity(studyClass);
        offlineClass.setTeacher(teacherMapper.toEntity(teacher));
        studyClassRepository.save(offlineClass);
        log.info("Updated offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), teacher, studyClass.getGroup(), studyClass.getLocation());
    }

    @Override
    public void deleteClassById(String classId) {
        log.debug("Deleting class with id {}", classId);
        studyClassRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize) {
        log.debug("Searching for class with page size {} and pageSize {}", pageNumber, pageSize);
        Page<StudyClass> pageResult = studyClassRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} classs", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
