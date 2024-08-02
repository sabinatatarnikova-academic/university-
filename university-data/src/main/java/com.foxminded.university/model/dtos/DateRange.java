package com.foxminded.university.model.dtos;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;

    @AssertTrue(message = "End date must be after start date")
    public boolean isValidDateRange() {
        return endDate != null && startDate != null && endDate.isAfter(startDate);
    }
}
