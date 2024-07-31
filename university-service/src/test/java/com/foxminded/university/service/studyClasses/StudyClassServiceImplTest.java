package com.foxminded.university.service.studyClasses;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.request.classes.StudyClassRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.GroupEditResponse;
import com.foxminded.university.model.dtos.response.classes.CreateStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.EditStudyClassResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.classes.GlobalStudyClassesService;
import com.foxminded.university.service.classes.StudyClassServiceImpl;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class StudyClassServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudyClassServiceImpl studyClassService;

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GlobalStudyClassesService globalStudyClassesService;

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
                .startTime((LocalDateTime.of(2024, 4, 23, 9, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 10, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .course(math)
                .teacher(alice)
                .group(groupA)
                .url("http://example.com")
                .classType("ONLINE")
                .build();
        offlineClass = OfflineClass.builder()
                .startTime((LocalDateTime.of(2024, 4, 23, 11, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 12, 0)).atZone(ZoneId.of("Europe/Kiev")))
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
        assertEquals(studyClass.getStartTime(), actual.getStartTime().toLocalDateTime());
        assertEquals(studyClass.getEndTime(), actual.getEndTime().toLocalDateTime());
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
        assertEquals(studyClass.getStartTime().atZone(ZoneId.of("Europe/Kiev")), actual.getStartTime());
        assertEquals(studyClass.getEndTime().atZone(ZoneId.of("Europe/Kiev")), actual.getEndTime());
        assertEquals(studyClass.getClassType(), actual.getClassType());
    }

    @Test
    void updateStudyClass() {
        String studyClassId = studyClassService.findAllClasses().getFirst().getId();

        StudyClassRequest studyClassRequest = StudyClassRequest.builder()
                .id(studyClassId)
                .startTime(onlineClass.getStartTime().toLocalDateTime())
                .endTime(onlineClass.getEndTime().toLocalDateTime())
                .classType(onlineClass.getClassType())
                .courseId(physics.getId())
                .groupId(offlineClass.getGroup().getId())
                .teacherId(offlineClass.getTeacher().getId())
                .url("http://test.com")
                .build();

        studyClassService.updateStudyClass(studyClassRequest);

        OnlineClass studyClass = (OnlineClass) studyClassService.findClassById(studyClassId);
        assertThat(studyClassRequest.getId(), is(studyClass.getId()));
        assertThat(studyClassRequest.getStartTime(), is(studyClass.getStartTime().toLocalDateTime()));
        assertThat(studyClassRequest.getEndTime(), is(studyClass.getEndTime().toLocalDateTime()));
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
    void findClassDTOById() {
        StudyClassResponse studyClass = studyClassService.findAllClasses().get(0);
        String classId = studyClass.getId();
        StudyClassRequest actual = studyClassService.findClassDTOById(classId);
        assertEquals(studyClass.getId(), actual.getId());
    }

    @Test
    void assertThrowsExceptionIfClassIsNotPresent() {
        assertThrows(EntityNotFoundException.class, () -> studyClassService.findClassById("testId"));
    }

    @Test
    void deleteClassById() {
        RequestPage page = PageUtils.createPage(0, 2);
        StudyClassResponse studyClass = studyClassService.findAllClassesWithPagination(page).toList().get(1);
        String classId = studyClass.getId();

        studyClassService.deleteClassById(classId);
        assertThrows(EntityNotFoundException.class, () -> studyClassService.findClassById(classId));
    }

    @Test
    void findAllClassesWithPagination() {
        RequestPage page = PageUtils.createPage(0, 2);
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

    @Test
    void testGetAllRequiredDataForStudyClassEdit() {
        EditStudyClassResponse data = studyClassService.getAllRequiredDataForStudyClassEdit();

        assertThat(data, hasProperty("courses"));
        List<CourseDTO> courses = data.getCourses();
        assertThat(courses,
                containsInAnyOrder(hasProperty("name", is("Mathematics")),
                        hasProperty("name", is("Physics"))));

        assertThat(data, hasProperty("teachers"));
        List<TeacherResponse> teachers = data.getTeachers();
        assertThat(teachers,
                containsInAnyOrder(hasProperty("firstName", is("Alice")),
                        hasProperty("firstName", is("Bob"))));

        assertThat(data, hasProperty("groups"));
        List<GroupFormation> groups = data.getGroups();
        assertThat(groups,
                containsInAnyOrder(hasProperty("name", is("Group A")),
                        hasProperty("name", is("Group B"))));

        assertThat(data, hasProperty("locations"));
        List<LocationDTO> locations = data.getLocations();
        assertThat(locations,
                containsInAnyOrder(hasProperty("department", is("ICS")),
                        hasProperty("department", is("FDU"))));
    }

    @Test
    void testGetAllRequiredData() {
        String groupId = studyClassService.findAllClasses().getFirst().getGroupId();
        RequestPage page = PageUtils.createPage(0, 10);


        GroupEditResponse data = studyClassService.getAllRequiredDataForGroupEdit(groupId, page);
        assertThat(data, hasProperty("group"));

        GroupRequest group = data.getGroup();

        GroupRequest groupDTOById = groupService.findGroupDTOById(groupId);
        assertThat(group.getId(), is(groupDTOById.getId()));
        assertThat(group.getName(), is(groupDTOById.getName()));

        assertThat(data, hasProperty("students"));

        Page<StudentResponse> students = data.getStudents();
        assertThat(students,
                containsInAnyOrder(hasProperty("firstName", is("Charlie")),
                        hasProperty("firstName", is("Diana"))));

        assertThat(data, hasProperty("studyClasses"));
        List<StudyClassResponse> classes = studyClassService.findAllClasses();
        List<StudyClassResponse> classesMap = data.getStudyClasses();
        assertThat(classes, is(classesMap));
    }

    @Test
    void testDeleteTeacherFromStudyClass() {
        String id = studyClassService.findAllClasses().getFirst().getId();

        StudyClass studyClass = studyClassService.findClassById(id);
        List<StudyClass> studyClassesList = new ArrayList<>();
        studyClassesList.add(studyClass);
        studyClass.getTeacher().setStudyClasses(studyClassesList);
        assertThat(studyClass.getTeacher().getStudyClasses(), contains(studyClass));

        studyClassService.deleteTeacherFromStudyClass(id);
        assertEquals(studyClass.getTeacher(), null);
    }
}
