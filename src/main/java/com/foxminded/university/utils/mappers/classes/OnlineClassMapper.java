package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class})
public interface OnlineClassMapper {

    OnlineClassMapper INSTANCE = Mappers.getMapper(OnlineClassMapper.class);

    OnlineClassDTO toDto (OnlineClass onlineClass);

    @InheritInverseConfiguration
    OnlineClass toEntity (OnlineClassDTO onlineClassDTO);
}
