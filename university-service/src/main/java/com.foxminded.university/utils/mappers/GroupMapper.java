package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.dtos.response.GroupResponse;
import com.foxminded.university.model.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {

    GroupDTO toDto(Group group);

    GroupResponse toDtoResponse(Group group);

    Group toEntity(GroupDTO groupDTO);
}
