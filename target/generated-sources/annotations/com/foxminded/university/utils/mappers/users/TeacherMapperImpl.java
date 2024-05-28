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
    date = "2024-05-28T14:16:29+0300",
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

        TeacherDTO.TeacherDTOBuilder teacherDTO = TeacherDTO.builder();

        teacherDTO.repeatedPassword( teacher.getRepeatedPassword() );
        teacherDTO.id( teacher.getId() );
        teacherDTO.firstName( teacher.getFirstName() );
        teacherDTO.lastName( teacher.getLastName() );
        teacherDTO.username( teacher.getUsername() );
        teacherDTO.password( teacher.getPassword() );
        teacherDTO.studyClasses( studyClassListToStudyClassDTOSet( teacher.getStudyClasses() ) );

        return teacherDTO.build();
    }

    @Override
    public Teacher toEntity(TeacherDTO teacherDTO) {
        if ( teacherDTO == null ) {
            return null;
        }

        Teacher.TeacherBuilder<?, ?> teacher = Teacher.builder();

        teacher.repeatedPassword( teacherDTO.getRepeatedPassword() );
        teacher.id( teacherDTO.getId() );
        teacher.firstName( teacherDTO.getFirstName() );
        teacher.lastName( teacherDTO.getLastName() );
        teacher.username( teacherDTO.getUsername() );
        teacher.password( teacherDTO.getPassword() );
        teacher.studyClasses( studyClassDTOSetToStudyClassList( teacherDTO.getStudyClasses() ) );

        return teacher.build();
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
        studyClassDTO.course( courseToCourseDTO( studyClass.getCourse() ) );
        studyClassDTO.teacher( toDto( studyClass.getTeacher() ) );
        studyClassDTO.group( groupToGroupDTO( studyClass.getGroup() ) );

        return studyClassDTO.build();
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

    protected List<StudyClass> studyClassDTOSetToStudyClassList(Set<StudyClassDTO> set) {
        if ( set == null ) {
            return null;
        }

        List<StudyClass> list = new ArrayList<StudyClass>( set.size() );
        for ( StudyClassDTO studyClassDTO : set ) {
            list.add( studyClassMapper.toEntity( studyClassDTO ) );
        }

        return list;
    }
}
