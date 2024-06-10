package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T01:47:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OnlineClassMapperImpl implements OnlineClassMapper {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public OnlineClassDTO toDto(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }

        OnlineClassDTO.OnlineClassDTOBuilder<?, ?> onlineClassDTO = OnlineClassDTO.builder();

        onlineClassDTO.id( onlineClass.getId() );
        onlineClassDTO.startTime( onlineClass.getStartTime() );
        onlineClassDTO.endTime( onlineClass.getEndTime() );
        onlineClassDTO.course( courseMapper.toDto( onlineClass.getCourse() ) );
        onlineClassDTO.group( groupMapper.toDto( onlineClass.getGroup() ) );
        onlineClassDTO.url( onlineClass.getUrl() );

        return onlineClassDTO.build();
    }

    @Override
    public OnlineClass toEntity(OnlineClassDTO onlineClassDTO) {
        if ( onlineClassDTO == null ) {
            return null;
        }

        OnlineClass.OnlineClassBuilder<?, ?> onlineClass = OnlineClass.builder();

        onlineClass.id( onlineClassDTO.getId() );
        onlineClass.startTime( onlineClassDTO.getStartTime() );
        onlineClass.endTime( onlineClassDTO.getEndTime() );
        onlineClass.course( courseMapper.toEntity( onlineClassDTO.getCourse() ) );
        onlineClass.group( groupMapper.toEntity( onlineClassDTO.getGroup() ) );
        onlineClass.url( onlineClassDTO.getUrl() );

        return onlineClass.build();
    }
}
