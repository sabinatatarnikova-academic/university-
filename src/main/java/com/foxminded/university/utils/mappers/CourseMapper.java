package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface CourseMapper {

    CourseDTO toDto(Course course);

    @InheritInverseConfiguration
    Course toEntity (CourseDTO courseDTO);
}
