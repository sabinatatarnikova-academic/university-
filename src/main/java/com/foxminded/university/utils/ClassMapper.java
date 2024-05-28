/*package com.foxminded.university.utils;

import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    @Mappings({
            @Mapping(target = "course", source = "course"),
            @Mapping(target = "teacher", source = "teacher"),
            @Mapping(target = "group", source = "group"),
            @Mapping(target = "url", source = "onlineClassDTO.url")
    })
    OnlineClass convertOnlineClassDtoToEntity(OnlineClassDTO onlineClassDTO, Course course, Teacher teacher, Group group);

    @Mappings({
            @Mapping(target = "course", source = "course"),
            @Mapping(target = "teacher", source = "teacher"),
            @Mapping(target = "group", source = "group"),
            @Mapping(target = "location", source = "location")
    })
    OfflineClass convertOfflineClassDtoToEntity(OfflineClassDTO offlineClassDTO, Course course, Teacher teacher, Group group, Location location);

    @Mapping(target = "group", source = "group")
    Student convertStudentDtoToEntity(StudentDTO studentDTO, Group group);

    @Mapping(target = "studyClasses", source = "studyClasses")
    Teacher convertTeacherDtoToEntity(TeacherDTO teacherDTO, List<StudyClass> studyClasses);
}*/
