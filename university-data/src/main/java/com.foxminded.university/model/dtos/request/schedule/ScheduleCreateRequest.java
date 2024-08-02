package com.foxminded.university.model.dtos.request.schedule;

import com.foxminded.university.model.dtos.DateRange;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleCreateRequest {

    private String id;

    @Valid
    private DateRange dateRange;
    private String groupId;
}
