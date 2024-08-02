package com.foxminded.university.repository;

import com.foxminded.university.model.entity.classes.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
