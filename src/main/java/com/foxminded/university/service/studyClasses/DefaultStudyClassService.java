package com.foxminded.university.service.studyClasses;

import com.foxminded.university.model.classes.OfflineClass;
import com.foxminded.university.model.classes.OnlineClass;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.classes.dtos.OfflineClassDTO;
import com.foxminded.university.model.classes.dtos.OnlineClassDTO;
import com.foxminded.university.repository.StudyClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultStudyClassService implements StudyClassService{

    private final StudyClassRepository studyClassRepository;

    @Override
    public void saveOnlineClass(OnlineClassDTO studyClass) {
        log.info("Adding new online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getUrl());
        studyClassRepository.save(convertOnlineClassDtoToEntity(studyClass));
        log.info("Saved online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getUrl());
    }

    @Override
    public void saveOfflineClass(OfflineClassDTO studyClass) {
        log.info("Adding new offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getLocation());
        studyClassRepository.save(convertOfflineClassDtoToEntity(studyClass));
        log.info("Saved offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getLocation());
    }

    @Override
    public StudyClass findClassById(String classId) {
        log.info("Searching for class with id {}", classId);
        StudyClass studyClass = studyClassRepository.findById(classId).get();
        log.info("Founded the class with id {}", classId);
        return studyClass;
    }

    @Override
    public void updateOnlineClass(OnlineClassDTO studyClass) {
        log.info("Updating new online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getUrl());
        studyClassRepository.save(convertOnlineClassDtoToEntity(studyClass));
        log.info("Updated online class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, url - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getUrl());
    }

    @Override
    public void updateOfflineClass(OfflineClassDTO studyClass) {
        log.info("Updating  offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getLocation());
        studyClassRepository.save(convertOfflineClassDtoToEntity(studyClass));
        log.info("Updated offline class: startTime - {}, endTime - {}, courses - {}, teacher - {}, group - {}, location - {}", studyClass.getStartTime(), studyClass.getEndTime(), studyClass.getCourse(), studyClass.getTeacher(),studyClass.getGroup(), studyClass.getLocation());
    }

    @Override
    public void deleteClassById(String classId) {
        log.info("Deleting class with id {}", classId);
        studyClassRepository.deleteById(classId);
        log.info("Deleted class with id - {}", classId);
    }

    @Override
    public List<StudyClass> findAllClassesWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for class with page size {} and pageSize {}", pageNumber, pageSize);
        Page<StudyClass> pageResult = studyClassRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} classs", pageResult.getTotalPages());
        return pageResult.toList();
    }

    private OnlineClass convertOnlineClassDtoToEntity(OnlineClassDTO onlineClassDTO) {
        OnlineClass onlineClass = new OnlineClass();
        onlineClass.setId(onlineClassDTO.getId());
        onlineClass.setStartTime(onlineClassDTO.getStartTime());
        onlineClass.setEndTime(onlineClassDTO.getEndTime());
        onlineClass.setCourse(onlineClassDTO.getCourse());
        onlineClass.setTeacher(onlineClassDTO.getTeacher());
        onlineClass.setGroup(onlineClassDTO.getGroup());
        onlineClass.setUrl(onlineClassDTO.getUrl());
        return onlineClass;
    }

   private OfflineClass convertOfflineClassDtoToEntity(OfflineClassDTO offlineClassDTO) {
        OfflineClass offlineClass = new OfflineClass();
        offlineClass.setId(offlineClassDTO.getId());
        offlineClass.setStartTime(offlineClassDTO.getStartTime());
        offlineClass.setEndTime(offlineClassDTO.getEndTime());
        offlineClass.setCourse(offlineClassDTO.getCourse());
        offlineClass.setTeacher(offlineClassDTO.getTeacher());
        offlineClass.setGroup(offlineClassDTO.getGroup());
        offlineClass.setLocation(offlineClassDTO.getLocation());
        return offlineClass;
    }
}
