package com.foxminded.university.service.schedule;

import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.repository.ScheduleTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleTimeServiceImpl implements ScheduleTimeService {

    private final ScheduleTimeRepository scheduleTimeRepository;

    @Override
    public List<ScheduleTimes> findAllLectureTimes() {
        List<ScheduleTimes> scheduleTimes = scheduleTimeRepository.findAll();
        log.info("Find all lecture times");
        return scheduleTimes;
    }

    @Override
    public ScheduleTimes findLectureTimeById(String id) {
        Optional<ScheduleTimes> lectureTime = scheduleTimeRepository.findById(id);

        if (!lectureTime.isPresent()) {
            log.error("Lecture time with id {} not found", id);
            throw new EntityNotFoundException();
        }
        log.info("Founded lecture time with id {}", id);

        return lectureTime.get();
    }
}
