package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.LocationDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {OfflineClassMapper.class})
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO toDto(Location location);

    @InheritInverseConfiguration
    Location toEntity(LocationDTO locationDTO);
}
