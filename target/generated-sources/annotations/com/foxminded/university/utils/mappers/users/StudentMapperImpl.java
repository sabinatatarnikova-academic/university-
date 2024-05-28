package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.GroupMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-31T18:55:49+0300",
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

        StudentDTO.StudentDTOBuilder studentDTO = StudentDTO.builder();

        studentDTO.id( student.getId() );
        studentDTO.firstName( student.getFirstName() );
        studentDTO.lastName( student.getLastName() );
        studentDTO.username( student.getUsername() );
        studentDTO.password( student.getPassword() );
        studentDTO.repeatedPassword( student.getRepeatedPassword() );
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
        student.username( studentDTO.getUsername() );
        student.password( studentDTO.getPassword() );
        student.repeatedPassword( studentDTO.getRepeatedPassword() );
        student.group( groupMapper.toEntity( studentDTO.getGroup() ) );

        return student.build();
    }
}
