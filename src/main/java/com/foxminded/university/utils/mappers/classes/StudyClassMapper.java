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
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class, LocationMapper.class})
public interface StudyClassMapper {
    StudyClassMapper INSTANCE = Mappers.getMapper(StudyClassMapper.class);
    @Mapping(target = "id", source = "studyClass.id")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "location", source = "location")
    StudyClassDTO toDto(StudyClass studyClass, String url, Location location);

    @InheritInverseConfiguration
    StudyClass toEntity(StudyClassDTO studyClassDTO);

    //List<StudyClass> toEntity(List<StudyClassDTO> studyClassDTO);

   // Set<StudyClassDTO> toDto(Set<StudyClass> studyClass);
}
