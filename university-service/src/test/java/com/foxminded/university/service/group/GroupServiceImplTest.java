package com.foxminded.university.service.group;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GroupServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupService groupService;

    @BeforeEach
    public void init() {
        entityManager.getEntityManager().createNativeQuery("DELETE FROM classes").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM courses").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM groups").executeUpdate();
        entityManager.getEntityManager().createNativeQuery("DELETE FROM locations").executeUpdate();

        String username = "username";
        String password = "$2a$12$IgoUWIHUQ/hmX39dsVixgeIWHK3.vBS8luDFFZRxQSIRlTborOB66";

        Group groupA = Group.builder()
                .name("Group A")
                .build();
        Group groupB = Group.builder()
                .name("Group B")
                .build();
        entityManager.persist(groupA);
        entityManager.persist(groupB);


        Course math = Course.builder()
                .name("Mathematics")
                .build();
        Course physics = Course.builder()
                .name("Physics")
                .build();
        entityManager.persist(math);
        entityManager.persist(physics);

        Teacher alice = Teacher.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username(username)
                .password(password)
                .build();
        Teacher bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username(username)
                .password(password)
                .build();
        entityManager.persist(alice);
        entityManager.persist(bob);

        Student charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .username(username)
                .password(password)
                .group(groupA)
                .build();
        Student diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .username(username)
                .password(password)
                .group(groupB)
                .build();
        entityManager.persist(charlie);
        entityManager.persist(diana);

        Location science = Location.builder()
                .department("Science")
                .classroom("101")
                .build();
        Location arts = Location.builder()
                .department("Arts")
                .classroom("102")
                .build();
        entityManager.persist(science);
        entityManager.persist(arts);

        OnlineClass onlineClass = OnlineClass.builder()
                .startTime((LocalDateTime.of(2024, 4, 23, 9, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 10, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .course(math)
                .teacher(alice)
                .group(groupA)
                .url("http://example.com")
                .build();
        OfflineClass offlineClass = OfflineClass.builder()
                .startTime((LocalDateTime.of(2024, 4, 23, 11, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 12, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .course(physics)
                .teacher(bob)
                .group(groupB)
                .location(science)
                .build();
        entityManager.persist(onlineClass);
        entityManager.persist(offlineClass);

        groupA.setStudents(new ArrayList<>(Arrays.asList(charlie)));
        groupA.setStudyClasses(new ArrayList<>(Arrays.asList(onlineClass)));
        groupA = entityManager.persist(groupA);

        groupB.setStudents(new ArrayList<>(Arrays.asList(diana)));
        groupB.setStudyClasses(new ArrayList<>(Arrays.asList(offlineClass)));
        groupB = entityManager.persist(groupB);

        entityManager.flush();
    }

    @Test
    void testSaveGroup() {
        GroupFormation groupToSave = GroupFormation.builder()
                .name("Mugiwaras")
                .build();

        groupService.saveGroup(groupToSave);
        GroupFormation group = groupService.findAllGroups().getLast();
        group.setId(null);
        assertEquals(groupToSave, group);
    }

    @Test
    void findGroupById() {
        GroupFormation group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        Group groupActual = groupService.findGroupById(groupId);
        assertEquals("Group A", groupActual.getName());
    }

    @Test
    void findGroupDTOById() {
        GroupFormation group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        GroupRequest groupActual = groupService.findGroupDTOById(groupId);
        assertEquals("Group A", groupActual.getName());
    }

    @Test
    void assertThrowsExceptionIfGroupIsNotPresent() {
        assertThrows(EntityNotFoundException.class, () -> groupService.findGroupById("testId"));
    }

    @Test
    void updateGroup() {
        GroupFormation group = groupService.findAllGroups().getFirst();
        String studentId = groupService.findGroupById(groupService.findAllGroups().getLast().getId()).getStudents().getFirst().getId();
        String classId = groupService.findGroupById(groupService.findAllGroups().getLast().getId()).getStudyClasses().getFirst().getId();
        String groupId = group.getId();
        GroupRequest groupToUpdate = GroupRequest.builder()
                .id(groupId)
                .name("Update name")
                .studentsIds(Arrays.asList(studentId))
                .studyClassesIds(Arrays.asList(classId))
                .build();
        groupService.updateGroup(groupToUpdate);
        Group updatedGroup = groupService.findGroupById(groupId);
        assertEquals(groupToUpdate.getId(), updatedGroup.getId());
        assertEquals(studentId, updatedGroup.getStudents().getFirst().getId());
        assertEquals(classId, updatedGroup.getStudyClasses().getFirst().getId());
    }

    @Test
    void deleteGroupById() {
        GroupFormation group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        groupService.deleteGroupById(groupId);
        assertThrows(EntityNotFoundException.class, () -> groupService.findGroupById(groupId));
    }

    @Test
    void findAllGroupsWithPagination() {
        GroupFormation groupA = GroupFormation.builder()
                .name("Group A")
                .build();
        GroupFormation groupB = GroupFormation.builder()
                .name("Group B")
                .build();

        List<GroupFormation> groups = Arrays.asList(groupA, groupB);
        RequestPage page = PageUtils.createPage(0, 2);
        Page<GroupFormation> groupsActual = groupService.findAllGroupsWithPagination(page);
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual.toList(), is(groups));
    }

    @Test
    void findAllGroupsWithoutSchedule() {
        GroupFormation groupA = GroupFormation.builder()
                .name("Group A")
                .build();
        GroupFormation groupB = GroupFormation.builder()
                .name("Group B")
                .build();

        List<GroupFormation> groups = Arrays.asList(groupA, groupB);
        List<GroupFormation> groupsActual = groupService.findAllGroupsWithoutSchedule();
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual, is(groups));
    }

    @Test
    void findAllGroupsResponseWithPagination() {
        GroupAssignResponse groupA = GroupAssignResponse.builder()
                .name("Group A")
                .build();
        GroupAssignResponse groupB = GroupAssignResponse.builder()
                .name("Group B")
                .build();

        List<GroupAssignResponse> groups = Arrays.asList(groupA, groupB);
        RequestPage page = PageUtils.createPage(0, 2);
        Page<GroupAssignResponse> groupsActual = groupService.findAllGroupsResponsesWithPagination(page);
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual.toList(), is(groups));
    }

    @Test
    void findAllGroups() {
        GroupFormation groupA = GroupFormation.builder()
                .name("Group A")
                .build();
        GroupFormation groupB = GroupFormation.builder()
                .name("Group B")
                .build();

        List<GroupFormation> groups = Arrays.asList(groupA, groupB);
        List<GroupFormation> groupsActual = groupService.findAllGroups();
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual, is(groups));
    }

    @Test
    void findAllStudentsAssignedToGroup() {
        GroupFormation group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();

        List<StudentResponse> studentsAssignedToGroup = groupService.findAllStudentsAssignedToGroup(groupId);
        assertEquals("Charlie", studentsAssignedToGroup.getFirst().getFirstName());
        assertEquals("Williams", studentsAssignedToGroup.getFirst().getLastName());
    }

    @Test
    void  deleteStudentFromGroupById(){
        String groupId = groupService.findAllGroups().getFirst().getId();
        Group group = groupService.findGroupById(groupId);
        Student student = group.getStudents().getFirst();
        group.getStudents().remove(student);
        List<Student> expectedStudents = group.getStudents();
        String studentId = student.getId();

        groupService.deleteStudentFromGroupById(studentId);
        List<Student> actualStudents = groupService.findGroupById(groupId).getStudents();
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void  deleteStudyClassFromGroupById(){
        String groupId = groupService.findAllGroups().getFirst().getId();
        Group group = groupService.findGroupById(groupId);
        StudyClass studyClass = group.getStudyClasses().getFirst();
        group.getStudyClasses().remove(studyClass);
        List<StudyClass> expectedStudyClasses = group.getStudyClasses();
        String studyClassId = studyClass.getId();

        groupService.deleteStudyClassFromGroupById(studyClassId);
        List<StudyClass> actualStudyClass = groupService.findGroupById(groupId).getStudyClasses();
        assertEquals(expectedStudyClasses, actualStudyClass);
    }



}
