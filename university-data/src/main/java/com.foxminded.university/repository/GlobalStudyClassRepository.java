package com.foxminded.university.repository;

import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalStudyClassRepository extends JpaRepository<GlobalStudyClass, String> {
}
