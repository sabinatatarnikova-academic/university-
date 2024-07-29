package com.foxminded.university.model.dtos.request.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleCreateRequest {

    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String groupId;
}
