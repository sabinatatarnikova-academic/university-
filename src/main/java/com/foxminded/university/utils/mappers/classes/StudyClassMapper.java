package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.LocationMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class, LocationMapper.class})
public interface StudyClassMapper {

    StudyClassDTO toDto(StudyClass studyClass);

    @Mapping(target = "url", source = "url", ignore = true)
    StudyClassDTO toDto(StudyClass studyClass, String url);

    @Mapping(target = "id", source = "studyClass.id")
    @Mapping(target = "location", source = "location", ignore = true)
    StudyClassDTO toDto(StudyClass studyClass, Location location);

    @InheritInverseConfiguration
    StudyClass toEntity(StudyClassDTO studyClassDTO);

}
