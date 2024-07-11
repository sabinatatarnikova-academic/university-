package com.foxminded.university.model.dtos.request.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudyClassRequest {

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String classType;
}
