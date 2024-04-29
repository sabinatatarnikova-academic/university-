package com.foxminded.university.service.studyClasses;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Location;
import com.foxminded.university.model.classes.OfflineClass;
import com.foxminded.university.model.classes.OnlineClass;
import com.foxminded.university.model.classes.StudyClass;
import com.foxminded.university.model.classes.dtos.OfflineClassDTO;
import com.foxminded.university.model.classes.dtos.OnlineClassDTO;
import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.service.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @BeforeEach
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
        entityManager.persist(groupA);
        entityManager.persist(groupB);


        math = Course.builder()
                .name("Mathematics")
                .build();
        physics = Course.builder()
                .name("Physics")
                .build();
        entityManager.persist(math);
        entityManager.persist(physics);

        alice = Teacher.builder()
                .firstName("Alice")
                .lastName("Smith")
                .build();
        bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .build();
        entityManager.persist(alice);
        entityManager.persist(bob);


        charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .build();
        diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .build();
        entityManager.persist(charlie);
        entityManager.persist(diana);
        ics = Location.builder()
                .department("ICS")
                .classroom("101")
                .build();
        fdu = Location.builder()
                .department("FDU")
                .classroom("102")
                .build();
        entityManager.persist(ics);
        entityManager.persist(fdu);

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
        entityManager.persist(onlineClass);
        entityManager.persist(offlineClass);

        entityManager.flush();
    }

    @Test
    void saveOnlineClass() {
        OnlineClassDTO onlineClassToSave = OnlineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(physics)
                .teacher(bob)
                .group(groupA)
                .url("http://example.com")
                .build();

        studyClassService.saveOnlineClass(onlineClassToSave);
        List<StudyClass> classes = studyClassService.findAllClassesWithPagination(0, 3);
        OnlineClass actual = (OnlineClass) classes.get(2);
        assertEquals(onlineClassToSave.getUrl(), actual.getUrl());
        assertEquals(onlineClassToSave.getCourse(), actual.getCourse());
        assertEquals(onlineClassToSave.getGroup(), actual.getGroup());
        assertEquals(onlineClassToSave.getTeacher(), actual.getTeacher());
        assertEquals(onlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(onlineClassToSave.getEndTime(), actual.getEndTime());
    }

    @Test
    void saveOfflineClass() {
        OfflineClassDTO offlineClassToSave = OfflineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(physics)
                .teacher(bob)
                .group(groupA)
                .location(ics)
                .build();

        studyClassService.saveOfflineClass(offlineClassToSave);
        List<StudyClass> offlineClass = studyClassService.findAllClassesWithPagination(0, 3);
        OfflineClass actual = (OfflineClass) offlineClass.get(2);
        assertEquals(offlineClassToSave.getLocation(), actual.getLocation());
        assertEquals(offlineClassToSave.getCourse(), actual.getCourse());
        assertEquals(offlineClassToSave.getGroup(), actual.getGroup());
        assertEquals(offlineClassToSave.getTeacher(), actual.getTeacher());
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
    void updateOnlineClass() {
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(0, 1).get(0);
        String classId = studyClass.getId();

        OnlineClassDTO onlineClassToUpdate = OnlineClassDTO.builder()
                .id(classId)
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(physics)
                .teacher(bob)
                .group(groupA)
                .url("http://foxminded.com")
                .build();

        studyClassService.updateOnlineClass(onlineClassToUpdate);
        List<StudyClass> onlineClass = studyClassService.findAllClassesWithPagination(0, 2);
        OnlineClass actual = (OnlineClass) onlineClass.get(0);
        assertEquals(onlineClassToUpdate.getUrl(), actual.getUrl());
        assertEquals(onlineClassToUpdate.getCourse(), actual.getCourse());
        assertEquals(onlineClassToUpdate.getGroup(), actual.getGroup());
        assertEquals(onlineClassToUpdate.getTeacher(), actual.getTeacher());
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
                .course(physics)
                .teacher(bob)
                .group(groupA)
                .location(ics)
                .build();

        studyClassService.updateOfflineClass(offlineClassToSave);
        List<StudyClass> studyClasses = studyClassService.findAllClassesWithPagination(0, 3);
        OfflineClass actual = (OfflineClass) studyClasses.get(1);
        assertEquals(offlineClassToSave.getLocation(), actual.getLocation());
        assertEquals(offlineClassToSave.getCourse(), actual.getCourse());
        assertEquals(offlineClassToSave.getGroup(), actual.getGroup());
        assertEquals(offlineClassToSave.getTeacher(), actual.getTeacher());
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
    }
}
