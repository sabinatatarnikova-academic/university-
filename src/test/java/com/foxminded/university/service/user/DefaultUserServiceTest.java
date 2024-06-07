package com.foxminded.university.service.user;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.classes.StudyClassDTO;
import com.foxminded.university.model.dtos.users.UserDTO;
import com.foxminded.university.model.dtos.users.UserFormDTO;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.entity.users.User;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class DefaultUserServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DefaultUserService userService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StudyClassMapper studyClassMapper;

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
    void saveUser() {
        UserDTO userToSave = UserDTO.builder()
                .firstName("Test")
                .lastName("Test")
                .userType("TEACHER")
                .username("username")
                .password("password")
                .build();

        userService.saveUser(userToSave);
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(5));
        List<User> users = userService.findAllUsersWithPagination(validatedParams).toList();
        User actualUser = users.get(4);

        assertEquals(userToSave.getFirstName(), actualUser.getFirstName());
        assertEquals(userToSave.getLastName(), actualUser.getLastName());
        assertEquals(userToSave.getUserType(), actualUser.getUserType());
        assertEquals(userToSave.getUsername(), actualUser.getUsername());
    }

    @Test
    void findUserById() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        User user = userService.findAllUsersWithPagination(validatedParams).toList().get(3);
        String userId = user.getId();
        assertEquals(user, userService.findUserById(userId));
    }

    @Test
    void assertThrowsExceptionIfCourseIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> userService.findUserById("testId"));
    }

    @Test
    void updateStudent() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        User user = userService.findAllUsersWithPagination(validatedParams).toList().get(3);
        String userId = user.getId();

        UserFormDTO studentToSave = UserFormDTO.builder()
                .id(userId)
                .firstName("Test")
                .lastName("Test")
                .password(password)
                .username(username)
                .group(groupMapper.toDto(groupA))
                .build();

        userService.updateStudent(studentToSave);
        Student actualStudent = (Student) userService.findUserById(userId);

        assertEquals(studentToSave.getFirstName(), actualStudent.getFirstName());
        assertEquals(studentToSave.getLastName(), actualStudent.getLastName());
        assertEquals(groupA, actualStudent.getGroup());
    }

    @Test
    void updateTeacher() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        User user = userService.findAllUsersWithPagination(validatedParams).toList().get(0);
        String userId = user.getId();

        StudyClassDTO onlineClassDTO = studyClassMapper.toDto(onlineClass);
        StudyClassDTO offlineClassDTO = studyClassMapper.toDto(offlineClass);

        UserFormDTO teacherToSave = UserFormDTO.builder()
                .id(userId)
                .firstName("Test")
                .lastName("Test")
                .password(password)
                .username(username)
                .studyClassesIds(List.of(onlineClassDTO.getId(), offlineClassDTO.getId()))
                .build();

        userService.updateTeacher(teacherToSave);
        Teacher actualTeacher = (Teacher) userService.findUserById(userId);

        assertEquals(teacherToSave.getFirstName(), actualTeacher.getFirstName());
        assertEquals(teacherToSave.getLastName(), actualTeacher.getLastName());
        assertThat(actualTeacher.getStudyClasses(), containsInAnyOrder(onlineClass, offlineClass));
    }

    @Test
    void deleteUserById() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        User user = userService.findAllUsersWithPagination(validatedParams).toList().get(0);
        String userId = user.getId();

        userService.deleteUserById(userId);
        assertThrows(NoSuchElementException.class, () -> userService.findUserById(userId));
    }

    @Test
    void findAllUsersWithPagination() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(3));
        List<User> expectedUsers = Arrays.asList(alice, bob, charlie);
        Page<User> actualUsers = userService.findAllUsersWithPagination(validatedParams);
        actualUsers.forEach(user -> user.setId(null));

        assertEquals(expectedUsers, actualUsers.stream().toList());
    }
    @Test
    void findAllStudents() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        List<Student> expectedStudents = Arrays.asList(charlie, diana);
        Page<Student> actualStudents = userService.findAllStudentsWithPagination(validatedParams);
        actualStudents.forEach(user -> user.setId(null));

        assertEquals(expectedStudents, actualStudents.toList());
    }
    @Test
    void findAllTeachers() {
        RequestPage validatedParams = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        List<Teacher> expectedTeachers = Arrays.asList(alice, bob);
        Page<Teacher> actualTeachers = userService.findAllTeachersWithPagination(validatedParams);
        actualTeachers.forEach(user -> user.setId(null));

        assertEquals(expectedTeachers, actualTeachers.toList());
    }
}
