package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.GroupFormationDTO;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {

    GroupFormationDTO toDto(Group group);

    GroupAssignResponse toDtoResponse(Group group);

    Group toEntity(GroupFormationDTO groupFormationDTO);
}
