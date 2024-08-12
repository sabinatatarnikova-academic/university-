package com.foxminded.university.service.classes;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.dtos.DateRange;
import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.enums.Regularity;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.schedule.ScheduleTimeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GlobalStudyClassesImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GlobalStudyClassesService globalStudyClassesService;

    @Autowired
    private ScheduleTimeService scheduleTimeService;

    @Autowired
    private StudyClassService studyClassService;

    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    @Transactional
    public void init() {
        entityManager.createNativeQuery("DELETE FROM classes").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM users").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM courses").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM groups").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM locations").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM schedule").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM schedule_times").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM global_classes").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO groups (group_id, group_name) VALUES ('d15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'Group A')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO groups (group_id, group_name) VALUES ('99e56438-c880-41ba-87aa-080b7413ad34', 'Group B')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO courses (course_id, course_name) VALUES ('fd17d1ab-2c1f-46f7-8501-1a2129f0c933', 'Mathematics')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO courses (course_id, course_name) VALUES ('bc5b7198-af0d-42d5-8b5b-83427cf9ba1d', 'History')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO locations (location_id, department, classroom) VALUES ('34af61d9-5a22-4636-bef3-c0a5e73036c6', 'ICS', '101')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time) VALUES ('THIRD_LECTURE', 'Third Lecture', '11:40:00', '13:10:00')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time) VALUES ('SECOND_LECTURE', 'Second Lecture', '09:50:00', '11:20:00')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time) VALUES ('FOURTH_LECTURE', 'Fourth Lecture', '13:30:00', '15:00:00')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time) VALUES ('FIRST_LECTURE', 'First Lecture', '08:00:00', '09:30:00')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO schedule_times (schedule_time_id, schedule_time_name, start_time, end_time) VALUES ('FIFTH_LECTURE', 'Fifth Lecture', '15:20:00', '16:50:00')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO schedule (schedule_id, group_id, start_date, end_date) VALUES ('27936573-c9b3-4403-b479-56440de97a78', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', '2024-07-01', '2024-08-11')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO global_classes (global_class_id, schedule_id, schedule_day, schedule_time, regularity, start_date, end_date) VALUES ('de7d65ad-974e-46d5-ac4a-95b558389857', '27936573-c9b3-4403-b479-56440de97a78', 'MONDAY', 'FIRST_LECTURE', 'EACH_WEEK', null, null)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO global_classes (global_class_id, schedule_id, schedule_day, schedule_time, regularity, start_date, end_date) VALUES ('8c31d98a-b434-4a45-ace6-3d72495a626e', '27936573-c9b3-4403-b479-56440de97a78', 'WEDNESDAY', 'THIRD_LECTURE', 'EACH_WEEK', null, null)").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO global_classes (global_class_id, schedule_id, schedule_day, schedule_time, regularity, start_date, end_date) VALUES ('1c31d98a-b434-4a45-ace6-3d72495a626e', '27936573-c9b3-4403-b479-56440de97a78', 'TUESDAY', 'THIRD_LECTURE', 'EACH_WEEK', null, null)").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO public.users (user_id, first_name, last_name, user_type, group_id, username, password) VALUES ('9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'John', 'Doe', 'TEACHER', null, 'teacher', '$2a$12$auvXRvsX0KCioaLjD7TYSO5TRtDZSj179qOkuj6vmK/2OobS8B9k2')").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('38485358-ea71-4d3e-a718-f1ce6c6202c5', '2024-07-01 05:00:00.000000', '2024-07-01 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'de7d65ad-974e-46d5-ac4a-95b558389857', 'ONLINE', 'wwww', null)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('a75c102c-bb24-4e7c-a6d7-ad26010035a3', '2024-07-08 05:00:00.000000', '2024-07-08 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'de7d65ad-974e-46d5-ac4a-95b558389857', 'ONLINE', 'wwww', null)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('95ea1e55-d617-4922-9af8-400c731b21aa', '2024-07-15 05:00:00.000000', '2024-07-15 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'de7d65ad-974e-46d5-ac4a-95b558389857', 'ONLINE', 'wwww', null)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('72f6311d-6d96-4162-9b75-3def674875c1', '2024-07-22 05:00:00.000000', '2024-07-22 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'de7d65ad-974e-46d5-ac4a-95b558389857', 'ONLINE', 'wwww', null)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('06181b25-2845-4f20-8482-5d0e1f8a9ae6', '2024-07-29 05:00:00.000000', '2024-07-29 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', 'de7d65ad-974e-46d5-ac4a-95b558389857', 'ONLINE', 'wwww', null)").executeUpdate();

        entityManager.createNativeQuery("INSERT INTO classes (class_id, start_time, end_time, course_id, user_id, group_id, global_class_id, class_type, url, location_id) VALUES ('0b59c6c5-f637-4c6e-822f-91c9b788e3c0', '2024-08-05 05:00:00.000000', '2024-08-05 06:30:00.000000', 'fd17d1ab-2c1f-46f7-8501-1a2129f0c933', '9f6cfb74-e71e-4d00-86e8-3307e8b6865e', 'd15b0018-47e9-4281-9dc8-a5aaf2bdf951', '1c31d98a-b434-4a45-ace6-3d72495a626e', 'OFFLINE', null, '34af61d9-5a22-4636-bef3-c0a5e73036c6')").executeUpdate();

        entityManager.flush();
    }

    @Test
    @Transactional
    void testParseScheduleListToGlobalClasses() {
        List<GlobalStudyClassRequest> globalClasses = new ArrayList<>();
        globalClasses.add(GlobalStudyClassRequest.builder()
                .teacherId("9f6cfb74-e71e-4d00-86e8-3307e8b6865e")
                .courseId("fd17d1ab-2c1f-46f7-8501-1a2129f0c933")
                .groupId("d15b0018-47e9-4281-9dc8-a5aaf2bdf951")
                .scheduleId("27936573-c9b3-4403-b479-56440de97a78")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTimeId("FIRST_LECTURE")
                .regularity(Regularity.EACH_WEEK)
                .classType("ONLINE")
                .url("wwww")
                .userZone("UTC")
                .build());

        globalClasses.add(GlobalStudyClassRequest.builder()
                .teacherId("9f6cfb74-e71e-4d00-86e8-3307e8b6865e")
                .courseId("bc5b7198-af0d-42d5-8b5b-83427cf9ba1d")
                .groupId("99e56438-c880-41ba-87aa-080b7413ad34")
                .scheduleId("27936573-c9b3-4403-b479-56440de97a78")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 3))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .scheduleDay(DayOfWeek.WEDNESDAY)
                .scheduleTimeId("THIRD_LECTURE")
                .regularity(Regularity.EACH_WEEK)
                .classType("ONLINE")
                .url("wwww")
                .userZone("UTC")
                .build());

        globalStudyClassesService.parseScheduleListToGlobalClasses(globalClasses);

        Schedule schedule = scheduleService.findScheduleById("27936573-c9b3-4403-b479-56440de97a78");
        List<GlobalStudyClass> globalStudyClasses = schedule.getGlobalStudyClasses();
        assertEquals(2, globalStudyClasses.size());

        GlobalStudyClass firstGlobalClass = globalStudyClasses.get(0);
        assertEquals(DayOfWeek.MONDAY, firstGlobalClass.getScheduleDay());
        assertEquals("FIRST_LECTURE", firstGlobalClass.getScheduleTime().getId());

        GlobalStudyClass secondGlobalClass = globalStudyClasses.get(1);
        assertEquals(DayOfWeek.WEDNESDAY, secondGlobalClass.getScheduleDay());
        assertEquals("THIRD_LECTURE", secondGlobalClass.getScheduleTime().getId());
    }

    @Test
    void testParseGlobalClassToOnlineStudyClasses() {
        GlobalStudyClassRequest request = GlobalStudyClassRequest.builder()
                .teacherId("9f6cfb74-e71e-4d00-86e8-3307e8b6865e")
                .courseId("fd17d1ab-2c1f-46f7-8501-1a2129f0c933")
                .groupId("d15b0018-47e9-4281-9dc8-a5aaf2bdf951")
                .scheduleId("27936573-c9b3-4403-b479-56440de97a78")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .scheduleDay(DayOfWeek.MONDAY)
                .scheduleTimeId("FIRST_LECTURE")
                .regularity(Regularity.EACH_WEEK)
                .classType("ONLINE")
                .url("wwww")
                .userZone("UTC")
                .build();

        GlobalStudyClass globalStudyClass = globalStudyClassesService.parseGlobalClassToStudyClasses(request);

        assertEquals(Regularity.EACH_WEEK, globalStudyClass.getRegularity());
        assertEquals("FIRST_LECTURE", globalStudyClass.getScheduleTime().getId());
        assertEquals(DayOfWeek.MONDAY, globalStudyClass.getScheduleDay());
        assertEquals("27936573-c9b3-4403-b479-56440de97a78", globalStudyClass.getSchedule().getId());

        List<StudyClass> studyClasses = globalStudyClass.getStudyClasses();
        assertEquals(6, studyClasses.size());

        for (StudyClass studyClass : studyClasses) {
            assertEquals("ONLINE", studyClass.getClassType());
            assertEquals("9f6cfb74-e71e-4d00-86e8-3307e8b6865e", studyClass.getTeacher().getId());
            assertEquals("fd17d1ab-2c1f-46f7-8501-1a2129f0c933", studyClass.getCourse().getId());
            assertEquals("d15b0018-47e9-4281-9dc8-a5aaf2bdf951", studyClass.getGroup().getId());
            assertEquals(globalStudyClass, studyClass.getGlobalStudyClass());
            assertEquals("wwww", ((OnlineClass) studyClass).getUrl());
        }
    }

    @Test
    void testParseGlobalClassToOfflineStudyClasses() {
        GlobalStudyClassRequest request = GlobalStudyClassRequest.builder()
                .teacherId("9f6cfb74-e71e-4d00-86e8-3307e8b6865e")
                .courseId("fd17d1ab-2c1f-46f7-8501-1a2129f0c933")
                .groupId("d15b0018-47e9-4281-9dc8-a5aaf2bdf951")
                .scheduleId("27936573-c9b3-4403-b479-56440de97a78")
                .dateRange(DateRange.builder()
                        .startDate(LocalDate.of(2024, 7, 1))
                        .endDate(LocalDate.of(2024, 8, 11)).build())
                .scheduleDay(DayOfWeek.TUESDAY)
                .scheduleTimeId("THIRD_LECTURE")
                .regularity(Regularity.EACH_WEEK)
                .classType("OFFLINE")
                .locationId("34af61d9-5a22-4636-bef3-c0a5e73036c6")
                .userZone("UTC")
                .build();

        GlobalStudyClass globalStudyClass = globalStudyClassesService.parseGlobalClassToStudyClasses(request);

        assertEquals(Regularity.EACH_WEEK, globalStudyClass.getRegularity());
        assertEquals("THIRD_LECTURE", globalStudyClass.getScheduleTime().getId());
        assertEquals(DayOfWeek.TUESDAY, globalStudyClass.getScheduleDay());
        assertEquals("27936573-c9b3-4403-b479-56440de97a78", globalStudyClass.getSchedule().getId());

        List<StudyClass> studyClasses = globalStudyClass.getStudyClasses();
        assertEquals(6, studyClasses.size());

        for (StudyClass studyClass : studyClasses) {
            assertEquals("OFFLINE", studyClass.getClassType());
            assertEquals("9f6cfb74-e71e-4d00-86e8-3307e8b6865e", studyClass.getTeacher().getId());
            assertEquals("fd17d1ab-2c1f-46f7-8501-1a2129f0c933", studyClass.getCourse().getId());
            assertEquals("d15b0018-47e9-4281-9dc8-a5aaf2bdf951", studyClass.getGroup().getId());
            assertEquals(globalStudyClass, studyClass.getGlobalStudyClass());
            assertEquals("34af61d9-5a22-4636-bef3-c0a5e73036c6", ((OfflineClass) studyClass).getLocation().getId());
        }
    }

    @Test
    void findGlobalClassByIdTest() {
        String globalClassId = "de7d65ad-974e-46d5-ac4a-95b558389857";
        GlobalStudyClass actual = globalStudyClassesService.findGlobalClassById(globalClassId);

        assertEquals(actual.getId(), globalClassId);
        assertEquals(actual.getStudyClasses().getFirst(), studyClassService.findClassById("38485358-ea71-4d3e-a718-f1ce6c6202c5"));
        assertEquals(DayOfWeek.MONDAY, actual.getScheduleDay());
        assertEquals(actual.getScheduleTime(), scheduleTimeService.findLectureTimeById("FIRST_LECTURE"));
    }

    @Test
    void deleteGlobalClassTest() {
        String globalClassId = "de7d65ad-974e-46d5-ac4a-95b558389857";
        globalStudyClassesService.deleteGlobalClass(globalClassId);
        assertThrows(EntityNotFoundException.class, () -> globalStudyClassesService.findGlobalClassById(globalClassId));
    }
}
