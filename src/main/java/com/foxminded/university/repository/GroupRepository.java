package com.foxminded.university.repository;

import com.foxminded.university.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository  extends JpaRepository<Group, String> {
    Optional<Group> findGroupByGroupName(String groupName);
}
