package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.OnlineClass;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OnlineClassMapperImpl implements OnlineClassMapper {

    @Override
    public OnlineClassResponse toDto(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }

        OnlineClassResponse.OnlineClassResponseBuilder<?, ?> onlineClassResponse = OnlineClassResponse.builder();

        onlineClassResponse.id( onlineClass.getId() );
        onlineClassResponse.startTime( onlineClass.getStartTime() );
        onlineClassResponse.endTime( onlineClass.getEndTime() );
        onlineClassResponse.classType( onlineClass.getClassType() );
        onlineClassResponse.url( onlineClass.getUrl() );

        return onlineClassResponse.build();
    }

    @Override
    public OnlineClass toEntity(OnlineClassResponse onlineClassDTO) {
        if ( onlineClassDTO == null ) {
            return null;
        }

        OnlineClass.OnlineClassBuilder<?, ?> onlineClass = OnlineClass.builder();

        onlineClass.id( onlineClassDTO.getId() );
        onlineClass.startTime( onlineClassDTO.getStartTime() );
        onlineClass.endTime( onlineClassDTO.getEndTime() );
        onlineClass.classType( onlineClassDTO.getClassType() );
        onlineClass.url( onlineClassDTO.getUrl() );

        return onlineClass.build();
    }

    @Override
    public OnlineClass toEntity(StudyClassResponse onlineClassDTO) {
        if ( onlineClassDTO == null ) {
            return null;
        }

        OnlineClass.OnlineClassBuilder<?, ?> onlineClass = OnlineClass.builder();

        onlineClass.id( onlineClassDTO.getId() );
        onlineClass.startTime( onlineClassDTO.getStartTime() );
        onlineClass.endTime( onlineClassDTO.getEndTime() );
        onlineClass.classType( onlineClassDTO.getClassType() );

        return onlineClass.build();
    }

    @Override
    public OnlineClass toEntity(CreateStudyClassResponse studyClassResponse) {
        if ( studyClassResponse == null ) {
            return null;
        }

        OnlineClass.OnlineClassBuilder<?, ?> onlineClass = OnlineClass.builder();

        onlineClass.startTime( studyClassResponse.getStartTime() );
        onlineClass.endTime( studyClassResponse.getEndTime() );
        onlineClass.classType( studyClassResponse.getClassType() );

        return onlineClass.build();
    }
}
