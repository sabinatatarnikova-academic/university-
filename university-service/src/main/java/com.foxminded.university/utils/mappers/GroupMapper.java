package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {

    GroupFormation toDto(Group group);

    GroupAssignResponse toDtoResponse(Group group);

    GroupRequest toDtoRequest(Group group);

    Group toEntity(GroupFormation groupFormation);
}
