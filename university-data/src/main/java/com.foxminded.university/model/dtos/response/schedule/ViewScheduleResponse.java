package com.foxminded.university.model.dtos.response.schedule;

import com.foxminded.university.model.dtos.DateRange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewScheduleResponse {

    private String id;
    private String groupName;
    private DateRange dateRange;
}
