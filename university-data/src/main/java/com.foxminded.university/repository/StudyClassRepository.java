package com.foxminded.university.repository;

import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyClassRepository extends JpaRepository<StudyClass, String> {
}
