package com.foxminded.university.service.group;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.User;

import java.util.List;

public interface GroupService {
    void saveGroup(String groupName, List<Student> students, List<StudyClass> classes);

    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);

    void updateGroup(String groupId, String groupName, List<Student> students, List<StudyClass> classes);

    void deleteGroupById(String groupId);

    List<Group> findAllGroupsWithPagination(int pageNumber, int pageSize);
}
