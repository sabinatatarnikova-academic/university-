package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.entity.Group;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupMapper {

    GroupDTO toDto(Group group);

    @InheritInverseConfiguration
    Group toEntity(GroupDTO groupDTO);
}
