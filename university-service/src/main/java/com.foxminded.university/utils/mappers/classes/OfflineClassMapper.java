package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.plainClasses.OfflineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.LocationMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class, LocationMapper.class})
public interface OfflineClassMapper {

    OfflineClassResponse toDto(OfflineClass offlineClass);

    @InheritInverseConfiguration
    OfflineClass toEntity(OfflineClassResponse offlineClassDTO);

    OfflineClass toEntity(StudyClassResponse offlineClassDTO);

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "localToZoned")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "localToZoned")
    OfflineClass toEntity(CreateStudyClassResponse offlineClassDTO);

    @Named("localToZoned")
    static ZonedDateTime localToZoned(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(ZoneId.systemDefault()) : null;
    }
}
