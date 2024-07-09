package com.foxminded.university.service.group;

import com.foxminded.university.model.dtos.request.GroupFormationDTO;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.utils.RequestPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {

    void saveGroup(GroupFormationDTO group);

    Group findGroupById(String groupId);

    GroupAssignResponse findGroupDTOById(String groupId);

    void updateGroup(GroupRequest groupResponse);

    void deleteGroupById(String groupId);

    Page<GroupFormationDTO> findAllGroupsWithPagination(RequestPage pageRequest);

    Page<GroupAssignResponse> findAllGroupsResponsesWithPagination(RequestPage pageRequest);

    List<GroupFormationDTO> findAllGroups();

    List<StudentResponse> findAllStudentsAssignedToGroup(String groupId);

    List<StudyClassResponse> findAllStudyClassesAssignedToGroup(String groupId);
}
