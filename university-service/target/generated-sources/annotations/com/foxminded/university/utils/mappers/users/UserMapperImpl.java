package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.request.GroupDTO;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
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
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public UserResponse toDto(User user) {
        if ( user == null ) {
            return null;
        }

        if (user instanceof Teacher) {
            return teacherToTeacherResponse( (Teacher) user );
        }
        else if (user instanceof Student) {
            return studentToStudentResponse( (Student) user );
        }
        else {
            UserResponse userResponse = new UserResponse();

            userResponse.setId( user.getId() );
            userResponse.setFirstName( user.getFirstName() );
            userResponse.setLastName( user.getLastName() );
            userResponse.setUsername( user.getUsername() );
            userResponse.setPassword( user.getPassword() );
            userResponse.setUserType( user.getUserType() );

            return userResponse;
        }
    }

    @Override
    public User toEntity(UserResponse userResponse) {
        if ( userResponse == null ) {
            return null;
        }

        if (userResponse instanceof TeacherResponse) {
            return teacherResponseToTeacher( (TeacherResponse) userResponse );
        }
        else if (userResponse instanceof StudentResponse) {
            return studentResponseToStudent( (StudentResponse) userResponse );
        }
        else {
            User user = new User();

            user.setId( userResponse.getId() );
            user.setFirstName( userResponse.getFirstName() );
            user.setLastName( userResponse.getLastName() );
            user.setUserType( userResponse.getUserType() );
            user.setUsername( userResponse.getUsername() );
            user.setPassword( userResponse.getPassword() );

            return user;
        }
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

    protected TeacherResponse teacherToTeacherResponse(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponse teacherResponse = new TeacherResponse();

        teacherResponse.setId( teacher.getId() );
        teacherResponse.setFirstName( teacher.getFirstName() );
        teacherResponse.setLastName( teacher.getLastName() );
        teacherResponse.setUsername( teacher.getUsername() );
        teacherResponse.setPassword( teacher.getPassword() );
        teacherResponse.setUserType( teacher.getUserType() );
        teacherResponse.setStudyClasses( studyClassListToStudyClassResponseList( teacher.getStudyClasses() ) );

        return teacherResponse;
    }

    protected GroupDTO groupToGroupDTO(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setId( group.getId() );
        groupDTO.setName( group.getName() );

        return groupDTO;
    }

    protected StudentResponse studentToStudentResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse studentResponse = new StudentResponse();

        studentResponse.setId( student.getId() );
        studentResponse.setFirstName( student.getFirstName() );
        studentResponse.setLastName( student.getLastName() );
        studentResponse.setUsername( student.getUsername() );
        studentResponse.setPassword( student.getPassword() );
        studentResponse.setUserType( student.getUserType() );
        studentResponse.setGroup( groupToGroupDTO( student.getGroup() ) );

        return studentResponse;
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

    protected Teacher teacherResponseToTeacher(TeacherResponse teacherResponse) {
        if ( teacherResponse == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setId( teacherResponse.getId() );
        teacher.setFirstName( teacherResponse.getFirstName() );
        teacher.setLastName( teacherResponse.getLastName() );
        teacher.setUserType( teacherResponse.getUserType() );
        teacher.setUsername( teacherResponse.getUsername() );
        teacher.setPassword( teacherResponse.getPassword() );
        teacher.setStudyClasses( studyClassResponseListToStudyClassList( teacherResponse.getStudyClasses() ) );

        return teacher;
    }

    protected Group groupDTOToGroup(GroupDTO groupDTO) {
        if ( groupDTO == null ) {
            return null;
        }

        Group group = new Group();

        group.setId( groupDTO.getId() );
        group.setName( groupDTO.getName() );

        return group;
    }

    protected Student studentResponseToStudent(StudentResponse studentResponse) {
        if ( studentResponse == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentResponse.getId() );
        student.setFirstName( studentResponse.getFirstName() );
        student.setLastName( studentResponse.getLastName() );
        student.setUserType( studentResponse.getUserType() );
        student.setUsername( studentResponse.getUsername() );
        student.setPassword( studentResponse.getPassword() );
        student.setGroup( groupDTOToGroup( studentResponse.getGroup() ) );

        return student;
    }
}
