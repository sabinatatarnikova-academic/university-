package com.foxminded.university.service.group;

import com.foxminded.university.model.entity.Group;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.utils.RequestPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DefaultGroupService implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public void saveGroup(Group group) {
        log.debug("Adding new group: group name - {}, classes - {}",group.getGroupName(), group.getStudyClasses());
        groupRepository.save(group);
        log.info("Saved group with name - {}, classes - {}", group.getGroupName(), group.getStudyClasses());
    }

    @Override
    public Group findGroupById(String groupId) {
        log.debug("Searching for group with id {}", groupId);
        Optional <Group> group = groupRepository.findById(groupId);
        if (!group.isPresent()) {
            log.error("Course with id {} not found", groupId);
            throw new NoSuchElementException();
        }
        log.info("Founded the group with id {}", groupId);
        return group.get();
    }

    @Override
    public Group findGroupByName(String groupName) {
        log.debug("Searching for group with name {}", groupName);
        Optional <Group> group = groupRepository.findGroupByGroupName(groupName);
        if (!group.isPresent()) {
            log.error("Course with name {} not found", groupName);
            throw new NoSuchElementException();
        }
        log.info("Founded the group with id {}", groupName);
        return group.get();
    }

    @Override
    public void updateGroup(Group group) {
        log.debug("Updating group with id {} and name {}", group.getId(), group.getGroupName());
        groupRepository.save(group);
        log.info("Updated group with id - {}, name - {}", group.getId(), group.getGroupName());
    }

    @Override
    public void deleteGroupById(String groupId) {
        log.debug("Deleting group with id {}", groupId);
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    public List<Group> findAllGroupsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        log.debug("Searching for group with page size {} and pageSize {}", pageNumber, pageSize);
        Page<Group> pageResult = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} groups", pageResult.getTotalPages());
        return pageResult.toList();
    }
}
