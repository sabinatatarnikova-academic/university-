package com.foxminded.university.utils.mappers;

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
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-28T14:21:09+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public CourseDTO toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.CourseDTOBuilder courseDTO = CourseDTO.builder();

        courseDTO.id( course.getId() );
        courseDTO.name( course.getName() );
        courseDTO.studyClasses( studyClassListToStudyClassDTOList( course.getStudyClasses() ) );

        return courseDTO.build();
    }

    @Override
    public Course toEntity(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.id( courseDTO.getId() );
        course.name( courseDTO.getName() );
        course.studyClasses( studyClassDTOListToStudyClassList( courseDTO.getStudyClasses() ) );

        return course.build();
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

    protected StudyClassDTO studyClassToStudyClassDTO(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studyClass.getId() );
        studyClassDTO.startTime( studyClass.getStartTime() );
        studyClassDTO.endTime( studyClass.getEndTime() );
        studyClassDTO.course( toDto( studyClass.getCourse() ) );
        studyClassDTO.teacher( teacherToTeacherDTO( studyClass.getTeacher() ) );
        studyClassDTO.group( groupToGroupDTO( studyClass.getGroup() ) );

        return studyClassDTO.build();
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
