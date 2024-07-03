package com.foxminded.university.utils.mappers.classes;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-03T07:19:34+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class StudyClassMapperImpl implements StudyClassMapper {

    @Override
    public StudyClassResponse toDto(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }

        if (studyClass instanceof OnlineClass) {
            return onlineClassToOnlineClassResponse( (OnlineClass) studyClass );
        }
        else if (studyClass instanceof OfflineClass) {
            return offlineClassToOfflineClassResponse( (OfflineClass) studyClass );
        }
        else {
            StudyClassResponse studyClassResponse = new StudyClassResponse();

            studyClassResponse.setCourseName( studyClassCourseName( studyClass ) );
            studyClassResponse.setGroupName( studyClassGroupName( studyClass ) );
            studyClassResponse.setTeacherFirstName( studyClassTeacherFirstName( studyClass ) );
            studyClassResponse.setTeacherLastName( studyClassTeacherLastName( studyClass ) );
            studyClassResponse.setId( studyClass.getId() );
            studyClassResponse.setStartTime( studyClass.getStartTime() );
            studyClassResponse.setEndTime( studyClass.getEndTime() );
            studyClassResponse.setClassType( studyClass.getClassType() );

            return studyClassResponse;
        }
    }

    @Override
    public StudyClass toEntity(StudyClassResponse studyClassResponse) {
        if ( studyClassResponse == null ) {
            return null;
        }

        if (studyClassResponse instanceof OnlineClassResponse) {
            return onlineClassResponseToOnlineClass( (OnlineClassResponse) studyClassResponse );
        }
        else if (studyClassResponse instanceof OfflineClassResponse) {
            return offlineClassResponseToOfflineClass( (OfflineClassResponse) studyClassResponse );
        }
        else {
            StudyClass studyClass = new StudyClass();

            studyClass.setCourse( studyClassResponseToCourse( studyClassResponse ) );
            studyClass.setGroup( studyClassResponseToGroup( studyClassResponse ) );
            studyClass.setTeacher( studyClassResponseToTeacher( studyClassResponse ) );
            studyClass.setId( studyClassResponse.getId() );
            studyClass.setStartTime( studyClassResponse.getStartTime() );
            studyClass.setEndTime( studyClassResponse.getEndTime() );
            studyClass.setClassType( studyClassResponse.getClassType() );

            return studyClass;
        }
    }

    private String studyClassCourseName(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }
        Course course = studyClass.getCourse();
        if ( course == null ) {
            return null;
        }
        String name = course.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String studyClassGroupName(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }
        Group group = studyClass.getGroup();
        if ( group == null ) {
            return null;
        }
        String name = group.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String studyClassTeacherFirstName(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }
        Teacher teacher = studyClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String firstName = teacher.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String studyClassTeacherLastName(StudyClass studyClass) {
        if ( studyClass == null ) {
            return null;
        }
        Teacher teacher = studyClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String lastName = teacher.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String onlineClassCourseName(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }
        Course course = onlineClass.getCourse();
        if ( course == null ) {
            return null;
        }
        String name = course.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String onlineClassGroupName(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }
        Group group = onlineClass.getGroup();
        if ( group == null ) {
            return null;
        }
        String name = group.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String onlineClassTeacherFirstName(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }
        Teacher teacher = onlineClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String firstName = teacher.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String onlineClassTeacherLastName(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }
        Teacher teacher = onlineClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String lastName = teacher.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    protected OnlineClassResponse onlineClassToOnlineClassResponse(OnlineClass onlineClass) {
        if ( onlineClass == null ) {
            return null;
        }

        OnlineClassResponse onlineClassResponse = new OnlineClassResponse();

        onlineClassResponse.setCourseName( onlineClassCourseName( onlineClass ) );
        onlineClassResponse.setGroupName( onlineClassGroupName( onlineClass ) );
        onlineClassResponse.setTeacherFirstName( onlineClassTeacherFirstName( onlineClass ) );
        onlineClassResponse.setTeacherLastName( onlineClassTeacherLastName( onlineClass ) );
        onlineClassResponse.setId( onlineClass.getId() );
        onlineClassResponse.setStartTime( onlineClass.getStartTime() );
        onlineClassResponse.setEndTime( onlineClass.getEndTime() );
        onlineClassResponse.setClassType( onlineClass.getClassType() );
        onlineClassResponse.setUrl( onlineClass.getUrl() );

        return onlineClassResponse;
    }

    private String offlineClassCourseName(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }
        Course course = offlineClass.getCourse();
        if ( course == null ) {
            return null;
        }
        String name = course.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String offlineClassGroupName(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }
        Group group = offlineClass.getGroup();
        if ( group == null ) {
            return null;
        }
        String name = group.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String offlineClassTeacherFirstName(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }
        Teacher teacher = offlineClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String firstName = teacher.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String offlineClassTeacherLastName(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }
        Teacher teacher = offlineClass.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String lastName = teacher.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    protected LocationDTO locationToLocationDTO(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( location.getId() );
        locationDTO.setDepartment( location.getDepartment() );
        locationDTO.setClassroom( location.getClassroom() );

        return locationDTO;
    }

    protected OfflineClassResponse offlineClassToOfflineClassResponse(OfflineClass offlineClass) {
        if ( offlineClass == null ) {
            return null;
        }

        OfflineClassResponse offlineClassResponse = new OfflineClassResponse();

        offlineClassResponse.setCourseName( offlineClassCourseName( offlineClass ) );
        offlineClassResponse.setGroupName( offlineClassGroupName( offlineClass ) );
        offlineClassResponse.setTeacherFirstName( offlineClassTeacherFirstName( offlineClass ) );
        offlineClassResponse.setTeacherLastName( offlineClassTeacherLastName( offlineClass ) );
        offlineClassResponse.setId( offlineClass.getId() );
        offlineClassResponse.setStartTime( offlineClass.getStartTime() );
        offlineClassResponse.setEndTime( offlineClass.getEndTime() );
        offlineClassResponse.setClassType( offlineClass.getClassType() );
        offlineClassResponse.setLocation( locationToLocationDTO( offlineClass.getLocation() ) );

        return offlineClassResponse;
    }

    protected Course studyClassResponseToCourse(StudyClassResponse studyClassResponse) {
        if ( studyClassResponse == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( studyClassResponse.getCourseName() );

        return course;
    }

    protected Group studyClassResponseToGroup(StudyClassResponse studyClassResponse) {
        if ( studyClassResponse == null ) {
            return null;
        }

        Group group = new Group();

        group.setName( studyClassResponse.getGroupName() );

        return group;
    }

    protected Teacher studyClassResponseToTeacher(StudyClassResponse studyClassResponse) {
        if ( studyClassResponse == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setFirstName( studyClassResponse.getTeacherFirstName() );
        teacher.setLastName( studyClassResponse.getTeacherLastName() );

        return teacher;
    }

    protected Course onlineClassResponseToCourse(OnlineClassResponse onlineClassResponse) {
        if ( onlineClassResponse == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( onlineClassResponse.getCourseName() );

        return course;
    }

    protected Group onlineClassResponseToGroup(OnlineClassResponse onlineClassResponse) {
        if ( onlineClassResponse == null ) {
            return null;
        }

        Group group = new Group();

        group.setName( onlineClassResponse.getGroupName() );

        return group;
    }

    protected Teacher onlineClassResponseToTeacher(OnlineClassResponse onlineClassResponse) {
        if ( onlineClassResponse == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setFirstName( onlineClassResponse.getTeacherFirstName() );
        teacher.setLastName( onlineClassResponse.getTeacherLastName() );

        return teacher;
    }

    protected OnlineClass onlineClassResponseToOnlineClass(OnlineClassResponse onlineClassResponse) {
        if ( onlineClassResponse == null ) {
            return null;
        }

        OnlineClass onlineClass = new OnlineClass();

        onlineClass.setCourse( onlineClassResponseToCourse( onlineClassResponse ) );
        onlineClass.setGroup( onlineClassResponseToGroup( onlineClassResponse ) );
        onlineClass.setTeacher( onlineClassResponseToTeacher( onlineClassResponse ) );
        onlineClass.setId( onlineClassResponse.getId() );
        onlineClass.setStartTime( onlineClassResponse.getStartTime() );
        onlineClass.setEndTime( onlineClassResponse.getEndTime() );
        onlineClass.setClassType( onlineClassResponse.getClassType() );
        onlineClass.setUrl( onlineClassResponse.getUrl() );

        return onlineClass;
    }

    protected Course offlineClassResponseToCourse(OfflineClassResponse offlineClassResponse) {
        if ( offlineClassResponse == null ) {
            return null;
        }

        Course course = new Course();

        course.setName( offlineClassResponse.getCourseName() );

        return course;
    }

    protected Group offlineClassResponseToGroup(OfflineClassResponse offlineClassResponse) {
        if ( offlineClassResponse == null ) {
            return null;
        }

        Group group = new Group();

        group.setName( offlineClassResponse.getGroupName() );

        return group;
    }

    protected Teacher offlineClassResponseToTeacher(OfflineClassResponse offlineClassResponse) {
        if ( offlineClassResponse == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setFirstName( offlineClassResponse.getTeacherFirstName() );
        teacher.setLastName( offlineClassResponse.getTeacherLastName() );

        return teacher;
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

    protected OfflineClass offlineClassResponseToOfflineClass(OfflineClassResponse offlineClassResponse) {
        if ( offlineClassResponse == null ) {
            return null;
        }

        OfflineClass offlineClass = new OfflineClass();

        offlineClass.setCourse( offlineClassResponseToCourse( offlineClassResponse ) );
        offlineClass.setGroup( offlineClassResponseToGroup( offlineClassResponse ) );
        offlineClass.setTeacher( offlineClassResponseToTeacher( offlineClassResponse ) );
        offlineClass.setId( offlineClassResponse.getId() );
        offlineClass.setStartTime( offlineClassResponse.getStartTime() );
        offlineClass.setEndTime( offlineClassResponse.getEndTime() );
        offlineClass.setClassType( offlineClassResponse.getClassType() );
        offlineClass.setLocation( locationDTOToLocation( offlineClassResponse.getLocation() ) );

        return offlineClass;
    }
}
