package com.foxminded.university.service.studyClasses;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.CourseDTO;
import com.foxminded.university.model.dtos.GroupDTO;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.dtos.users.TeacherDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.classes.DefaultStudyClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
class DefaultStudyClassServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DefaultStudyClassService studyClassService;

    private Group groupA;
    private Group groupB;
    private Course math;
    private Course physics;
    private Teacher alice;
    private Teacher bob;
    private Student charlie;
    private Student diana;
    private Location ics;
    private Location fdu;
    private OnlineClass onlineClass;
    private OfflineClass offlineClass;
    private String username = "username";
    private String password = "$2a$12$IgoUWIHUQ/hmX39dsVixgeIWHK3.vBS8luDFFZRxQSIRlTborOB66";
    private String rawPassword = "password";

    @BeforeEach
    @Transactional
    public void init() {
        entityManager.getEntityManager().createNativeQuery("DELETE FROM classes").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM courses").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM groups").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM locations").executeUpdate();

        groupA = Group.builder()
                .groupName("Group A")
                .build();
        groupB = Group.builder()
                .groupName("Group B")
                .build();
        groupA = entityManager.merge(groupA);
        groupB = entityManager.merge(groupB);

        math = Course.builder()
                .name("Mathematics")
                .build();
        physics = Course.builder()
                .name("Physics")
                .build();
        math = entityManager.merge(math);
        physics = entityManager.merge(physics);

        alice = Teacher.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username(username)
                .password(password)
                .repeatedPassword(rawPassword)
                .build();
        bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username(username)
                .password(password)
                .repeatedPassword(rawPassword)
                .build();
        alice = entityManager.merge(alice);
        bob = entityManager.merge(bob);

        charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .username(username)
                .password(password)
                .repeatedPassword(rawPassword)
                .build();
        diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .username(username)
                .password(password)
                .repeatedPassword(rawPassword)
                .build();
        charlie = entityManager.merge(charlie);
        diana = entityManager.merge(diana);

        ics = Location.builder()
                .department("ICS")
                .classroom("101")
                .build();
        fdu = Location.builder()

                .department("FDU")
                .classroom("102")
                .build();
        ics = entityManager.merge(ics);
        fdu = entityManager.merge(fdu);

        onlineClass = OnlineClass.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(math)
                .teacher(alice)
                .group(groupA)
                .url("http://example.com")
                .build();
        offlineClass = OfflineClass.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 11, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 12, 0))
                .course(physics)
                .teacher(bob)
                .group(groupB)
                .location(ics)
                .build();
        onlineClass = entityManager.merge(onlineClass);
        offlineClass = entityManager.merge(offlineClass);

        entityManager.flush();
    }

    @Test
    void assignTeacherToClassIfClassIsPresent(){
        assertEquals(offlineClass.getTeacher(), bob);
        studyClassService.assignTeacherToClass(alice.getId(), offlineClass.getId());
        assertEquals(offlineClass.getTeacher(), alice);
    }
    @Test
    void assignTeacherToClassIfClassIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> studyClassService.assignTeacherToClass(alice.getId(), "testId"));
    }

    @Test
    void saveOnlineClass() {
        OnlineClassDTO onlineClassToSave = OnlineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(CourseDTO.builder().name("Mathematics").build())
                .teacher(TeacherDTO.builder().firstName("Alice")
                        .lastName("Smith")
                        .username(username)
                        .password(password)
                        .repeatedPassword(rawPassword).build())
                .group(GroupDTO.builder().groupName("Group A").build())
                .url("http://example.com")
                .build();

        studyClassService.saveOnlineClass(onlineClassToSave);
        List<StudyClass> classes = studyClassService.findAllClassesWithPagination(0, 3);
        OnlineClass actual = (OnlineClass) classes.get(2);
        assertEquals(onlineClassToSave.getUrl(), actual.getUrl());
        assertEquals(math, actual.getCourse());
        assertEquals(groupA, actual.getGroup());
        assertEquals(alice, actual.getTeacher());
        assertEquals(onlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(onlineClassToSave.getEndTime(), actual.getEndTime());
    }
/*
    @Test
    void saveOfflineClass() {
        OfflineClassDTO offlineClassToSave = OfflineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .courseId(physics.getId())
                .teacherId(bob.getId())
                .groupId(groupA.getId())
                .locationId(ics.getId())
                .build();

        studyClassService.saveOfflineClass(offlineClassToSave);
        List<StudyClass> offlineClass = studyClassService.findAllClassesWithPagination(0, 3);
        OfflineClass actual = (OfflineClass) offlineClass.get(2);
        assertEquals(ics, actual.getLocation());
        assertEquals(physics, actual.getCourse());
        assertEquals(groupA, actual.getGroup());
        assertEquals(bob, actual.getTeacher());
        assertEquals(offlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(offlineClassToSave.getEndTime(), actual.getEndTime());
    }

    @Test
    void findClassById() {
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(0, 1).get(0);
        String classId = studyClass.getId();
        assertEquals(studyClass, studyClassService.findClassById(classId));
    }

    @Test
    void assertThrowsExceptionIfClassIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById("testId"));
    }

    @Test
    void updateOnlineClass() {
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(0, 1).get(0);
        String classId = studyClass.getId();

        OnlineClassDTO onlineClassToUpdate = OnlineClassDTO.builder()
                .id(classId)
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .courseId(physics.getId())
                .teacherId(bob.getId())
                .groupId(groupA.getId())
                .url("http://foxminded.com")
                .build();

        studyClassService.updateOnlineClass(onlineClassToUpdate);
        List<StudyClass> onlineClass = studyClassService.findAllClassesWithPagination(0, 2);
        OnlineClass actual = (OnlineClass) onlineClass.get(0);
        assertEquals(onlineClassToUpdate.getUrl(), actual.getUrl());
        assertEquals(physics, actual.getCourse());
        assertEquals(groupA, actual.getGroup());
        assertEquals(bob, actual.getTeacher());
        assertEquals(onlineClassToUpdate.getStartTime(), actual.getStartTime());
        assertEquals(onlineClassToUpdate.getEndTime(), actual.getEndTime());
    }

    @Test
    void updateOfflineClass() {
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(0, 2).get(1);
        String classId = studyClass.getId();

        OfflineClassDTO offlineClassToSave = OfflineClassDTO.builder()
                .id(classId)
                .startTime(LocalDateTime.of(2025, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2025, 4, 23, 10, 0))
                .courseId(physics.getId())
                .teacherId(bob.getId())
                .groupId(groupA.getId())
                .locationId(ics.getId())
                .build();

        studyClassService.updateOfflineClass(offlineClassToSave);
        List<StudyClass> studyClasses = studyClassService.findAllClassesWithPagination(0, 3);
        OfflineClass actual = (OfflineClass) studyClasses.get(1);
        assertEquals(ics, actual.getLocation());
        assertEquals(physics, actual.getCourse());
        assertEquals(groupA, actual.getGroup());
        assertEquals(bob, actual.getTeacher());
        assertEquals(offlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(offlineClassToSave.getEndTime(), actual.getEndTime());
    }

    @Test
    void deleteClassById() {
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(0, 2).get(1);
        String classId = studyClass.getId();

        studyClassService.deleteClassById(classId);
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById(classId));
    }

    @Test
    void findAllClassesWithPagination() {
        List<StudyClass> expectedClasses = Arrays.asList(onlineClass, offlineClass);
        List<StudyClass> actualClasses = studyClassService.findAllClassesWithPagination(0,2);
        actualClasses.forEach(studyClass -> studyClass.setId(null));
        assertEquals(expectedClasses, actualClasses);
    }*/
}
