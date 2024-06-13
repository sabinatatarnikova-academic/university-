package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.LocationMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class, LocationMapper.class})
public interface OfflineClassMapper {

    OfflineClassDTO toDto(OfflineClass offlineClass);

    @InheritInverseConfiguration
    OfflineClass toEntity(OfflineClassDTO offlineClassDTO);
}
