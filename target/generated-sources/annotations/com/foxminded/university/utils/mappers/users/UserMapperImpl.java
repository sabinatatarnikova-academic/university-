package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
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
public class UserMapperImpl implements UserMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        if (user instanceof Teacher) {
            return teacherToTeacherDTO( (Teacher) user );
        }
        else if (user instanceof Student) {
            return studentToStudentDTO( (Student) user );
        }
        else {
            UserDTO userDTO = new UserDTO();

            userDTO.setId( user.getId() );
            userDTO.setFirstName( user.getFirstName() );
            userDTO.setLastName( user.getLastName() );
            userDTO.setUsername( user.getUsername() );
            userDTO.setPassword( user.getPassword() );
            userDTO.setUserType( user.getUserType() );

            return userDTO;
        }
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        if (userDTO instanceof TeacherDTO) {
            return teacherDTOToTeacher( (TeacherDTO) userDTO );
        }
        else if (userDTO instanceof StudentDTO) {
            return studentDTOToStudent( (StudentDTO) userDTO );
        }
        else {
            User user = new User();

            user.setId( userDTO.getId() );
            user.setFirstName( userDTO.getFirstName() );
            user.setLastName( userDTO.getLastName() );
            user.setUserType( userDTO.getUserType() );
            user.setUsername( userDTO.getUsername() );
            user.setPassword( userDTO.getPassword() );

            return user;
        }
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

    protected TeacherDTO teacherToTeacherDTO(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setId( teacher.getId() );
        teacherDTO.setFirstName( teacher.getFirstName() );
        teacherDTO.setLastName( teacher.getLastName() );
        teacherDTO.setUsername( teacher.getUsername() );
        teacherDTO.setPassword( teacher.getPassword() );
        teacherDTO.setUserType( teacher.getUserType() );
        teacherDTO.setStudyClasses( studyClassListToStudyClassDTOList( teacher.getStudyClasses() ) );

        return teacherDTO;
    }

    protected GroupDTO groupToGroupDTO(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setId( group.getId() );
        groupDTO.setGroupName( group.getGroupName() );

        return groupDTO;
    }

    protected StudentDTO studentToStudentDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId( student.getId() );
        studentDTO.setFirstName( student.getFirstName() );
        studentDTO.setLastName( student.getLastName() );
        studentDTO.setUsername( student.getUsername() );
        studentDTO.setPassword( student.getPassword() );
        studentDTO.setUserType( student.getUserType() );
        studentDTO.setGroup( groupToGroupDTO( student.getGroup() ) );

        return studentDTO;
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

    protected Teacher teacherDTOToTeacher(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setId( teacherDTO.getId() );
        teacher.setFirstName( teacherDTO.getFirstName() );
        teacher.setLastName( teacherDTO.getLastName() );
        teacher.setUserType( teacherDTO.getUserType() );
        teacher.setUsername( teacherDTO.getUsername() );
        teacher.setPassword( teacherDTO.getPassword() );
        teacher.setStudyClasses( studyClassDTOListToStudyClassList( teacherDTO.getStudyClasses() ) );

        return teacher;
    }

    protected Group groupDTOToGroup(GroupDTO groupDTO) {
        if ( groupDTO == null ) {
            return null;
        }

        Group group = new Group();

        group.setId( groupDTO.getId() );
        group.setGroupName( groupDTO.getGroupName() );

        return group;
    }

    protected Student studentDTOToStudent(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDTO.getId() );
        student.setFirstName( studentDTO.getFirstName() );
        student.setLastName( studentDTO.getLastName() );
        student.setUserType( studentDTO.getUserType() );
        student.setUsername( studentDTO.getUsername() );
        student.setPassword( studentDTO.getPassword() );
        student.setGroup( groupDTOToGroup( studentDTO.getGroup() ) );

        return student;
    }
}
