package com.foxminded.university.repository;

import com.foxminded.university.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findLocationByDepartmentAndClassroom(String department, String classroom);
}
