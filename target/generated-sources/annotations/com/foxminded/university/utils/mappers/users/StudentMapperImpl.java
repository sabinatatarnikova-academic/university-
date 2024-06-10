package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.GroupMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T01:47:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public StudentDTO toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO.StudentDTOBuilder<?, ?> studentDTO = StudentDTO.builder();

        studentDTO.id( student.getId() );
        studentDTO.firstName( student.getFirstName() );
        studentDTO.lastName( student.getLastName() );
        studentDTO.username( student.getUsername() );
        studentDTO.password( student.getPassword() );
        studentDTO.userType( student.getUserType() );
        studentDTO.group( groupMapper.toDto( student.getGroup() ) );

        return studentDTO.build();
    }

    @Override
    public Student toEntity(StudentDTO studentDTO) {
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
    public Student toEntity(UserFormDTO studentDTO) {
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
    public Student toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        Student.StudentBuilder<?, ?> student = Student.builder();

        student.id( userDTO.getId() );
        student.firstName( userDTO.getFirstName() );
        student.lastName( userDTO.getLastName() );
        student.userType( userDTO.getUserType() );
        student.username( userDTO.getUsername() );
        student.password( userDTO.getPassword() );

        return student.build();
    }
}
