package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
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
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TeacherMapperImpl implements TeacherMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public TeacherResponse toDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse.TeacherResponseBuilder<?, ?> teacherResponse = TeacherResponse.builder();

        teacherResponse.studyClasses( studyClassListToStudyClassResponseList( teacher.getStudyClasses() ) );
        teacherResponse.id( teacher.getId() );
        teacherResponse.firstName( teacher.getFirstName() );
        teacherResponse.lastName( teacher.getLastName() );
        teacherResponse.username( teacher.getUsername() );
        teacherResponse.password( teacher.getPassword() );
        teacherResponse.userType( teacher.getUserType() );

        return teacherResponse.build();
    }

    @Override
    public TeacherResponse toDto(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        TeacherResponse.TeacherResponseBuilder<?, ?> teacherResponse = TeacherResponse.builder();

        teacherResponse.id( userResponse.getId() );
        teacherResponse.firstName( userResponse.getFirstName() );
        teacherResponse.lastName( userResponse.getLastName() );
        teacherResponse.username( userResponse.getUsername() );
        teacherResponse.password( userResponse.getPassword() );
        teacherResponse.userType( userResponse.getUserType() );

        return teacherResponse.build();
    }

    @Override
    public Teacher toEntity(TeacherResponse teacherResponse) {
        if ( teacherResponse == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.studyClasses( studyClassResponseListToStudyClassList( teacherResponse.getStudyClasses() ) );
        teacher.id( teacherResponse.getId() );
        teacher.firstName( teacherResponse.getFirstName() );
        teacher.lastName( teacherResponse.getLastName() );
        teacher.userType( teacherResponse.getUserType() );
        teacher.username( teacherResponse.getUsername() );
        teacher.password( teacherResponse.getPassword() );

        return teacher.build();
    }

    @Override
    public Teacher toEntity(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.id( userResponse.getId() );
        teacher.firstName( userResponse.getFirstName() );
        teacher.lastName( userResponse.getLastName() );
        teacher.userType( userResponse.getUserType() );
        teacher.username( userResponse.getUsername() );
        teacher.password( userResponse.getPassword() );

        return teacher.build();
    }

    @Override
    public Teacher toEntity(UserFormRequest teacherDTO) {
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

    protected List<StudyClassResponse> studyClassListToStudyClassResponseList(List<StudyClass> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClassResponse> list1 = new ArrayList<StudyClassResponse>( list.size() );
        for ( StudyClass studyClass : list ) {
            list1.add( studyClassMapper.toDto( studyClass ) );
        }

        return list1;
    }

    protected List<StudyClass> studyClassResponseListToStudyClassList(List<StudyClassResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClass> list1 = new ArrayList<StudyClass>( list.size() );
        for ( StudyClassResponse studyClassResponse : list ) {
            list1.add( studyClassMapper.toEntity( studyClassResponse ) );
        }

        return list1;
    }
}
