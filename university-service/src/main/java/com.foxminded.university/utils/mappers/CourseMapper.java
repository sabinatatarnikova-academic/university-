package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface CourseMapper {

    @Mapping(target = "studyClasses", source = "studyClasses")
    CourseDTO toDto(Course course);

    CourseRequest toDtoRequest(Course course);

    @InheritInverseConfiguration
    Course toEntity(CourseDTO courseDTO);
}
