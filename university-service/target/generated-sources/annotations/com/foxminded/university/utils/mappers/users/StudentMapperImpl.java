package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.GroupMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public StudentResponse toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse.StudentResponseBuilder<?, ?> studentResponse = StudentResponse.builder();

        studentResponse.id( student.getId() );
        studentResponse.firstName( student.getFirstName() );
        studentResponse.lastName( student.getLastName() );
        studentResponse.username( student.getUsername() );
        studentResponse.password( student.getPassword() );
        studentResponse.userType( student.getUserType() );
        studentResponse.group( groupMapper.toDto( student.getGroup() ) );

        return studentResponse.build();
    }

    @Override
    public Student toEntity(StudentResponse studentResponse) {
        if ( studentResponse == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.id( studentResponse.getId() );
        student.firstName( studentResponse.getFirstName() );
        student.lastName( studentResponse.getLastName() );
        student.userType( studentResponse.getUserType() );
        student.username( studentResponse.getUsername() );
        student.password( studentResponse.getPassword() );
        student.group( groupMapper.toEntity( studentResponse.getGroup() ) );

        return student.build();
    }

    @Override
    public Student toEntity(UserFormRequest studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.id( studentDTO.getId() );
        student.firstName( studentDTO.getFirstName() );
        student.lastName( studentDTO.getLastName() );
        student.userType( studentDTO.getUserType() );
        student.username( studentDTO.getUsername() );
        student.password( studentDTO.getPassword() );
        student.group( groupMapper.toEntity( studentDTO.getGroup() ) );

        return student.build();
    }

    @Override
    public Student toEntity(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.id( userResponse.getId() );
        student.firstName( userResponse.getFirstName() );
        student.lastName( userResponse.getLastName() );
        student.userType( userResponse.getUserType() );
        student.username( userResponse.getUsername() );
        student.password( userResponse.getPassword() );

        return student.build();
    }
}
