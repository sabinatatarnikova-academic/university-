package com.foxminded.university.service.course;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.request.CourseRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.classes.StudyClassResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.classes.OfflineClass;
import com.foxminded.university.model.entity.classes.OnlineClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.service.classes.StudyClassService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@Import(TestConfig.class)
class CourseServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private StudyClassService studyClassService;

    @Autowired
    private CourseMapper courseMapper;

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
                .studyClasses(new ArrayList<>())
                .build();
        Course physics = Course.builder()
                .name("Physics")
                .studyClasses(new ArrayList<>())
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
                .group(groupA)
                .username(username)
                .password(password)
                .build();
        Student diana = Student.builder()
                .firstName("Diana")
                .lastName("Brown")
                .group(groupB)
                .username(username)
                .password(password)
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
    void saveCourse() {
        CourseDTO courseToSave = CourseDTO.builder()
                .name("Mugiwaras")
                .build();

        courseService.saveCourse(courseToSave);
        Course course = courseService.findCourseByName("Mugiwaras");
        course.setId(null);
        assertThat(courseMapper.toEntity(courseToSave), is(course));
    }

    @Test
    void findCourseById() {
        Course courseByName = courseService.findCourseByName("Mathematics");
        String courseId = courseByName.getId();
        Course course = courseService.findCourseById(courseId);

        assertEquals(course, courseByName);
    }

    @Test
    void assertThrowsExceptionIfCourseIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> courseService.findCourseById("testId"));
    }

    @Test
    void findCourseByName() {
        Course course = courseService.findCourseByName("Mathematics");
        CourseDTO courseDTO = courseService.findAllCourses().get(0);

        assertEquals(course.getId(), courseDTO.getId());
        assertEquals(course.getName(), courseDTO.getName());
    }

    @Test
    void assertThrowsExceptionIfCourseIsNotPresentByName(){
        assertThrows(NoSuchElementException.class, () -> courseService.findCourseByName("test"));
    }

    @Test
    void updateCourse() {
        List<StudyClassResponse> classes = studyClassService.findAllClasses();
        Course courseByName = courseService.findCourseByName("Mathematics");
        String courseId = courseByName.getId();
        CourseRequest courseToUpdate = CourseRequest.builder()
                .id(courseId)
                .name("Update name")
                .studyClasses(List.of(classes.get(0).getId(), classes.get(1).getId()))
                .build();
        courseService.updateCourse(courseToUpdate);
        Course updatedCourse = courseService.findCourseByName("Update name");
        assertEquals("Update name", updatedCourse.getName());
    }

    @Test
    void deleteCourseById() {
        String courseId = courseService.findCourseByName("Mathematics").getId();
        courseService.deleteCourseById(courseId);
        assertThrows(NoSuchElementException.class, () -> courseService.findCourseById(courseId));
    }

    @Test
    void findAllCoursesWithPagination() {
        CourseDTO courseA = CourseDTO.builder()
                .name("Mathematics")
                .studyClasses(new ArrayList<>())
                .build();
        CourseDTO courseB = CourseDTO.builder()
                .name("Physics")
                .studyClasses(new ArrayList<>())
                .build();

        List<CourseDTO> courses = Arrays.asList(courseA, courseB);
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        Page<CourseDTO> coursesActual = courseService.findAllCoursesWithPagination(page);
        coursesActual.forEach(course -> course.setId(null));
        assertThat(coursesActual.toList(), is(courses));
    }
}
