package com.foxminded.university.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;


@AllArgsConstructor
@Getter
public enum ScheduleTime {
    FIRST_LECTURE(LocalTime.of(8, 00), LocalTime.of(9, 30)),
    SECOND_LECTURE(LocalTime.of(9, 50), LocalTime.of(11, 20)),
    THIRD_LECTURE(LocalTime.of(11, 40), LocalTime.of(13, 10)),
    FOURTH_LECTURE(LocalTime.of(13, 30), LocalTime.of(15, 00)),
    FIFTH_LECTURE(LocalTime.of(15, 20), LocalTime.of(16, 50));

    private final LocalTime startTime;
    private final LocalTime endTime;
}
