package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.LocationMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T18:55:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OfflineClassMapperImpl implements OfflineClassMapper {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public OfflineClassDTO toDto(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }

        OfflineClassDTO.OfflineClassDTOBuilder offlineClassDTO = OfflineClassDTO.builder();

        offlineClassDTO.id( offlineClass.getId() );
        offlineClassDTO.startTime( offlineClass.getStartTime() );
        offlineClassDTO.endTime( offlineClass.getEndTime() );
        offlineClassDTO.course( courseMapper.toDto( offlineClass.getCourse() ) );
        offlineClassDTO.group( groupMapper.toDto( offlineClass.getGroup() ) );
        offlineClassDTO.location( locationMapper.toDto( offlineClass.getLocation() ) );

        return offlineClassDTO.build();
    }

    @Override
    public OfflineClass toEntity(OfflineClassDTO offlineClassDTO) {
        if ( offlineClassDTO == null ) {
            return null;
        }

        OfflineClass.OfflineClassBuilder<?, ?> offlineClass = OfflineClass.builder();

        offlineClass.id( offlineClassDTO.getId() );
        offlineClass.startTime( offlineClassDTO.getStartTime() );
        offlineClass.endTime( offlineClassDTO.getEndTime() );
        offlineClass.course( courseMapper.toEntity( offlineClassDTO.getCourse() ) );
        offlineClass.group( groupMapper.toEntity( offlineClassDTO.getGroup() ) );
        offlineClass.location( locationMapper.toEntity( offlineClassDTO.getLocation() ) );

        return offlineClass.build();
    }
}
