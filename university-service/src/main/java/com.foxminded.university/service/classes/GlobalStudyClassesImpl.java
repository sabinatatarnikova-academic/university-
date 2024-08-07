package com.foxminded.university.service.classes;

import com.foxminded.university.model.dtos.request.schedule.GlobalStudyClassRequest;
import com.foxminded.university.model.entity.Course;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.Location;
import com.foxminded.university.model.entity.ScheduleTimes;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainclasses.OfflineClass;
import com.foxminded.university.model.entity.classes.plainclasses.OnlineClass;
import com.foxminded.university.model.entity.classes.plainclasses.StudyClass;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.enums.Regularity;
import com.foxminded.university.repository.GlobalStudyClassRepository;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.schedule.ScheduleService;
import com.foxminded.university.service.schedule.ScheduleTimeService;
import com.foxminded.university.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GlobalStudyClassesImpl implements GlobalStudyClassesService {

    private final GlobalStudyClassRepository globalStudyClassRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final LocationService locationService;
    private final ScheduleService scheduleService;
    private final ScheduleTimeService scheduleTimeService;

    @Override
    @Transactional
    public void parseScheduleListToGlobalClasses(List<GlobalStudyClassRequest> globalClasses) {
        Schedule schedule = scheduleService.findScheduleById(globalClasses.get(0).getScheduleId());
        List<GlobalStudyClass> globalStudyClasses = globalClasses.stream().map(this::parseGlobalClassToStudyClasses).collect(Collectors.toList());
        schedule.setGlobalStudyClasses(globalStudyClasses);
        scheduleService.saveSchedule(schedule);
    }

    @Override
    @Transactional
    public GlobalStudyClass parseGlobalClassToStudyClasses(GlobalStudyClassRequest request) {
        Teacher teacher = userService.findUserById(request.getTeacherId());
        Course course = courseService.findCourseById(request.getCourseId());
        Group group = groupService.findGroupById(request.getGroupId());
        Schedule schedule = scheduleService.findScheduleById(request.getScheduleId());

        LocalDate startDate = request.getDateRange().getStartDate();
        LocalDate endDate = request.getDateRange().getEndDate();
        DayOfWeek scheduleDay = request.getScheduleDay();
        ScheduleTimes scheduleTime = scheduleTimeService.findLectureTimeById(request.getScheduleTimeId());
        Regularity regularity = request.getRegularity();
        String userZone = request.getUserZone();

        List<ZonedDateTime[]> scheduleDateTimes = generateDatesForStudyClasses(startDate, endDate, scheduleDay, scheduleTime, regularity, userZone);

        GlobalStudyClass globalStudyClass = GlobalStudyClass.builder()
                .regularity(regularity)
                .scheduleTime(scheduleTime)
                .scheduleDay(request.getScheduleDay())
                .schedule(schedule)
                .build();
        globalStudyClassRepository.save(globalStudyClass);

        List<StudyClass> studyClasses = new ArrayList<>();
        for (ZonedDateTime[] times : scheduleDateTimes) {
            if (Objects.equals(request.getClassType(), "ONLINE")) {
                StudyClass studyClass = OnlineClass.builder()
                        .classType(request.getClassType())
                        .teacher(teacher)
                        .course(course)
                        .group(group)
                        .startTime(times[0])
                        .endTime(times[1])
                        .globalStudyClass(globalStudyClass)
                        .url(request.getUrl())
                        .build();
                studyClasses.add(studyClass);
            } else {
                Location location = request.getLocationId().isEmpty() ? null : locationService.findLocationById(request.getLocationId());
                StudyClass studyClass = OfflineClass.builder()
                        .classType(request.getClassType())
                        .teacher(teacher)
                        .course(course)
                        .group(group)
                        .startTime(times[0])
                        .endTime(times[1])
                        .globalStudyClass(globalStudyClass)
                        .location(location)
                        .build();
                studyClasses.add(studyClass);
            }
        }

        globalStudyClass.setStudyClasses(studyClasses);
        globalStudyClassRepository.save(globalStudyClass);
        return globalStudyClass;
    }

    private List<ZonedDateTime[]> generateDatesForStudyClasses(LocalDate currentDate, LocalDate endDate, DayOfWeek targetDayOfWeek, ScheduleTimes scheduleTime, Regularity regularity, String zone) {
        List<ZonedDateTime[]> dates = new ArrayList<>();
        ZoneId zoneId = ZoneId.of(zone);
        int interval = regularity == Regularity.EACH_WEEK ? 1 : 2;
        while (currentDate.getDayOfWeek() != targetDayOfWeek) {
            currentDate = currentDate.plusDays(1);
        }
        while (!currentDate.isAfter(endDate)) {
            ZonedDateTime startDateTimeUserZone = LocalDateTime.of(currentDate, scheduleTime.getStartTime()).atZone(zoneId);
            ZonedDateTime endDateTimeUserZone = LocalDateTime.of(currentDate, scheduleTime.getEndTime()).atZone(zoneId);

            ZonedDateTime startDateTimeUTC = startDateTimeUserZone.withZoneSameInstant(ZoneOffset.UTC);
            ZonedDateTime endDateTimeUTC = endDateTimeUserZone.withZoneSameInstant(ZoneOffset.UTC);

            dates.add(new ZonedDateTime[]{startDateTimeUTC, endDateTimeUTC});
            currentDate = currentDate.plusWeeks(interval);
        }

        return dates;
    }

    @Override
    public GlobalStudyClass findGlobalClassById(String id) {
        Optional<GlobalStudyClass> globalStudyClass = globalStudyClassRepository.findById(id);
        if (!globalStudyClass.isPresent()) {
            log.error("Global class with id {} not found", id);
            throw new EntityNotFoundException();
        }
        log.info("Founded global class with id {}", id);
        return globalStudyClass.get();
    }

    @Override
    public void deleteGlobalClass(String id) {
        globalStudyClassRepository.deleteById(id);
        log.info("Global class with id {} was delted", id);
    }
}
