package com.foxminded.university.model.dtos.response.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudyClassResponse {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classType;
}
