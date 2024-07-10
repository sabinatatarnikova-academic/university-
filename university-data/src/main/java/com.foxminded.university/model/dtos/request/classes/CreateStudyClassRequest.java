package com.foxminded.university.model.dtos.request.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudyClassRequest {

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String classType;

    public ZonedDateTime getStartTime() {
        return startTime != null ? startTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime != null ? startTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public ZonedDateTime getEndTime() {
        return endTime != null ? endTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime != null ? endTime.withZoneSameInstant(ZoneId.of("Europe/Kiev")) : null;
    }
}
