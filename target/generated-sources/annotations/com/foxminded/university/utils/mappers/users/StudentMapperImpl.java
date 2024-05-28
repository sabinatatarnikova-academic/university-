package com.foxminded.university.utils.mappers.users;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T15:05:58+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

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
        studentDTO.group( groupToGroupDTO( student.getGroup() ) );

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
        student.group( groupDTOToGroup( studentDTO.getGroup() ) );

        return student.build();
    }

    protected List<StudentDTO> studentListToStudentDTOList(List<Student> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentDTO> list1 = new ArrayList<StudentDTO>( list.size() );
        for ( Student student : list ) {
            list1.add( toDto( student ) );
        }

        return list1;
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

    protected CourseDTO courseToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.CourseDTOBuilder courseDTO = CourseDTO.builder();

        courseDTO.id( course.getId() );
        courseDTO.name( course.getName() );
        courseDTO.studyClasses( studyClassListToStudyClassDTOList( course.getStudyClasses() ) );

        return courseDTO.build();
    }

    protected Set<StudyClassDTO> studyClassListToStudyClassDTOSet(List<StudyClass> list) {
        if ( list == null ) {
            return null;
        }

        Set<StudyClassDTO> set = new LinkedHashSet<StudyClassDTO>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( StudyClass studyClass : list ) {
            set.add( studyClassToStudyClassDTO( studyClass ) );
        }

        return set;
    }

    protected TeacherDTO teacherToTeacherDTO(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDTO.TeacherDTOBuilder teacherDTO = TeacherDTO.builder();

        teacherDTO.id( teacher.getId() );
        teacherDTO.firstName( teacher.getFirstName() );
        teacherDTO.lastName( teacher.getLastName() );
        teacherDTO.username( teacher.getUsername() );
        teacherDTO.password( teacher.getPassword() );
        teacherDTO.repeatedPassword( teacher.getRepeatedPassword() );
        teacherDTO.studyClasses( studyClassListToStudyClassDTOSet( teacher.getStudyClasses() ) );

        return teacherDTO.build();
    }

    protected StudyClassDTO studyClassToStudyClassDTO(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studyClass.getId() );
        studyClassDTO.startTime( studyClass.getStartTime() );
        studyClassDTO.endTime( studyClass.getEndTime() );
        studyClassDTO.course( courseToCourseDTO( studyClass.getCourse() ) );
        studyClassDTO.teacher( teacherToTeacherDTO( studyClass.getTeacher() ) );
        studyClassDTO.group( groupToGroupDTO( studyClass.getGroup() ) );

        return studyClassDTO.build();
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

    protected List<Student> studentDTOListToStudentList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Student> list1 = new ArrayList<Student>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( toEntity( studentDTO ) );
        }

        return list1;
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

    protected Course courseDTOToCourse(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDTO.getId() );
        course.name( courseDTO.getName() );
        course.studyClasses( studyClassDTOListToStudyClassList( courseDTO.getStudyClasses() ) );

        return course.build();
    }

    protected List<StudyClass> studyClassDTOSetToStudyClassList(Set<StudyClassDTO> set) {
        if ( set == null ) {
            return null;
        }

        List<StudyClass> list = new ArrayList<StudyClass>( set.size() );
        for ( StudyClassDTO studyClassDTO : set ) {
            list.add( studyClassDTOToStudyClass( studyClassDTO ) );
        }

        return list;
    }

    protected Teacher teacherDTOToTeacher(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.id( teacherDTO.getId() );
        teacher.firstName( teacherDTO.getFirstName() );
        teacher.lastName( teacherDTO.getLastName() );
        teacher.username( teacherDTO.getUsername() );
        teacher.password( teacherDTO.getPassword() );
        teacher.repeatedPassword( teacherDTO.getRepeatedPassword() );
        teacher.studyClasses( studyClassDTOSetToStudyClassList( teacherDTO.getStudyClasses() ) );

        return teacher.build();
    }

    protected StudyClass studyClassDTOToStudyClass(StudyClassDTO studyClassDTO) {
        if ( studyClassDTO == null ) {
            return null;
        }

        StudyClass.StudyClassBuilder<?, ?> studyClass = StudyClass.builder();

        studyClass.id( studyClassDTO.getId() );
        studyClass.startTime( studyClassDTO.getStartTime() );
        studyClass.endTime( studyClassDTO.getEndTime() );
        studyClass.course( courseDTOToCourse( studyClassDTO.getCourse() ) );
        studyClass.teacher( teacherDTOToTeacher( studyClassDTO.getTeacher() ) );
        studyClass.group( groupDTOToGroup( studyClassDTO.getGroup() ) );

        return studyClass.build();
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
