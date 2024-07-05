package com.foxminded.university.service.group;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {

    void saveGroup(GroupDTO group);

    Group findGroupById(String groupId);

    Group findGroupByName(String groupName);

    void updateGroup(GroupRequest groupResponse);

    void deleteGroupById(String groupId);

    Page<GroupDTO> findAllGroupsWithPagination(RequestPage pageRequest);

    Page<GroupResponse> findAllGroupsResponsesWithPagination(RequestPage pageRequest);

    List<GroupDTO> findAllGroups();

    List<StudentResponse> findAllStudentsAssignedToGroup(String groupId);

    List<StudyClassResponse> findAllStudyClassesAssignedToGroup(String groupId);
}
