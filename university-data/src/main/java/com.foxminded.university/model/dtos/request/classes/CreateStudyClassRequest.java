package com.foxminded.university.model.dtos.request.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudyClassRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classType;
}
