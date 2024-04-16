package com.foxminded.university.repository;

import com.foxminded.university.model.StudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<StudyClass, String> {
}
