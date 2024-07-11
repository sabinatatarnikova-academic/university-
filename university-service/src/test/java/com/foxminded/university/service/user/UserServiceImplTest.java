package com.foxminded.university.service.user;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.GroupFormation;
import com.foxminded.university.model.dtos.request.users.UserFormRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.dtos.response.users.StudentResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.dtos.response.users.UserResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import com.foxminded.university.utils.mappers.GroupMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import com.foxminded.university.utils.mappers.users.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class UserServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StudyClassMapper studyClassMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

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

        entityManager.flush();

        alice = Teacher.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username("username1")
                .password(password)
                .build();
        bob = Teacher.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username("username2")
                .password(password)
                .build();
        alice = entityManager.merge(alice);
        bob = entityManager.merge(bob);

        charlie = Student.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(groupA)
                .username("username3")
                .password(password)
                .build();
        diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .username("username4")
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
                .build();
        offlineClass = OfflineClass.builder()
                .startTime((LocalDateTime.of(2024, 4, 23, 11, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .endTime((LocalDateTime.of(2024, 4, 23, 12, 0)).atZone(ZoneId.of("Europe/Kiev")))
                .course(physics)
                .teacher(bob)
                .group(groupB)
                .location(ics)
                .build();
        onlineClass = entityManager.merge(onlineClass);
        offlineClass = entityManager.merge(offlineClass);

        groupA.setStudyClasses(Arrays.asList(onlineClass));
        groupA = entityManager.persist(groupA);

        entityManager.flush();
    }

    @Test
    void saveUser() {
        UserResponse userToSave = UserResponse.builder()
                .firstName("Test")
                .lastName("Test")
                .userType("TEACHER")
                .username("username")
                .password("password")
                .build();

        userService.saveUser(userToSave);
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(5));
        List<UserResponse> users = userService.findAllUsersWithPagination(page).toList();
        UserResponse actualUser = users.get(4);

        assertEquals(userToSave.getFirstName(), actualUser.getFirstName());
        assertEquals(userToSave.getLastName(), actualUser.getLastName());
        assertEquals(userToSave.getUserType(), actualUser.getUserType());
        assertEquals(userToSave.getUsername(), actualUser.getUsername());
    }

    @Test
    void findUserById() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(3);
        String userId = user.getId();
        assertEquals(user, studentMapper.toDto((Student) userService.findUserById(userId)));
    }

    @Test
    void findUserDTOById() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(3);
        String userId = user.getId();
        UserFormRequest dto = userService.findUserDTOById(userId);
        assertEquals(user.getId(), dto.getId());
    }

    @Test
    void findUserByName() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(3);
        String username = user.getUsername();
        assertEquals(user, studentMapper.toDto((Student) userService.findUserByUsername(username)));
    }

    @Test
    void assertThrowsExceptionIfCourseIsNotPresent(){
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById("testId"));
    }

    @Test
    void updateStudent() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(3);
        String userId = user.getId();

        UserFormRequest studentToSave = UserFormRequest.builder()
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(0);
        String userId = user.getId();

        StudyClassResponse onlineClassDTO = studyClassMapper.toDto(onlineClass);
        StudyClassResponse offlineClassDTO = studyClassMapper.toDto(offlineClass);

        UserFormRequest teacherToSave = UserFormRequest.builder()
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
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(4));
        UserResponse user = userService.findAllUsersWithPagination(page).toList().get(0);
        String userId = user.getId();

        userService.deleteUserById(userId);
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    void findAllUsersWithPagination() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        TeacherResponse aliceDTO = TeacherResponse.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username("username1")
                .password(password)
                .build();
        TeacherResponse bobDTO = TeacherResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username("username2")
                .password(password)
                .build();
        List<UserResponse> expectedUsers = Arrays.asList(aliceDTO, bobDTO);
        List<UserResponse> actualUsers = userService.findAllUsersWithPagination(page).toList().subList(0, 2);
        actualUsers.forEach(user -> {
            user.setId(null);
        });

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void findAllStudentsWithPagination() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));

        StudentResponse charlieDTO = StudentResponse.builder()
                .firstName("Charlie")
                .lastName("Williams")
                .group(GroupFormation.builder().name("Group A").build())
                .username("username3")
                .password(password)
                .build();
        StudentResponse dianaDTO = StudentResponse.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(GroupFormation.builder().name("Group B").build())
                .username("username4")
                .password(password)
                .build();

        List<StudentResponse> expectedStudents = Arrays.asList(charlieDTO, dianaDTO);
        Page<StudentResponse> actualStudents = userService.findAllStudentsWithPagination(page);
        actualStudents.forEach(user -> {
            user.setId(null);
            user.getGroup().setId(null);
        });

        assertIterableEquals(expectedStudents, actualStudents.toList());
    }

    @Test
    void findAllTeachersWithPagination() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));

        TeacherResponse aliceDTO = TeacherResponse.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username("username1")
                .password(password)
                .build();
        TeacherResponse bobDTO = TeacherResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username("username2")
                .password(password)
                .build();

        List<TeacherResponse> expectedTeachers = Arrays.asList(aliceDTO, bobDTO);
        Page<TeacherResponse> actualTeachers = userService.findAllTeachersWithPagination(page);
        actualTeachers.forEach(user -> {
            user.setId(null);
        });

        assertEquals(expectedTeachers, actualTeachers.toList());
    }

    @Test
    void findAllTeachers() {
        TeacherResponse aliceDTO = TeacherResponse.builder()
                .firstName("Alice")
                .lastName("Smith")
                .username("username1")
                .password(password)
                .build();
        TeacherResponse bobDTO = TeacherResponse.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .username("username2")
                .password(password)
                .build();

        List<TeacherResponse> expectedTeachers = Arrays.asList(aliceDTO, bobDTO);
        List<TeacherResponse> actualTeachers = userService.findAllTeachers();
        actualTeachers.forEach(user -> {
            user.setId(null);
        });

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    @WithMockUser(username = "username3", authorities = {"view"})
    void testShowCoursesAssignedToStudent() {
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        Page<CourseDTO> coursesActual = userService.showCoursesThatAssignedToStudent(page);

        assertEquals(courseMapper.toDto(math), coursesActual.toList().getFirst());
    }
}
