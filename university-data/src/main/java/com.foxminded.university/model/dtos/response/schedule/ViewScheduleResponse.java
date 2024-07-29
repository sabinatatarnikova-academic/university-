package com.foxminded.university.model.dtos.response.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewScheduleResponse {

    private String id;
    private String groupName;
    private LocalDate startDate;
    private LocalDate endDate;
}
