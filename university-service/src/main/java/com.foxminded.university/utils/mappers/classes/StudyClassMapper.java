package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudyClassMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @SubclassMapping(source = OnlineClass.class, target = OnlineClassResponse.class)
    @SubclassMapping(source = OfflineClass.class, target = OfflineClassResponse.class)
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherLastName", source = "teacher.lastName")
    StudyClassResponse toDto(StudyClass studyClass);

    @Named("zonedToLocal")
    static LocalDateTime zonedToLocal(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? zonedDateTime.toLocalDateTime() : null;
    }

    @Named("localToZoned")
    static ZonedDateTime localToZoned(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(ZoneId.systemDefault()) : null;
    }

    @InheritInverseConfiguration
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @SubclassMapping(source = OnlineClassResponse.class, target = OnlineClass.class)
    @SubclassMapping(source = OfflineClassResponse.class, target = OfflineClass.class)
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "course.name", source = "courseName")
    @Mapping(target = "group.id", source = "groupId")
    @Mapping(target = "group.name", source = "groupName")
    @Mapping(target = "teacher.id", source = "teacherId")
    @Mapping(target = "teacher.firstName", source = "teacherFirstName")
    @Mapping(target = "teacher.lastName", source = "teacherLastName")
    StudyClass toEntity(StudyClassResponse studyClassResponse);

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "zonedToLocal")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "zonedToLocal")
    StudyClassRequest toDtoRequest(StudyClass studyClass);

    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "localToZoned")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "localToZoned")
    StudyClass toEntity(StudyClassRequest studyClassRequest);
}
