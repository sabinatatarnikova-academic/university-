package com.foxminded.university.utils.mappers;

import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
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
public class GroupMapperImpl implements GroupMapper {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudyClassMapper studyClassMapper;

    @Override
    public GroupDTO toDto(Group group, List<StudentDTO> studentDTO) {
        if ( group == null && studentDTO == null ) {
            return null;
        }

        GroupDTO.GroupDTOBuilder groupDTO = GroupDTO.builder();

        if ( group != null ) {
            groupDTO.id( group.getId() );
            groupDTO.groupName( group.getGroupName() );
            groupDTO.students( studentListToStudentDTOList( group.getStudents() ) );
        }
        groupDTO.studyClasses( studentDTOListToStudyClassDTOList( studentDTO ) );

        return groupDTO.build();
    }

    @Override
    public Group toEntity(GroupDTO groupDTO, List<StudentDTO> studentDTO) {
        if ( groupDTO == null && studentDTO == null ) {
            return null;
        }

        Group.GroupBuilder group = Group.builder();

        if ( groupDTO != null ) {
            group.id( groupDTO.getId() );
            group.groupName( groupDTO.getGroupName() );
            group.students( studentDTOListToStudentList( groupDTO.getStudents() ) );
        }
        group.studyClasses( studentDTOListToStudyClassList( studentDTO ) );

        return group.build();
    }

    protected StudyClassDTO studentDTOToStudyClassDTO(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studentDTO.getId() );
        studyClassDTO.group( studentDTO.getGroup() );

        return studyClassDTO.build();
    }

    protected List<StudyClassDTO> studentDTOListToStudyClassDTOList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClassDTO> list1 = new ArrayList<StudyClassDTO>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( studentDTOToStudyClassDTO( studentDTO ) );
        }

        return list1;
    }

    protected List<StudentDTO> studentListToStudentDTOList(List<Student> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentDTO> list1 = new ArrayList<StudentDTO>( list.size() );
        for ( Student student : list ) {
            list1.add( studentMapper.toDto( student ) );
        }

        return list1;
    }

    protected List<Student> studentDTOListToStudentList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Student> list1 = new ArrayList<Student>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( studentMapper.toEntity( studentDTO ) );
        }

        return list1;
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

    protected StudyClass studentDTOToStudyClass(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        StudyClass.StudyClassBuilder<?, ?> studyClass = StudyClass.builder();

        studyClass.id( studentDTO.getId() );
        studyClass.group( groupDTOToGroup( studentDTO.getGroup() ) );

        return studyClass.build();
    }

    protected List<StudyClass> studentDTOListToStudyClassList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<StudyClass> list1 = new ArrayList<StudyClass>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( studentDTOToStudyClass( studentDTO ) );
        }

        return list1;
    }
}
