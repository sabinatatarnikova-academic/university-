package com.foxminded.university.service.group;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {

    void saveGroup(Group group);

    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);

    void updateGroup(Group group);

    void deleteGroupById(String groupId);

    Page<GroupDTO> findAllGroupsWithPagination(RequestPage pageRequest);

    List<GroupDTO> findAllGroups();
}
