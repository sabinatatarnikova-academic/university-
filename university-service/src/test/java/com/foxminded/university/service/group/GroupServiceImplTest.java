package com.foxminded.university.service.group;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.GroupFormationDTO;
import com.foxminded.university.model.dtos.request.GroupRequest;
import com.foxminded.university.model.dtos.response.GroupAssignResponse;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
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
                .startTime(LocalDateTime.of(2024, 4, 23, 9, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 10, 0))
                .course(math)
                .teacher(alice)
                .group(groupA)
                .url("http://example.com")
                .build();
        OfflineClass offlineClass = OfflineClass.builder()
                .startTime(LocalDateTime.of(2024, 4, 23, 11, 0))
                .endTime(LocalDateTime.of(2024, 4, 23, 12, 0))
                .course(physics)
                .teacher(bob)
                .group(groupB)
                .location(science)
                .build();
        entityManager.persist(onlineClass);
        entityManager.persist(offlineClass);

        groupA.setStudents(Arrays.asList(charlie));
        groupA.setStudyClasses(Arrays.asList(onlineClass));
        groupA = entityManager.persist(groupA);

        entityManager.flush();
    }

    @Test
    void testSaveGroup() {
        GroupFormationDTO groupToSave = GroupFormationDTO.builder()
                .name("Mugiwaras")
                .build();

        groupService.saveGroup(groupToSave);
        GroupFormationDTO group = groupService.findAllGroups().getLast();
        group.setId(null);
        assertEquals(groupToSave, group);
    }

    @Test
    void findGroupById() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        Group groupActual = groupService.findGroupById(groupId);
        assertEquals("Group A", groupActual.getName());
    }

    @Test
    void findGroupDTOById() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        GroupAssignResponse groupActual = groupService.findGroupDTOById(groupId);
        assertEquals("Group A", groupActual.getName());
    }

    @Test
    void assertThrowsExceptionIfGroupIsNotPresent() {
        assertThrows(EntityNotFoundException.class, () -> groupService.findGroupById("testId"));
    }

    @Test
    void updateGroup() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        GroupRequest groupToUpdate = GroupRequest.builder()
                .id(groupId)
                .name("Update name")
                .students(new ArrayList<>())
                .studyClasses(new ArrayList<>())
                .build();
        groupService.updateGroup(groupToUpdate);
        Group updatedGroup = groupService.findGroupById(groupId);
        assertEquals(groupToUpdate.getId(), updatedGroup.getId());
        assertEquals(null, updatedGroup.getStudents());
        assertEquals(null, updatedGroup.getStudyClasses());
    }

    @Test
    void deleteGroupById() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();
        groupService.deleteGroupById(groupId);
        assertThrows(EntityNotFoundException.class, () -> groupService.findGroupById(groupId));
    }

    @Test
    void findAllGroupsWithPagination() {
        GroupFormationDTO groupA = GroupFormationDTO.builder()
                .name("Group A")
                .build();
        GroupFormationDTO groupB = GroupFormationDTO.builder()
                .name("Group B")
                .build();

        List<GroupFormationDTO> groups = Arrays.asList(groupA, groupB);
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(Integer.MAX_VALUE));
        Page<GroupFormationDTO> groupsActual = groupService.findAllGroupsWithPagination(page);
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual.toList(), is(groups));
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
        RequestPage page = PageUtils.createPage("0", "2");
        Page<GroupAssignResponse> groupsActual = groupService.findAllGroupsResponsesWithPagination(page);
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual.toList(), is(groups));
    }

    @Test
    void findAllGroups() {
        GroupFormationDTO groupA = GroupFormationDTO.builder()
                .name("Group A")
                .build();
        GroupFormationDTO groupB = GroupFormationDTO.builder()
                .name("Group B")
                .build();

        List<GroupFormationDTO> groups = Arrays.asList(groupA, groupB);
        List<GroupFormationDTO> groupsActual = groupService.findAllGroups();
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual, is(groups));
    }

    @Test
    void findAllStudentsAssignedToGroup() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();

        List<StudentResponse> studentsAssignedToGroup = groupService.findAllStudentsAssignedToGroup(groupId);
        assertEquals("Charlie", studentsAssignedToGroup.getFirst().getFirstName());
        assertEquals("Williams", studentsAssignedToGroup.getFirst().getLastName());
    }

    @Test
    void findAllClassesAssignedToGroup() {
        GroupFormationDTO group = groupService.findAllGroups().getFirst();
        String groupId = group.getId();

        List<StudyClassResponse> studyClassesAssignedToGroup = groupService.findAllStudyClassesAssignedToGroup(groupId);
        assertEquals(LocalDateTime.of(2024, 4, 23, 9, 0), studyClassesAssignedToGroup.getFirst().getStartTime());
        assertEquals(LocalDateTime.of(2024, 4, 23, 10, 0), studyClassesAssignedToGroup.getFirst().getEndTime());
        assertEquals(groupId, studyClassesAssignedToGroup.getFirst().getGroupId());
        assertEquals("Group A", studyClassesAssignedToGroup.getFirst().getGroupName());
    }
}
