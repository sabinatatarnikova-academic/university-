package com.foxminded.university.service.schedule;

import com.foxminded.university.config.TestConfig;
import com.foxminded.university.model.entity.ScheduleTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class ScheduleTimeServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScheduleTimeService scheduleTimeService;

    private ScheduleTimes firstLecture;
    private ScheduleTimes secondLecture;

    @BeforeEach
    @Transactional
    public void init() {
        entityManager.getEntityManager().createNativeQuery("DELETE FROM schedule_times").executeUpdate();

        firstLecture = ScheduleTimes.builder()
                .name("First lecture")
                .startTime(LocalTime.of(8, 00, 00))
                .endTime(LocalTime.of(9, 30, 00))
                .build();

        firstLecture = entityManager.merge(firstLecture);

        secondLecture = ScheduleTimes.builder()
                .name("Second lecture")
                .startTime(LocalTime.of(9, 50, 00))
                .endTime(LocalTime.of(11, 20, 00))
                .build();

        secondLecture = entityManager.merge(secondLecture);

        entityManager.flush();
    }

    @Test
    void findAllLectureTimesTest() {
        List<ScheduleTimes> actual = scheduleTimeService.findAllLectureTimes();

        List<ScheduleTimes> expected = Arrays.asList(
                ScheduleTimes.builder()
                        .id(firstLecture.getId())
                        .name("First lecture")
                        .startTime(LocalTime.of(8, 0, 0))
                        .endTime(LocalTime.of(9, 30, 0))
                        .build(),
                ScheduleTimes.builder()
                        .id(secondLecture.getId())
                        .name("Second lecture")
                        .startTime(LocalTime.of(9, 50, 0))
                        .endTime(LocalTime.of(11, 20, 0))
                        .build()
        );

        assertEquals(actual, expected);
    }

    @Test
    void findLectureTimeByIdTest() {
        String id = firstLecture.getId();
        ScheduleTimes lectureTimeById = scheduleTimeService.findLectureTimeById(id);
        assertEquals(firstLecture, lectureTimeById);
    }
}
