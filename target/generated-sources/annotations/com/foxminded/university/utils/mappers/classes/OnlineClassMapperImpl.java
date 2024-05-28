package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T14:16:29+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OnlineClassMapperImpl implements OnlineClassMapper {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public OnlineClassDTO toDto(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }

        OnlineClassDTO.OnlineClassDTOBuilder onlineClassDTO = OnlineClassDTO.builder();

        onlineClassDTO.id( onlineClass.getId() );
        onlineClassDTO.startTime( onlineClass.getStartTime() );
        onlineClassDTO.endTime( onlineClass.getEndTime() );
        onlineClassDTO.course( courseMapper.toDto( onlineClass.getCourse() ) );
        onlineClassDTO.teacher( teacherMapper.toDto( onlineClass.getTeacher() ) );
        onlineClassDTO.group( groupToGroupDTO( onlineClass.getGroup() ) );
        onlineClassDTO.url( onlineClass.getUrl() );

        return onlineClassDTO.build();
    }

    @Override
    public OnlineClass toEntity(OnlineClassDTO onlineClassDTO) {
        if ( onlineClassDTO == null ) {
            return null;
        }

        OnlineClass.OnlineClassBuilder<?, ?> onlineClass = OnlineClass.builder();

        onlineClass.id( onlineClassDTO.getId() );
        onlineClass.startTime( onlineClassDTO.getStartTime() );
        onlineClass.endTime( onlineClassDTO.getEndTime() );
        onlineClass.course( courseMapper.toEntity( onlineClassDTO.getCourse() ) );
        onlineClass.teacher( teacherMapper.toEntity( onlineClassDTO.getTeacher() ) );
        onlineClass.group( groupDTOToGroup( onlineClassDTO.getGroup() ) );
        onlineClass.url( onlineClassDTO.getUrl() );

        return onlineClass.build();
    }

    protected StudentDTO studentToStudentDTO(Student student) {
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
        studentDTO.group( groupToGroupDTO( student.getGroup() ) );

        return studentDTO.build();
    }

    protected List<StudentDTO> studentListToStudentDTOList(List<Student> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentDTO> list1 = new ArrayList<StudentDTO>( list.size() );
        for ( Student student : list ) {
            list1.add( studentToStudentDTO( student ) );
        }

        return list1;
    }

    protected StudyClassDTO studyClassToStudyClassDTO(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studyClass.getId() );
        studyClassDTO.startTime( studyClass.getStartTime() );
        studyClassDTO.endTime( studyClass.getEndTime() );
        studyClassDTO.course( courseMapper.toDto( studyClass.getCourse() ) );
        studyClassDTO.teacher( teacherMapper.toDto( studyClass.getTeacher() ) );
        studyClassDTO.group( groupToGroupDTO( studyClass.getGroup() ) );

        return studyClassDTO.build();
    }

    protected List<StudyClassDTO> studyClassListToStudyClassDTOList(List<StudyClass> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClassDTO> list1 = new ArrayList<StudyClassDTO>( list.size() );
        for ( StudyClass studyClass : list ) {
            list1.add( studyClassToStudyClassDTO( studyClass ) );
        }

        return list1;
    }

    protected GroupDTO groupToGroupDTO(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDTO.GroupDTOBuilder groupDTO = GroupDTO.builder();

        groupDTO.id( group.getId() );
        groupDTO.groupName( group.getGroupName() );
        groupDTO.students( studentListToStudentDTOList( group.getStudents() ) );
        groupDTO.studyClasses( studyClassListToStudyClassDTOList( group.getStudyClasses() ) );

        return groupDTO.build();
    }

    protected Student studentDTOToStudent(StudentDTO studentDTO) {
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
        student.group( groupDTOToGroup( studentDTO.getGroup() ) );

        return student.build();
    }

    protected List<Student> studentDTOListToStudentList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Student> list1 = new ArrayList<Student>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( studentDTOToStudent( studentDTO ) );
        }

        return list1;
    }

    protected StudyClass studyClassDTOToStudyClass(StudyClassDTO studyClassDTO) {
        if ( studyClassDTO == null ) {
            return null;
        }

        StudyClass.StudyClassBuilder<?, ?> studyClass = StudyClass.builder();

        studyClass.id( studyClassDTO.getId() );
        studyClass.startTime( studyClassDTO.getStartTime() );
        studyClass.endTime( studyClassDTO.getEndTime() );
        studyClass.course( courseMapper.toEntity( studyClassDTO.getCourse() ) );
        studyClass.teacher( teacherMapper.toEntity( studyClassDTO.getTeacher() ) );
        studyClass.group( groupDTOToGroup( studyClassDTO.getGroup() ) );

        return studyClass.build();
    }

    protected List<StudyClass> studyClassDTOListToStudyClassList(List<StudyClassDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClass> list1 = new ArrayList<StudyClass>( list.size() );
        for ( StudyClassDTO studyClassDTO : list ) {
            list1.add( studyClassDTOToStudyClass( studyClassDTO ) );
        }

        return list1;
    }

    protected Group groupDTOToGroup(GroupDTO groupDTO) {
        if ( groupDTO == null ) {
            return null;
        }

        Group.GroupBuilder group = Group.builder();

        group.id( groupDTO.getId() );
        group.groupName( groupDTO.getGroupName() );
        group.students( studentDTOListToStudentList( groupDTO.getStudents() ) );
        group.studyClasses( studyClassDTOListToStudyClassList( groupDTO.getStudyClasses() ) );

        return group.build();
    }
}
