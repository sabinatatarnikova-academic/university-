package com.foxminded.university.service.schedule;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.DateRange;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.enums.Regularity;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.PageUtils;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.ScheduleMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class ScheduleServiceImpTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ScheduleTimeService scheduleTimeService;

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
    private Schedule schedule;
    private GlobalStudyClass globalStudyClass;
    private ScheduleTimes scheduleTimes;
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
                .username("diana_username")
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

        scheduleTimes = ScheduleTimes.builder()
                .id("FIRST_LECTURE")
                .name("First lecture")
                .startTime(LocalTime.of(8, 00, 00))
                .endTime(LocalTime.of(9, 30, 00))
                .build();

        scheduleTimes = entityManager.merge(scheduleTimes);

        globalStudyClass = GlobalStudyClass.builder()
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTime(scheduleTimes)
                .regularity(Regularity.EACH_WEEK)
                .studyClasses(List.of(offlineClass))
                .build();

        globalStudyClass = entityManager.merge(globalStudyClass);

        offlineClass.setGlobalStudyClass(globalStudyClass);
        offlineClass = entityManager.persist(offlineClass);

        schedule = Schedule.builder()
                .group(groupB)
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 15))
                .globalStudyClasses(List.of(globalStudyClass))
                .build();

        schedule = entityManager.merge(schedule);

        globalStudyClass.setSchedule(schedule);
        schedule = entityManager.persist(schedule);

        groupB.setSchedule(schedule);
        groupB = entityManager.merge(groupB);

        entityManager.flush();
    }

    @Test
    void addScheduleTest() {
        String groupId = groupA.getId();
        LocalDate startDate = LocalDate.of(2024, 9, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 15);

        ScheduleCreateRequest createRequest = ScheduleCreateRequest.builder()
                .dateRange(DateRange.builder()
                        .startDate(startDate)
                        .endDate(endDate).build())
                .groupId(groupId)
                .build();

        String createdScheduleId = scheduleService.addSchedule(createRequest);
        Schedule schedule = scheduleService.findScheduleById(createdScheduleId);
        assertEquals(schedule.getGroup().getId(), groupId);
        assertEquals(schedule.getStartDate(), startDate);
        assertEquals(schedule.getEndDate(), endDate);
    }

    @Test
    void findScheduleById() {
        String id = schedule.getId();
        Schedule foundedSchedule = scheduleService.findScheduleById(id);
        assertEquals(schedule, foundedSchedule);
    }

    @Test
    void saveScheduleTest() {
        Schedule scheduleForSave = Schedule.builder()
                .group(groupA)
                .startDate(LocalDate.of(2024, 9, 1))
                .endDate(LocalDate.of(2024, 12, 15))
                .build();

        scheduleService.saveSchedule(scheduleForSave);
        assertEquals(scheduleForSave, scheduleService.findScheduleById(scheduleForSave.getId()));
    }

    @Test
    void findAllSchedulesWithPaginationTest() {
        List<ViewScheduleResponse> dto = List.of(scheduleMapper.toDto(schedule));
        RequestPage page = PageUtils.createPage(0, 1);

        assertEquals(scheduleService.findAllSchedulesWithPagination(page).toList(), dto);
    }

    @Test
    void deleteScheduleTest() {
        String scheduleId = schedule.getId();
        scheduleService.deleteSchedule(scheduleId);

        assertThrows(EntityNotFoundException.class, () -> scheduleService.findScheduleById(scheduleId));
    }

    @Test
    void getAllRequiredDataForAddingClassesToScheduleTest() {
        String scheduleId = schedule.getId();

        ScheduleClassesResponse scheduleClassesResponse = ScheduleClassesResponse.builder()
                .times(scheduleTimeService.findAllLectureTimes())
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleId(scheduleId)
                .groupId(schedule.getGroup().getId())
                .groupName(schedule.getGroup().getName())
                .dateRange(DateRange.builder()
                        .startDate(schedule.getStartDate())
                        .endDate(schedule.getEndDate())
                        .build())
                .courses(courseService.findAllCourses())
                .teachers(userService.findAllTeachers())
                .locations(locationService.findAllLocations())
                .globalStudyClasses(schedule.getGlobalStudyClasses())
                .build();

        ScheduleClassesResponse data = scheduleService.getAllRequiredDataForAddingClassesToSchedule(scheduleId);
        assertEquals(scheduleClassesResponse, data);
    }

    @Test
    void getAllRequiredDataForViewingScheduleTest() {
        String scheduleId = schedule.getId();
        LocalDate userDate = LocalDate.of(2024, 9, 5);
        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingSchedule(scheduleId, userDate);

        LocalDate weekStart = userDate.with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        ScheduleViewResponse scheduleViewResponse = ScheduleViewResponse.builder()
                .times(scheduleTimeService.findAllLectureTimes())
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleByWeek(Arrays.asList())
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .groupName(scheduleService.findScheduleById(scheduleId).getGroup().getName())
                .build();

        assertEquals(data, scheduleViewResponse);
    }

    @Test
    @WithMockUser(username = "diana_username", authorities = {"view"})
    void getAllRequiredDataForViewingScheduleThatAssignedToStudent() {
        String id = diana.getGroup().getSchedule().getId();
        LocalDate userDate = LocalDate.of(2024, 9, 5);

        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingScheduleThatAssignedToStudent(userDate);

        LocalDate weekStart = userDate.with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        ScheduleViewResponse scheduleViewResponse = ScheduleViewResponse.builder()
                .times(scheduleTimeService.findAllLectureTimes())
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleByWeek(Arrays.asList())
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .groupName(scheduleService.findScheduleById(id).getGroup().getName())
                .build();

        assertEquals(data, scheduleViewResponse);
    }

    @Test
    void getAllRequiredDataForViewingSchedulesThatAssignedToTeacherTest() {
        String id = bob.getId();
        LocalDate userDate = LocalDate.of(2024, 9, 5);
        ScheduleViewResponse data = scheduleService.getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(id, userDate);

        LocalDate weekStart = userDate.with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        ScheduleViewResponse scheduleViewResponse = ScheduleViewResponse.builder()
                .times(scheduleTimeService.findAllLectureTimes())
                .days(Arrays.asList(DayOfWeek.values()))
                .scheduleByWeek(Arrays.asList())
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .build();

        assertEquals(data, scheduleViewResponse);
    }
}
