package com.foxminded.university.model.dtos.response.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudyClassResponse {

    private String id;

    @ToString.Exclude
    private ZonedDateTime startTime;

    @ToString.Exclude
    private ZonedDateTime endTime;

    private String classType;

    private String courseId;

    @ToString.Exclude
    private String courseName;

    private String groupId;

    @ToString.Exclude
    private String groupName;

    private String teacherId;

    @ToString.Exclude
    private String teacherFirstName;

    @ToString.Exclude
    private String teacherLastName;

/*    public ZonedDateTime getStartTime() {
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
    }*/
}
