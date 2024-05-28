package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {StudentMapper.class, StudyClassMapper.class})
public interface GroupMapper {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "studyClasses", source = "studentDTO")
    GroupDTO toDto(Group group, List<StudentDTO> studentDTO);

    @InheritInverseConfiguration
    @Mapping(target = "studyClasses", source = "studentDTO")
    Group toEntity(GroupDTO groupDTO, List<StudentDTO> studentDTO);
}
