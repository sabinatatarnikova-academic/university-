package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.entity.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T18:55:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDTO toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.CourseDTOBuilder courseDTO = CourseDTO.builder();

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

        course.id( courseDTO.getId() );
        course.name( courseDTO.getName() );

        return course.build();
    }
}
