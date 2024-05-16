package com.foxminded.university.service.user;

import com.foxminded.university.model.Course;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Location;
import com.foxminded.university.model.classes.OfflineClass;
import com.foxminded.university.model.classes.OnlineClass;
import com.foxminded.university.model.users.Student;
import com.foxminded.university.model.users.Teacher;
import com.foxminded.university.model.users.User;
import com.foxminded.university.model.users.dtos.StudentDTO;
import com.foxminded.university.model.users.dtos.TeacherDTO;
import com.foxminded.university.service.TestConfig;
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
class DefaultUserServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DefaultUserService userService;

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
    private final String username = "username";
    private final String password = "$2a$12$IgoUWIHUQ/hmX39dsVixgeIWHK3.vBS8luDFFZRxQSIRlTborOB66";
    private final String rawPassword = "password";

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
                .username(username)
                .password(password)
                .rawPassword(rawPassword)
                .build();
        bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username(username)
                .password(password)
                .rawPassword(rawPassword)
                .build();
        entityManager.persist(alice);
        entityManager.persist(bob);


        charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .username(username)
                .password(password)
                .rawPassword(rawPassword)
                .build();
        diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .username(username)
                .password(password)
                .rawPassword(rawPassword)
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
    void saveStudent() {
        StudentDTO studentToSave = StudentDTO.builder()
                .firstName("Test")
                .lastName("Test")
                .group(groupA)
                .build();

        userService.saveStudent(studentToSave);
        List<User> users = userService.findAllUsersWithPagination(0, 5).toList();
        Student actualStudent = (Student) users.get(4);

        assertEquals(studentToSave.getFirstName(), actualStudent.getFirstName());
        assertEquals(studentToSave.getLastName(), actualStudent.getLastName());
        assertEquals(studentToSave.getGroup(), actualStudent.getGroup());
    }

    @Test
    void saveTeacher() {
        TeacherDTO teacherToSave = TeacherDTO.builder()
                .firstName("Test")
                .lastName("Test")
                .studyClasses(List.of(offlineClass, onlineClass))
                .build();

        userService.saveTeacher(teacherToSave);
        List<User> users = userService.findAllUsersWithPagination(0, 5).toList();
        Teacher actualTeacher = (Teacher) users.get(4);

        assertEquals(teacherToSave.getFirstName(), actualTeacher.getFirstName());
        assertEquals(teacherToSave.getLastName(), actualTeacher.getLastName());
        assertEquals(teacherToSave.getStudyClasses(), actualTeacher.getStudyClasses());
    }

    @Test
    void findUserById() {
        User user = userService.findAllUsersWithPagination(0, 4).toList().get(3);
        String userId = user.getId();
        assertEquals(user, userService.findUserById(userId));
    }

    @Test
    void updateStudent() {
        User user = userService.findAllUsersWithPagination(0, 4).toList().get(3);
        String userId = user.getId();

        StudentDTO studentToSave = StudentDTO.builder()
                .id(userId)
                .firstName("Test")
                .lastName("Test")
                .group(groupA)
                .password(password)
                .rawPassword(rawPassword)
                .username(username)
                .build();

        userService.updateStudent(studentToSave);
        Student actualStudent = (Student) userService.findUserById(userId);

        assertEquals(studentToSave.getFirstName(), actualStudent.getFirstName());
        assertEquals(studentToSave.getLastName(), actualStudent.getLastName());
        assertEquals(studentToSave.getGroup(), actualStudent.getGroup());
    }

    @Test
    void updateTeacher() {
        User user = userService.findAllUsersWithPagination(0, 4).toList().get(0);
        String userId = user.getId();

        TeacherDTO teacherToSave = TeacherDTO.builder()
                .id(userId)
                .firstName("Test")
                .lastName("Test")
                .studyClasses(List.of(offlineClass, onlineClass))
                .password(password)
                .rawPassword(rawPassword)
                .username(username)
                .build();

        userService.updateTeacher(teacherToSave);
        Teacher actualTeacher = (Teacher) userService.findUserById(userId);

        assertEquals(teacherToSave.getFirstName(), actualTeacher.getFirstName());
        assertEquals(teacherToSave.getLastName(), actualTeacher.getLastName());
        assertEquals(teacherToSave.getStudyClasses(), actualTeacher.getStudyClasses());
    }

    @Test
    void deleteUserById() {
        User user = userService.findAllUsersWithPagination(0, 4).toList().get(0);
        String userId = user.getId();

        userService.deleteUserById(userId);
        assertThrows(NoSuchElementException.class, () -> userService.findUserById(userId));
    }

    @Test
    void findAllUsersWithPagination() {
        List<User> expectedUsers = Arrays.asList(alice, bob, charlie);
        Page<User> actualUsers = userService.findAllUsersWithPagination(0, 3);
        actualUsers.forEach(user -> user.setId(null));

        assertEquals(expectedUsers, actualUsers.stream().toList());
    }
    @Test
    void findAllStudents() {
        List<Student> expectedStudents = Arrays.asList(charlie, diana);
        Page<Student> actualStudents = userService.findAllStudentsWithPagination(0,2);
        actualStudents.forEach(user -> user.setId(null));

        assertEquals(expectedStudents, actualStudents.toList());
    }
    @Test
    void findAllTeachers() {
        List<Teacher> expectedTeachers = Arrays.asList(alice, bob);
        Page<Teacher> actualTeachers = userService.findAllTeachersWithPagination(0,2);
        actualTeachers.forEach(user -> user.setId(null));

        assertEquals(expectedTeachers, actualTeachers.toList());
    }
}
