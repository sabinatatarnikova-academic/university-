package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.LocationDTO;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T14:16:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Autowired
    private OfflineClassMapper offlineClassMapper;

    @Override
    public LocationDTO toDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDTO.LocationDTOBuilder locationDTO = LocationDTO.builder();

        locationDTO.id( location.getId() );
        locationDTO.department( location.getDepartment() );
        locationDTO.classroom( location.getClassroom() );
        locationDTO.studyClass( offlineClassListToOfflineClassDTOList( location.getStudyClass() ) );

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
        location.studyClass( offlineClassDTOListToOfflineClassList( locationDTO.getStudyClass() ) );

        return location.build();
    }

    protected List<OfflineClassDTO> offlineClassListToOfflineClassDTOList(List<OfflineClass> list) {
        if ( list == null ) {
            return null;
        }

        List<OfflineClassDTO> list1 = new ArrayList<OfflineClassDTO>( list.size() );
        for ( OfflineClass offlineClass : list ) {
            list1.add( offlineClassMapper.toDto( offlineClass ) );
        }

        return list1;
    }

    protected List<OfflineClass> offlineClassDTOListToOfflineClassList(List<OfflineClassDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OfflineClass> list1 = new ArrayList<OfflineClass>( list.size() );
        for ( OfflineClassDTO offlineClassDTO : list ) {
            list1.add( offlineClassMapper.toEntity( offlineClassDTO ) );
        }

        return list1;
    }
}
