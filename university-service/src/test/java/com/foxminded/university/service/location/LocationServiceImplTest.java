package com.foxminded.university.service.location;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class LocationServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationServiceImpl locationService;

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
                .department("ICS")
                .classroom("101")
                .build();
        Location arts = Location.builder()
                .department("FDU")
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
    void saveLocation() {
        Location locationToSave = Location.builder()
                .department("FCR")
                .classroom("123")
                .build();

        locationService.saveLocation(locationToSave);
        Location location = locationService.findLocationByDepartmentAndClassroom("FCR", "123");
        location.setId(null);
        assertThat(locationToSave, is(location));
    }

    @Test
    void findLocationById() {
        Location locationByName = locationService.findLocationByDepartmentAndClassroom("ICS","101");
        String locationId = locationByName.getId();
        Location location = locationService.findLocationById(locationId);
        location.setId(null);
        Location locationA = Location.builder()
                .department("ICS")
                .classroom("101")
                .build();
        assertThat(location, is(locationA));
    }

    @Test
    void assertThrowsExceptionIfLocationIsNotPresent(){
        assertThrows(NoSuchElementException.class, () -> locationService.findLocationById("testId"));
    }

    @Test
    void findLocationByDepartmentAndClassroom() {
        Location location = locationService.findLocationByDepartmentAndClassroom("ICS", "101");
        location.setId(null);
        Location locationA = Location.builder()
                .department("ICS")
                .classroom("101")
                .build();
        assertThat(location, is(locationA));
    }

    @Test
    void updateLocation() {
        Location location = locationService.findLocationByDepartmentAndClassroom("ICS", "101");
        String locationId = location.getId();
        Location locationToUpdate = Location.builder()
                .id(locationId)
                .department("Update name")
                .classroom("222")
                .build();
        locationService.updateLocation(locationToUpdate);
        Location updatedLocation = locationService.findLocationByDepartmentAndClassroom("Update name", "222");
        assertThat(locationToUpdate, is(updatedLocation));
    }

    @Test
    void deleteLocationById() {
        String locationId = locationService.findLocationByDepartmentAndClassroom("ICS", "101").getId();
        locationService.deleteLocationById(locationId);
        assertThrows(NoSuchElementException.class, () -> locationService.findLocationById(locationId));
    }

    @Test
    void findAllLocationsWithPagination() {
        Location locationA = Location.builder()
                .department("ICS")
                .classroom("101")
                .build();
        Location locationB = Location.builder()
                .department("FDU")
                .classroom("102")
                .build();

        List<Location> locations = Arrays.asList(locationA, locationB);
        RequestPage page = PageUtils.createPage(String.valueOf(0), String.valueOf(2));
        List<Location> locationsActual = locationService.findAllLocationsWithPagination(page);
        locationsActual.forEach(course -> course.setId(null));
        assertThat(locationsActual, is(locations));
    }
}
