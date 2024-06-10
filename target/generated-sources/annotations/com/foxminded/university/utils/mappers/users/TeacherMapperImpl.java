package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T01:47:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public TeacherDTO toDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDTO.TeacherDTOBuilder<?, ?> teacherDTO = TeacherDTO.builder();

        teacherDTO.studyClasses( studyClassListToStudyClassDTOList( teacher.getStudyClasses() ) );
        teacherDTO.id( teacher.getId() );
        teacherDTO.firstName( teacher.getFirstName() );
        teacherDTO.lastName( teacher.getLastName() );
        teacherDTO.username( teacher.getUsername() );
        teacherDTO.password( teacher.getPassword() );
        teacherDTO.userType( teacher.getUserType() );

        return teacherDTO.build();
    }

    @Override
    public TeacherDTO toDto(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        TeacherDTO.TeacherDTOBuilder<?, ?> teacherDTO = TeacherDTO.builder();

        teacherDTO.id( userDTO.getId() );
        teacherDTO.firstName( userDTO.getFirstName() );
        teacherDTO.lastName( userDTO.getLastName() );
        teacherDTO.username( userDTO.getUsername() );
        teacherDTO.password( userDTO.getPassword() );
        teacherDTO.userType( userDTO.getUserType() );

        return teacherDTO.build();
    }

    @Override
    public Teacher toEntity(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.studyClasses( studyClassDTOListToStudyClassList( teacherDTO.getStudyClasses() ) );
        teacher.id( teacherDTO.getId() );
        teacher.firstName( teacherDTO.getFirstName() );
        teacher.lastName( teacherDTO.getLastName() );
        teacher.userType( teacherDTO.getUserType() );
        teacher.username( teacherDTO.getUsername() );
        teacher.password( teacherDTO.getPassword() );

        return teacher.build();
    }

    @Override
    public Teacher toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.id( userDTO.getId() );
        teacher.firstName( userDTO.getFirstName() );
        teacher.lastName( userDTO.getLastName() );
        teacher.userType( userDTO.getUserType() );
        teacher.username( userDTO.getUsername() );
        teacher.password( userDTO.getPassword() );

        return teacher.build();
    }

    @Override
    public Teacher toEntity(UserFormDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.studyClasses( mapStudyClasses( teacherDTO.getStudyClasses() ) );
        teacher.id( teacherDTO.getId() );
        teacher.firstName( teacherDTO.getFirstName() );
        teacher.lastName( teacherDTO.getLastName() );
        teacher.userType( teacherDTO.getUserType() );
        teacher.username( teacherDTO.getUsername() );
        teacher.password( teacherDTO.getPassword() );

        return teacher.build();
    }

    protected List<StudyClassDTO> studyClassListToStudyClassDTOList(List<StudyClass> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClassDTO> list1 = new ArrayList<StudyClassDTO>( list.size() );
        for ( StudyClass studyClass : list ) {
            list1.add( studyClassMapper.toDto( studyClass ) );
        }

        return list1;
    }

    protected List<StudyClass> studyClassDTOListToStudyClassList(List<StudyClassDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClass> list1 = new ArrayList<StudyClass>( list.size() );
        for ( StudyClassDTO studyClassDTO : list ) {
            list1.add( studyClassMapper.toEntity( studyClassDTO ) );
        }

        return list1;
    }
}
