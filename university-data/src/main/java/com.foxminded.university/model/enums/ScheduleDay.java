package com.foxminded.university.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@AllArgsConstructor
@Getter
public enum ScheduleDay {
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY);

    private final DayOfWeek dayOfWeek;
}
