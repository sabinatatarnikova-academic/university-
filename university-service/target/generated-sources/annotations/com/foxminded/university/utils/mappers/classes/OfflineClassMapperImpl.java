package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.utils.mappers.LocationMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OfflineClassMapperImpl implements OfflineClassMapper {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public OfflineClassResponse toDto(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }

        OfflineClassResponse.OfflineClassResponseBuilder<?, ?> offlineClassResponse = OfflineClassResponse.builder();

        offlineClassResponse.id( offlineClass.getId() );
        offlineClassResponse.startTime( offlineClass.getStartTime() );
        offlineClassResponse.endTime( offlineClass.getEndTime() );
        offlineClassResponse.classType( offlineClass.getClassType() );
        offlineClassResponse.location( locationMapper.toDto( offlineClass.getLocation() ) );

        return offlineClassResponse.build();
    }

    @Override
    public OfflineClass toEntity(OfflineClassResponse offlineClassDTO) {
        if ( offlineClassDTO == null ) {
            return null;
        }

        OfflineClass.OfflineClassBuilder<?, ?> offlineClass = OfflineClass.builder();

        offlineClass.id( offlineClassDTO.getId() );
        offlineClass.startTime( offlineClassDTO.getStartTime() );
        offlineClass.endTime( offlineClassDTO.getEndTime() );
        offlineClass.classType( offlineClassDTO.getClassType() );
        offlineClass.location( locationMapper.toEntity( offlineClassDTO.getLocation() ) );

        return offlineClass.build();
    }

    @Override
    public OfflineClass toEntity(StudyClassResponse offlineClassDTO) {
        if ( offlineClassDTO == null ) {
            return null;
        }

        OfflineClass.OfflineClassBuilder<?, ?> offlineClass = OfflineClass.builder();

        offlineClass.id( offlineClassDTO.getId() );
        offlineClass.startTime( offlineClassDTO.getStartTime() );
        offlineClass.endTime( offlineClassDTO.getEndTime() );
        offlineClass.classType( offlineClassDTO.getClassType() );

        return offlineClass.build();
    }

    @Override
    public OfflineClass toEntity(CreateStudyClassResponse offlineClassDTO) {
        if ( offlineClassDTO == null ) {
            return null;
        }

        OfflineClass.OfflineClassBuilder<?, ?> offlineClass = OfflineClass.builder();

        offlineClass.startTime( offlineClassDTO.getStartTime() );
        offlineClass.endTime( offlineClassDTO.getEndTime() );
        offlineClass.classType( offlineClassDTO.getClassType() );

        return offlineClass.build();
    }
}
