package com.foxminded.university.service.group;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
class GroupServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupServiceImpl groupService;

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
                .groupName("Group A")
                .build();
        Group groupB = Group.builder()
                .groupName("Group B")
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

        entityManager.flush();
    }

    @Test
    void testSaveGroup() {
        Group groupToSave = Group.builder()
                .groupName("Mugiwaras")
                .build();

        groupService.saveGroup(groupToSave);
        Group group = groupService.findGroupByName("Mugiwaras");
        group.setId(null);
        assertThat(groupToSave, is(group));
    }

    @Test
    void findGroupById() {
        Group groupByName = groupService.findGroupByName("Group A");
        String groupId = groupByName.getId();
        Group group = groupService.findGroupById(groupId);
        group.setId(null);
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        assertThat(group, is(groupA));
    }

    @Test
    void assertThrowsExceptionIfGroupIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> groupService.findGroupById("testId"));
    }

    @Test
    void findGroupByName() {
        Group group = groupService.findGroupByName("Group A");
        group.setId(null);
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        assertThat(group, is(groupA));
    }

    @Test
    void assertThrowsExceptionIfGroupIsNotPresentByName(){
        assertThrows(NoSuchElementException.class, () -> groupService.findGroupByName("test"));
    }

    @Test
    void updateGroup() {
        Group group = groupService.findGroupByName("Group A");
        String groupId = group.getId();
        Group groupToUpdate = Group.builder()
                .id(groupId)
                .groupName("Update name")
                .students(new ArrayList<>())
                .studyClasses(new ArrayList<>())
                .build();
        groupService.updateGroup(groupToUpdate);
        Group updatedGroup = groupService.findGroupByName("Update name");
        assertThat(groupToUpdate, is(updatedGroup));
    }

    @Test
    void deleteGroupById() {
        String groupId = groupService.findGroupByName("Group A").getId();
        groupService.deleteGroupById(groupId);
        assertThrows(NoSuchElementException.class, () -> groupService.findGroupById(groupId));
    }

    @Test
    void findAllGroupsWithPagination() {
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        Group groupB = Group.builder()
                .groupName("Group B")
                .build();

        List<Group> groups = Arrays.asList(groupA, groupB);
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(Integer.MAX_VALUE));
        List<Group> groupsActual = groupService.findAllGroupsWithPagination(validatedParams);
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual, is(groups));
    }

    @Test
    void findAllGroups() {
        Group groupA = Group.builder()
                .groupName("Group A")
                .build();
        Group groupB = Group.builder()
                .groupName("Group B")
                .build();

        List<Group> groups = Arrays.asList(groupA, groupB);
        List<Group> groupsActual = groupService.findAllGroups();
        groupsActual.forEach(course -> course.setId(null));
        assertThat(groupsActual, is(groups));
    }
}
