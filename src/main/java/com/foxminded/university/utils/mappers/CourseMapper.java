package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDTO toDto(Course course);

    @InheritInverseConfiguration
    Course toEntity (CourseDTO courseDTO);
}
