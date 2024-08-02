package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.entity.classes.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "dateRange.startDate", source = "startDate")
    @Mapping(target = "dateRange.endDate", source = "endDate")
    ViewScheduleResponse toDto(Schedule schedule);
}
