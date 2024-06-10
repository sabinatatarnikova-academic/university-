package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.LocationDTO;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-11T01:47:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudyClassMapperImpl implements StudyClassMapper {

    @Override
    public StudyClassDTO toDto(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        StudyClassDTO.StudyClassDTOBuilder<?, ?> studyClassDTO = StudyClassDTO.builder();

        studyClassDTO.id( studyClass.getId() );
        studyClassDTO.startTime( studyClass.getStartTime() );
        studyClassDTO.endTime( studyClass.getEndTime() );
        studyClassDTO.course( courseToCourseDTO( studyClass.getCourse() ) );
        studyClassDTO.group( groupToGroupDTO( studyClass.getGroup() ) );

        return studyClassDTO.build();
    }

    @Override
    public StudyClass toEntity(StudyClassDTO studyClassDTO) {
        if ( studyClassDTO == null ) {
            return null;
        }

        if (studyClassDTO instanceof OnlineClassDTO) {
            return onlineClassDTOToOnlineClass( (OnlineClassDTO) studyClassDTO );
        }
        else if (studyClassDTO instanceof OfflineClassDTO) {
            return offlineClassDTOToOfflineClass( (OfflineClassDTO) studyClassDTO );
        }
        else {
            StudyClass studyClass = new StudyClass();

            studyClass.setId( studyClassDTO.getId() );
            studyClass.setStartTime( studyClassDTO.getStartTime() );
            studyClass.setEndTime( studyClassDTO.getEndTime() );
            studyClass.setCourse( courseDTOToCourse( studyClassDTO.getCourse() ) );
            studyClass.setGroup( groupDTOToGroup( studyClassDTO.getGroup() ) );

            return studyClass;
        }
    }

    protected CourseDTO courseToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO.CourseDTOBuilder courseDTO = CourseDTO.builder();

        courseDTO.id( course.getId() );
        courseDTO.name( course.getName() );

        return courseDTO.build();
    }

    protected GroupDTO groupToGroupDTO(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDTO.GroupDTOBuilder groupDTO = GroupDTO.builder();

        groupDTO.id( group.getId() );
        groupDTO.groupName( group.getGroupName() );

        return groupDTO.build();
    }

    protected Course courseDTOToCourse(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( courseDTO.getId() );
        course.setName( courseDTO.getName() );

        return course;
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

    protected OnlineClass onlineClassDTOToOnlineClass(OnlineClassDTO onlineClassDTO) {
        if ( onlineClassDTO == null ) {
            return null;
        }

        OnlineClass onlineClass = new OnlineClass();

        onlineClass.setId( onlineClassDTO.getId() );
        onlineClass.setStartTime( onlineClassDTO.getStartTime() );
        onlineClass.setEndTime( onlineClassDTO.getEndTime() );
        onlineClass.setCourse( courseDTOToCourse( onlineClassDTO.getCourse() ) );
        onlineClass.setGroup( groupDTOToGroup( onlineClassDTO.getGroup() ) );
        onlineClass.setUrl( onlineClassDTO.getUrl() );

        return onlineClass;
    }

    protected Location locationDTOToLocation(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        Location location = new Location();

        location.setId( locationDTO.getId() );
        location.setDepartment( locationDTO.getDepartment() );
        location.setClassroom( locationDTO.getClassroom() );

        return location;
    }

    protected OfflineClass offlineClassDTOToOfflineClass(OfflineClassDTO offlineClassDTO) {
        if ( offlineClassDTO == null ) {
            return null;
        }

        OfflineClass offlineClass = new OfflineClass();

        offlineClass.setId( offlineClassDTO.getId() );
        offlineClass.setStartTime( offlineClassDTO.getStartTime() );
        offlineClass.setEndTime( offlineClassDTO.getEndTime() );
        offlineClass.setCourse( courseDTOToCourse( offlineClassDTO.getCourse() ) );
        offlineClass.setGroup( groupDTOToGroup( offlineClassDTO.getGroup() ) );
        offlineClass.setLocation( locationDTOToLocation( offlineClassDTO.getLocation() ) );

        return offlineClass;
    }
}
