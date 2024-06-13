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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
        log.info("Saved group with name - {}, classes - {}", group.getGroupName(), group.getStudyClasses());
    }

    @Override
    public Group findGroupById(String groupId) {
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
        groupRepository.save(group);
        log.info("Updated group with id - {}, name - {}", group.getId(), group.getGroupName());
    }

    @Override
    public void deleteGroupById(String groupId) {
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    public List<Group> findAllGroupsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Page<Group> pageResult = groupRepository.findAll(PageRequest.of(pageNumber, pageSize));
        log.info("Found {} groups", pageResult.getTotalPages());
        return pageResult.toList();
    }

    @Override
    public List<Group> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        log.info("Found {} groups", groups.size());
        return groups;
    }


}
