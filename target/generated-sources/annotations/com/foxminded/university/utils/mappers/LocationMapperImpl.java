package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.LocationDTO;
import com.foxminded.university.model.entity.Location;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T18:55:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public LocationDTO toDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDTO.LocationDTOBuilder locationDTO = LocationDTO.builder();

        locationDTO.id( location.getId() );
        locationDTO.department( location.getDepartment() );
        locationDTO.classroom( location.getClassroom() );

        return locationDTO.build();
    }

    @Override
    public Location toEntity(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        Location.LocationBuilder location = Location.builder();

        location.id( locationDTO.getId() );
        location.department( locationDTO.getDepartment() );
        location.classroom( locationDTO.getClassroom() );

        return location.build();
    }
}
