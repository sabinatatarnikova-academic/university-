package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.GroupMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {GroupMapper.class})
public interface StudentMapper {

    StudentDTO toDto(Student student);

    @InheritInverseConfiguration
    Student toEntity(StudentDTO studentDTO);
}
