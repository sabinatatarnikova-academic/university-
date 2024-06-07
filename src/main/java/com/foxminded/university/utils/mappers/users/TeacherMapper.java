package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface TeacherMapper {

    TeacherDTO toDto (Teacher teacher);

    TeacherDTO toDto (UserDTO userDTO);

    @InheritInverseConfiguration
    Teacher toEntity (TeacherDTO teacherDTO);

    @InheritInverseConfiguration
    Teacher toEntity(UserFormDTO teacherDTO);
}
