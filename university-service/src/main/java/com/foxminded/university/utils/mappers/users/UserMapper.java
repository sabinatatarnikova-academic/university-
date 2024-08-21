package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface UserMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @SubclassMapping(source = Teacher.class, target = TeacherResponse.class)
    @SubclassMapping(source = Student.class, target = StudentResponse.class)
    UserDTO toDto(User user);

    UserFormRequest toDtoRequest(User user);

    @InheritInverseConfiguration
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @SubclassMapping(source = TeacherResponse.class, target = Teacher.class)
    @SubclassMapping(source = StudentResponse.class, target = Student.class)
    User toEntity(UserDTO userDTO);
}
