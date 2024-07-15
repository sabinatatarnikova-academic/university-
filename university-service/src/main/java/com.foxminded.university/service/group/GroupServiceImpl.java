package com.foxminded.university.service.group;

import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.repository.UserRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupServiceImpl implements GroupService {

    private final StudyClassMapper studyClassMapper;
    private final StudentMapper studentMapper;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final StudyClassRepository studyClassRepository;

    @Override
    public void saveGroup(GroupFormation group) {
        groupRepository.save(groupMapper.toEntity(group));
        log.info("Saved group with name - {}", group.getName());
    }

    @Override
    public Group findGroupById(String groupId) {
        Optional <Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) {
            log.warn("Course with id {} not found", groupId);
            throw new EntityNotFoundException();
        }
        log.info("Founded the group with id {}", groupId);
        return group.get();
    }

    @Override
    public GroupRequest findGroupDTOById(String groupId) {
        GroupRequest dto = groupMapper.toDtoRequest(findGroupById(groupId));
        log.info("Entity group converted to dto");
        return dto;
    }

    @Override
    @Transactional
    public void updateGroup(GroupRequest groupRequest) {
        Group group = Group.builder()
                .id(groupRequest.getId())
                .name(groupRequest.getName())
                .build();

        if (!groupRequest.getStudentsIds().isEmpty()) {
            group.setStudents(
                    groupRequest.getStudentsIds().stream()
                            .map(studentId -> assignStudentToGroup(group.getId(), studentId))
                            .collect(Collectors.toList())
            );
        }

        if (!groupRequest.getStudyClassesIds().isEmpty()) {
            group.setStudyClasses(
                    groupRequest.getStudyClassesIds().stream()
                            .map(studyClassId -> assignClassToGroup(group.getId(), studyClassId))
                            .collect(Collectors.toList())
            );
        }

        groupRepository.save(group);
        log.info("Updated group info: id - {}, name - {}", group.getId(), group.getName());
    }

    private Student assignStudentToGroup(String groupId, String studentId) {
        Optional<User> studentOptional = userRepository.findById(studentId);
        Group group = findGroupById(groupId);
        if (!studentOptional.isPresent()) {
            log.warn("Student with id {} not found", studentId);
            throw new EntityNotFoundException();
        }
        Student student = (Student) studentOptional.get();
        student.setGroup(group);
        userRepository.save(student);
        log.debug("Student was assigned to group");
        return student;
    }

    private StudyClass assignClassToGroup(String groupId, String classId) {
        Optional<StudyClass> studyClassOptional = studyClassRepository.findById(classId);
        Group group = findGroupById(groupId);
        if (!studyClassOptional.isPresent()) {
            log.warn("StudyClass with id {} not found", classId);
            throw new EntityNotFoundException();
        }
        StudyClass studyClass = studyClassOptional.get();
        studyClass.setGroup(group);
        studyClassRepository.save(studyClass);
        log.debug("Class was assigned to group");
        return studyClass;
    }

    @Override
    public void deleteGroupById(String groupId) {
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    @Transactional
    public void deleteStudentFromGroupById(String studentId) {
        Optional <User> optionalUser = userRepository.findById(studentId);
        if (!optionalUser.isPresent()){
            log.warn("User with id {} not found", studentId);
            throw new EntityNotFoundException();
        }
        Student student = (Student) optionalUser.get();
        Group group = student.getGroup();
        group.getStudents().remove(student);
        student.setGroup(null);

        groupRepository.save(group);
        userRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudyClassFromGroupById(String classId) {
        Optional<StudyClass> classOptional = studyClassRepository.findById(classId);
        if (!classOptional.isPresent()){
            log.warn("StudyClass with id {} not found", classId);
            throw new EntityNotFoundException();
        }
        StudyClass studyClass =  classOptional.get();
        Group group = studyClass.getGroup();
        group.getStudyClasses().remove(studyClass);
        studyClass.setGroup(null);

        groupRepository.save(group);
        studyClassRepository.save(studyClass);
    }

    @Override
    public Page<GroupFormation> findAllGroupsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Group> groups = groupRepository.findAll(pageable);
        List<GroupFormation> groupFormations = groups.stream().map(groupMapper::toDto).toList();
        log.info("Found {} groups", groupFormations.size());
        return new PageImpl<>(groupFormations, pageable, groups.getTotalElements());
    }

    @Override
    public Page<GroupAssignResponse> findAllGroupsResponsesWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Group> groups = groupRepository.findAll(pageable);
        List<GroupAssignResponse> groupRespons = groups.stream().map(groupMapper::toDtoResponse).collect(Collectors.toList());
        log.info("Found {} groups", groupRespons.size());
        return new PageImpl<>(groupRespons, pageable, groups.getTotalElements());
    }

    @Override
    public List<GroupFormation> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupFormation> groupFormations = groups.stream().map(groupMapper::toDto).collect(Collectors.toList());
        log.info("Found {} groups", groups.size());
        return groupFormations;
    }

    @Override
    public List<StudentResponse> findAllStudentsAssignedToGroup(String groupId) {
        Group group = findGroupById(groupId);
        List<Student> students = group.getStudents();
        int studentQuantity;
        if (students == null) {
            studentQuantity = 0;
        } else {
            studentQuantity = students.size();
        }
        log.info("Find {} students in group - {}", studentQuantity, group.getName());
        return students != null ? students.stream().map(studentMapper::toDto).collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override
    public List<StudyClassResponse> findAllStudyClassesAssignedToGroup(String groupId) {
        Group group = findGroupById(groupId);
        List<StudyClass> studyClasses = group.getStudyClasses();

        List<StudyClassResponse> studyClassResponses =
                (studyClasses == null ? Collections.<StudyClass>emptyList() : studyClasses)
                        .stream()
                        .map(studyClassMapper::toDto)
                        .collect(Collectors.toList());

        int studyClassQuantity = studyClassResponses.size();

        log.info("Find {} study classes in group - {}", studyClassQuantity, group.getName());
        return studyClassResponses;
    }
}
