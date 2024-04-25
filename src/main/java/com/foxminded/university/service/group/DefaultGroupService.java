package com.foxminded.university.service.group;

import com.foxminded.university.model.Group;
import com.foxminded.university.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultGroupService implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public void saveGroup(Group group) {
        log.info("Adding new group: group name - {}, classes - {}",group.getGroupName(), group.getStudyClasses());
        groupRepository.save(group);
        log.info("Saved group with name - {}, classes - {}", group.getGroupName(), group.getStudyClasses());
    }

    @Override
    public Group findGroupById(String groupId) {
        log.info("Searching for group with id {}", groupId);
        Group group = groupRepository.findById(groupId).get();
        log.info("Founded the group with id {}", groupId);
        return group;
    }

    @Override
    public Group findGroupByName(String groupName) {
        log.info("Searching for group with name {}", groupName);
        Group group = groupRepository.findGroupByGroupName(groupName).get();
        log.info("Founded the group with id {}", groupName);
        return group;
    }

    @Override
    public void updateGroup(Group group) {
        log.info("Updating group with id {} and name {}", group.getId(), group.getGroupName());
        groupRepository.save(group);
        log.info("Updated group with id - {}, name - {}", group.getId(), group.getGroupName());
    }

    @Override
    public void deleteGroupById(String groupId) {
        log.info("Deleting group with id {}", groupId);
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    public List<Group> findAllGroupsWithPagination(int pageNumber, int pageSize) {
        log.info("Searching for group with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Group> pageResult = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} groups", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
