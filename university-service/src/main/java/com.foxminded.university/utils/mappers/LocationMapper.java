package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {OfflineClassMapper.class})
public interface LocationMapper {

    LocationDTO toDto(Location location);

    @InheritInverseConfiguration
    Location toEntity(LocationDTO locationDTO);
}
