package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.classes.plainClasses.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper (unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = StudyClassMapper.class)
public interface TeacherMapper {

    @Mapping(target = "studyClasses", source = "studyClasses")
    TeacherResponse toDto(Teacher teacher);

    TeacherResponse toDto(UserResponse userResponse);

    @InheritInverseConfiguration
    Teacher toEntity(TeacherResponse teacherResponse);

    Teacher toEntity(UserResponse userResponse);

    @Mapping(target = "studyClasses", source = "studyClassesIds", qualifiedByName = "mapStudyClasses")
    Teacher toEntity(UserFormRequest teacherDTO);

    @Named("mapStudyClasses")
    default List<StudyClass> mapStudyClasses(List<String> studyClassIds) {
        if (studyClassIds == null) {
            return null;
        }
        return studyClassIds.stream()
                .map(id -> {
                    StudyClass studyClass = new StudyClass();
                    studyClass.setId(id);
                    return studyClass;
                })
                .collect(Collectors.toList());
    }
}
