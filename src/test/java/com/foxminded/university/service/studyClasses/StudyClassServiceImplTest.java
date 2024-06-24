package com.foxminded.university.service.studyClasses;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.OfflineClassResponse;
import com.foxminded.university.model.dtos.response.classes.OnlineClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
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
import com.foxminded.university.utils.mappers.classes.OfflineClassMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.TeacherMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    @Autowired
    private StudyClassMapper studyClassMapper;
    @Autowired
    private OfflineClassMapper offlineClassMapper;

    @BeforeEach
    @Transactional
    public void init() {
        entityManager.getEntityManager().createNativeQuery("DELETE FROM classes").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM courses").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM groups").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM locations").executeUpdate();

        groupA = Group.builder()
                .name("Group A")
                .build();
        groupB = Group.builder()
                .name("Group B")
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
                .classType("ONLINE")
                .build();
        offlineClass = OfflineClass.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 11, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 12, 0))
                .course(physics)
                .teacher(bob)
                .group(groupB)
                .location(ics)
                .classType("OFFLINE")
                .build();
        onlineClass = entityManager.merge(onlineClass);
        offlineClass = entityManager.merge(offlineClass);

        entityManager.flush();
    }

    @Test
    void testSaveOnlineStudyClass() {
        CreateStudyClassResponse studyClass = CreateStudyClassResponse.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .classType("ONLINE")
                .build();

        studyClassService.saveStudyClass(studyClass);
        List<StudyClassResponse> classes = studyClassService.findAllClasses();
        StudyClassResponse actual = classes.get(2);
        actual.setId(null);
        assertEquals(studyClass.getStartTime(), actual.getStartTime());
        assertEquals(studyClass.getEndTime(), actual.getEndTime());
        assertEquals(studyClass.getClassType(), actual.getClassType());
    }

    @Test
    void testSaveOfflineStudyClass() {
        CreateStudyClassResponse studyClass = CreateStudyClassResponse.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .classType("OFFLINE")
                .build();

        studyClassService.saveStudyClass(studyClass);
        List<StudyClassResponse> classes = studyClassService.findAllClasses();
        StudyClassResponse actual = classes.get(2);
        actual.setId(null);
        assertEquals(studyClass.getStartTime(), actual.getStartTime());
        assertEquals(studyClass.getEndTime(), actual.getEndTime());
        assertEquals(studyClass.getClassType(), actual.getClassType());
    }

    @Test
    void updateStudyClass() {
        String studyClassId = studyClassService.findAllClasses().getFirst().getId();

        StudyClassRequest studyClassRequest = StudyClassRequest.builder()
                .id(studyClassId)
                .startTime(onlineClass.getStartTime())
                .endTime(onlineClass.getEndTime())
                .classType(onlineClass.getClassType())
                .courseId(physics.getId())
                .groupId(offlineClass.getGroup().getId())
                .teacherId(offlineClass.getTeacher().getId())
                .url("http://test.com")
                .build();

        studyClassService.updateStudyClass(studyClassRequest);

        OnlineClass studyClass = (OnlineClass) studyClassService.findClassById(studyClassId);
        assertThat(studyClassRequest.getId(), is(studyClass.getId()));
        assertThat(studyClassRequest.getStartTime(), is(studyClass.getStartTime()));
        assertThat(studyClassRequest.getEndTime(), is(studyClass.getEndTime()));
        assertThat(studyClassRequest.getCourseId(), is(studyClass.getCourse().getId()));
        assertThat(studyClassRequest.getGroupId(), is(studyClass.getGroup().getId()));
        assertThat(studyClassRequest.getUrl(), is(studyClass.getUrl()));
    }

    @Test
    void findClassById() {
        StudyClassResponse studyClass = studyClassService.findAllClasses().get(0);
        String classId = studyClass.getId();
        StudyClass actual = studyClassService.findClassById(classId);
        assertEquals(studyClass.getId(), actual.getId());
    }

    @Test
    void assertThrowsExceptionIfClassIsNotPresent() {
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById("testId"));
    }

    @Test
    void deleteClassById() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        StudyClassResponse studyClass = studyClassService.findAllClassesWithPagination(page).toList().get(1);
        String classId = studyClass.getId();

        studyClassService.deleteClassById(classId);
        assertThrows(NoSuchElementException.class, () -> studyClassService.findClassById(classId));
    }

    @Test
    void findAllClassesWithPagination() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        List<StudyClassResponse> expectedClasses = Arrays.asList(studyClassMapper.toDto(onlineClass), studyClassMapper.toDto(offlineClass));
        Page<StudyClassResponse> actualClasses = studyClassService.findAllClassesWithPagination(page);
        actualClasses.forEach(studyClass -> studyClass.setId(null));
        expectedClasses.forEach(studyClass -> studyClass.setId(null));
        assertThat(expectedClasses.get(0), is(actualClasses.toList().get(0)));
        assertThat(expectedClasses.get(1), is(actualClasses.toList().get(1)));
    }

    @Test
    void findAllClasses() {
        List<StudyClassResponse> expectedClasses = Arrays.asList(studyClassMapper.toDto(onlineClass), studyClassMapper.toDto(offlineClass));
        List<StudyClassResponse> actualClasses = studyClassService.findAllClasses();
        actualClasses.forEach(studyClass -> studyClass.setId(null));
        expectedClasses.forEach(studyClass -> studyClass.setId(null));
        assertEquals(expectedClasses, actualClasses);
    }
}
