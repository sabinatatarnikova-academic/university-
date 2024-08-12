package com.foxminded.university.service.schedule;

import com.foxminded.university.model.entity.ScheduleTimes;

import java.util.List;

public interface ScheduleTimeService {

    List<ScheduleTimes> findAllLectureTimes();

    ScheduleTimes findLectureTimeById(String id);
}
