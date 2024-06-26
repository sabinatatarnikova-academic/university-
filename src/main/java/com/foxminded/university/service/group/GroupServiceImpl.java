package com.foxminded.university.service.group;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.GroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
        log.info("Saved group with name - {}, classes - {}", group.getName(), group.getStudyClasses());
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
        Optional<Group> group = groupRepository.findGroupByName(groupName);
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
        log.info("Updated group with id - {}, name - {}", group.getId(), group.getName());
    }

    @Override
    public void deleteGroupById(String groupId) {
        groupRepository.deleteById(groupId);
        log.info("Deleted group with id - {}", groupId);
    }

    @Override
    public Page<GroupDTO> findAllGroupsWithPagination(RequestPage pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = groups.stream().map(groupMapper::toDto).toList();
        log.info("Found {} groups", groupDTOs.size());
        return new PageImpl<>(groupDTOs, pageable, groupDTOs.size());
    }

    @Override
    public List<GroupDTO> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = groups.stream().map(groupMapper::toDto).collect(Collectors.toList());
        log.info("Found {} groups", groups.size());
        return groupDTOs;
    }
}
