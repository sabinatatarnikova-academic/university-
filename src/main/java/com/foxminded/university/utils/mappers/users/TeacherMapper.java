package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
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
    TeacherDTO toDto (Teacher teacher);

    TeacherDTO toDto (UserDTO userDTO);

    @InheritInverseConfiguration
    Teacher toEntity (TeacherDTO teacherDTO);

    Teacher toEntity(UserDTO userDTO);

    @Mapping(target = "studyClasses", source = "studyClasses", qualifiedByName = "mapStudyClasses")
    Teacher toEntity(UserFormDTO teacherDTO);

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
