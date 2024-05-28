/*
package com.foxminded.university.utils;

import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.dtos.users.StudentDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterDtoToEntity {

    public OnlineClass convertOnlineClassDtoToEntity(OnlineClassDTO onlineClassDTO, Course course, Teacher teacher, Group group) {
        OnlineClass onlineClass = new OnlineClass();
        onlineClass.setId(onlineClassDTO.getId());
        onlineClass.setStartTime(onlineClassDTO.getStartTime());
        onlineClass.setEndTime(onlineClassDTO.getEndTime());
        onlineClass.setCourse(course);
        onlineClass.setTeacher(teacher);
        onlineClass.setGroup(group);
        onlineClass.setUrl(onlineClassDTO.getUrl());
        return onlineClass;
    }

    public OfflineClass convertOfflineClassDtoToEntity(OfflineClassDTO offlineClassDTO, Course course, Teacher teacher, Group group, Location location) {
        OfflineClass offlineClass = new OfflineClass();
        offlineClass.setId(offlineClassDTO.getId());
        offlineClass.setStartTime(offlineClassDTO.getStartTime());
        offlineClass.setEndTime(offlineClassDTO.getEndTime());
        offlineClass.setCourse(course);
        offlineClass.setTeacher(teacher);
        offlineClass.setGroup(group);
        offlineClass.setLocation(location);
        return offlineClass;
    }

    public Student convertStudentDtoToEntity(StudentDTO studentDTO, Group group) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setUsername(studentDTO.getUsername());
        student.setPassword(studentDTO.getPassword());
        student.setRepeatedPassword(studentDTO.getRawPassword());
        student.setGroup(group);
        return student;
    }

    public Teacher convertTeacherDtoToEntity(TeacherDTO teacherDTO, List <StudyClass> studyClasses) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setUsername(teacherDTO.getUsername());
        teacher.setRepeatedPassword(teacherDTO.getRawPassword());
        teacher.setStudyClasses(studyClasses);
        return teacher;
    }
}
*/
