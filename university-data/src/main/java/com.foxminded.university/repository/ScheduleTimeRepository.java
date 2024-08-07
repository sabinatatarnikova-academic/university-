package com.foxminded.university.repository;

import com.foxminded.university.model.entity.ScheduleTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTimeRepository extends JpaRepository<ScheduleTimes, String> {
}
