package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.GroupMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {GroupMapper.class})
public interface StudentMapper {

    StudentResponse toDto(Student student);

    @InheritInverseConfiguration
    Student toEntity(StudentResponse studentResponse);

    @InheritInverseConfiguration
    Student toEntity(UserFormRequest studentDTO);

    Student toEntity(UserResponse userResponse);
}
