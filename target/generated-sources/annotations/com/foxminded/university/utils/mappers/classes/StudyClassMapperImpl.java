package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T18:55:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudyClassMapperImpl implements StudyClassMapper {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public StudyClassDTO toDto(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studyClass.getId() );
        studyClassDTO.startTime( studyClass.getStartTime() );
        studyClassDTO.endTime( studyClass.getEndTime() );
        studyClassDTO.course( courseMapper.toDto( studyClass.getCourse() ) );
        studyClassDTO.group( groupMapper.toDto( studyClass.getGroup() ) );

        return studyClassDTO.build();
    }

    @Override
    public StudyClassDTO toDto(StudyClass studyClass, String url) {
        if ( studyClass == null && url == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        if ( studyClass != null ) {
            studyClassDTO.id( studyClass.getId() );
            studyClassDTO.startTime( studyClass.getStartTime() );
            studyClassDTO.endTime( studyClass.getEndTime() );
            studyClassDTO.course( courseMapper.toDto( studyClass.getCourse() ) );
            studyClassDTO.group( groupMapper.toDto( studyClass.getGroup() ) );
        }

        return studyClassDTO.build();
    }

    @Override
    public StudyClassDTO toDto(StudyClass studyClass, Location location) {
        if ( studyClass == null && location == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        if ( studyClass != null ) {
            studyClassDTO.id( studyClass.getId() );
            studyClassDTO.startTime( studyClass.getStartTime() );
            studyClassDTO.endTime( studyClass.getEndTime() );
            studyClassDTO.course( courseMapper.toDto( studyClass.getCourse() ) );
            studyClassDTO.group( groupMapper.toDto( studyClass.getGroup() ) );
        }

        return studyClassDTO.build();
    }

    @Override
    public StudyClass toEntity(StudyClassDTO studyClassDTO) {
        if ( studyClassDTO == null ) {
            return null;
        }

        StudyClass.StudyClassBuilder<?, ?> studyClass = StudyClass.builder();

        studyClass.id( studyClassDTO.getId() );
        studyClass.startTime( studyClassDTO.getStartTime() );
        studyClass.endTime( studyClassDTO.getEndTime() );
        studyClass.course( courseMapper.toEntity( studyClassDTO.getCourse() ) );
        studyClass.group( groupMapper.toEntity( studyClassDTO.getGroup() ) );

        return studyClass.build();
    }
}
