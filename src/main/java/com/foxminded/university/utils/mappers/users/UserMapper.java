package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface UserMapper {

    UserDTO toDto (User user);

    @InheritInverseConfiguration
    User toEntity (UserDTO userDTO);
}
