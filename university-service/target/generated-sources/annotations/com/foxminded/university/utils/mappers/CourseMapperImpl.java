package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public CourseDTO toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.CourseDTOBuilder courseDTO = CourseDTO.builder();

        courseDTO.studyClasses( studyClassListToStudyClassResponseList( course.getStudyClasses() ) );
        courseDTO.id( course.getId() );
        courseDTO.name( course.getName() );

        return courseDTO.build();
    }

    @Override
    public Course toEntity(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.studyClasses( studyClassResponseListToStudyClassList( courseDTO.getStudyClasses() ) );
        course.id( courseDTO.getId() );
        course.name( courseDTO.getName() );

        return course.build();
    }

    protected List<StudyClassResponse> studyClassListToStudyClassResponseList(List<StudyClass> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClassResponse> list1 = new ArrayList<StudyClassResponse>( list.size() );
        for ( StudyClass studyClass : list ) {
            list1.add( studyClassMapper.toDto( studyClass ) );
        }

        return list1;
    }

    protected List<StudyClass> studyClassResponseListToStudyClassList(List<StudyClassResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClass> list1 = new ArrayList<StudyClass>( list.size() );
        for ( StudyClassResponse studyClassResponse : list ) {
            list1.add( studyClassMapper.toEntity( studyClassResponse ) );
        }

        return list1;
    }
}
