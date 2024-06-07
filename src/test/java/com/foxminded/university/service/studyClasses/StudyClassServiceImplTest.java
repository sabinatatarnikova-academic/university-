package com.foxminded.university.service.studyClasses;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.classes.OfflineClassDTO;
import com.foxminded.university.model.dtos.classes.OnlineClassDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.classes.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.classes.StudyClassServiceImpl;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.LocationMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
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
class StudyClassServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudyClassServiceImpl studyClassService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private TeacherMapper teacherMapper;

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
    private String password = "password";

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
                .build();
        bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username(username)
                .password(password)
                .build();
        alice = entityManager.merge(alice);
        bob = entityManager.merge(bob);

        charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .username(username)
                .password(password)
                .build();
        diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .username(username)
                .password(password)
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
    void saveOnlineClass() {
        OnlineClassDTO onlineClassToSave = OnlineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .group(groupMapper.toDto(groupA))
                .course(courseMapper.toDto(math))
                .url("http://example.com")
                .build();

        studyClassService.saveOnlineClass(onlineClassToSave);
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(3));
        List<StudyClass> classes = studyClassService.findAllClassesWithPagination(validatedParams);
        OnlineClass actual = (OnlineClass) classes.get(2);
        assertEquals(onlineClassToSave.getUrl(), actual.getUrl());
        assertEquals(onlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(onlineClassToSave.getEndTime(), actual.getEndTime());
        assertEquals(groupA, actual.getGroup());
        assertEquals(math, actual.getCourse());
    }

    @Test
    void saveOfflineClass() {
        OfflineClassDTO offlineClassToSave = OfflineClassDTO.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .group(groupMapper.toDto(groupA))
                .course(courseMapper.toDto(physics))
                .location(locationMapper.toDto(ics))
                .build();

        studyClassService.saveOfflineClass(offlineClassToSave);
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(3));
        List<StudyClass> offlineClass = studyClassService.findAllClassesWithPagination(validatedParams);
        OfflineClass actual = (OfflineClass) offlineClass.get(2);
        assertEquals(ics, actual.getLocation());
        assertEquals(physics, actual.getCourse());
        assertEquals(groupA, actual.getGroup());
        assertEquals(offlineClassToSave.getStartTime(), actual.getStartTime());
        assertEquals(offlineClassToSave.getEndTime(), actual.getEndTime());
    }

    @Test
    void findClassById() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(1));
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(validatedParams).get(0);
        String classId = studyClass.getId();
        assertEquals(studyClass, studyClassService.findClassById(classId));
    }

    @Test
    void assertThrowsExceptionIfClassIsNotPresent() {
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById("testId"));
    }

    @Test
    void deleteClassById() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        StudyClass studyClass = studyClassService.findAllClassesWithPagination(validatedParams).get(1);
        String classId = studyClass.getId();

        studyClassService.deleteClassById(classId);
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById(classId));
    }

    @Test
    void findAllClassesWithPagination() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        List<StudyClass> expectedClasses = Arrays.asList(onlineClass, offlineClass);
        List<StudyClass> actualClasses = studyClassService.findAllClassesWithPagination(validatedParams);
        actualClasses.forEach(studyClass -> studyClass.setId(null));
        assertEquals(expectedClasses, actualClasses);
    }
}
