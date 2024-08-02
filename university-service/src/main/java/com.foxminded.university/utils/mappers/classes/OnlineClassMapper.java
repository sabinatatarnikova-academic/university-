package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, GroupMapper.class})
public interface OnlineClassMapper {

    OnlineClassResponse toDto(OnlineClass onlineClass);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "onlineClassDTO.id")
    OnlineClass toEntity(OnlineClassResponse onlineClassDTO);

    OnlineClass toEntity(StudyClassResponse onlineClassDTO);

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "localToZoned")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "localToZoned")
    OnlineClass toEntity(CreateStudyClassResponse studyClassResponse);

    @Named("localToZoned")
    static ZonedDateTime localToZoned(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(ZoneId.systemDefault()) : null;
    }
}
