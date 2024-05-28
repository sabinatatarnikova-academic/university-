package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface TeacherMapper {
    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    @Mapping(target = "repeatedPassword", source = "repeatedPassword")
    TeacherDTO toDto (Teacher teacher);

    @InheritInverseConfiguration
    Teacher toEntity (TeacherDTO teacherDTO);
}
