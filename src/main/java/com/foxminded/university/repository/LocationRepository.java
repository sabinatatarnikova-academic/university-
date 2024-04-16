package com.foxminded.university.repository;

import com.foxminded.university.model.Location;
import com.foxminded.university.model.enums.Classroom;
import com.foxminded.university.model.enums.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findLocationByDepartmentAndClassroom(Department department, Classroom classroom);
}
