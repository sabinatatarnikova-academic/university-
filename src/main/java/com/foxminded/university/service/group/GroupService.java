package com.foxminded.university.service.group;

import com.foxminded.university.model.Group;

import java.util.List;

public interface GroupService {
    void saveGroup(Group group);

    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);

    void updateGroup(Group group);

    void deleteGroupById(String groupId);

    List<Group> findAllGroupsWithPagination(int pageNumber, int pageSize);
}
