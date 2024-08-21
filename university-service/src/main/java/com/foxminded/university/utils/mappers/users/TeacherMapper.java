package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserDTO;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface TeacherMapper {

    @Mapping(target = "studyClasses", source = "studyClasses")
    TeacherResponse toDto(Teacher teacher);

    TeacherResponse toDto(UserDTO userResponse);

    @InheritInverseConfiguration
    Teacher toEntity(TeacherResponse teacherResponse);

    Teacher toEntity(UserDTO userResponse);

    @Mapping(target = "studyClasses", source = "studyClassesIds", qualifiedByName = "mapStudyClasses")
    Teacher toEntity(UserFormRequest teacherDTO);

    @Named("mapStudyClasses")
    default List<StudyClass> mapStudyClasses(List<String> studyClassIds) {
        if (studyClassIds == null) {
            return Collections.emptyList();
        }
        return studyClassIds.stream()
                .map(id -> {
                    StudyClass studyClass = new StudyClass();
                    studyClass.setId(id);
                    return studyClass;
                })
                .toList();
    }
}
