package com.foxminded.university.service.schedule;

import com.foxminded.university.model.dtos.request.LocationDTO;
import com.foxminded.university.model.dtos.request.schedule.ScheduleCreateRequest;
import com.foxminded.university.model.dtos.response.CourseDTO;
import com.foxminded.university.model.dtos.response.schedule.ScheduleClassesResponse;
import com.foxminded.university.model.dtos.response.schedule.ScheduleViewResponse;
import com.foxminded.university.model.dtos.response.schedule.StudyClassScheduleResponse;
import com.foxminded.university.model.dtos.response.schedule.ViewScheduleResponse;
import com.foxminded.university.model.dtos.response.users.TeacherResponse;
import com.foxminded.university.model.entity.Group;
import com.foxminded.university.model.entity.classes.GlobalStudyClass;
import com.foxminded.university.model.entity.classes.Schedule;
import com.foxminded.university.model.entity.classes.plainClasses.StudyClass;
import com.foxminded.university.model.entity.users.Student;
import com.foxminded.university.model.entity.users.Teacher;
import com.foxminded.university.model.enums.ScheduleDay;
import com.foxminded.university.model.enums.ScheduleTime;
import com.foxminded.university.repository.ScheduleRepository;
import com.foxminded.university.repository.StudyClassRepository;
import com.foxminded.university.service.course.CourseService;
import com.foxminded.university.service.group.GroupService;
import com.foxminded.university.service.location.LocationService;
import com.foxminded.university.service.user.UserService;
import com.foxminded.university.utils.RequestPage;
import com.foxminded.university.utils.mappers.ScheduleMapper;
import com.foxminded.university.utils.mappers.classes.StudyClassMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupService groupService;
    private final StudyClassMapper studyClassMapper;
    private final ScheduleMapper scheduleMapper;
    private final CourseService courseService;
    private final UserService userService;
    private final LocationService locationService;
    private final StudyClassRepository studyClassRepository;

    @Override
    @Transactional
    public String addSchedule(ScheduleCreateRequest request) {
        Group group = groupService.findGroupById(request.getGroupId());
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        Schedule schedule = Schedule.builder()
                .startDate(startDate)
                .endDate(endDate)
                .group(group)
                .build();

        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    @Override
    public Schedule findScheduleById(String id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (!schedule.isPresent()) {
            log.error("Schedule with id {} not found", id);
            throw new EntityNotFoundException();
        }
        log.info("Founded schedule with id {}", id);
        return schedule.get();
    }

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
        log.info("Schedule was saved.");
    }

    @Override
    public List<StudyClassScheduleResponse> paginateScheduleByWeek(String id, LocalDate startDateOfWeek, LocalDate endDateOfWeek) {
        Schedule schedule = findScheduleById(id);
        List<GlobalStudyClass> globalStudyClasses = schedule.getGlobalStudyClasses();
        globalStudyClasses.stream().map(globalStudyClass -> {
            List<StudyClass> studyClasses = globalStudyClass.getStudyClasses();
            return studyClasses;
        }).collect(Collectors.toList());


        List<StudyClass> studyClasses = schedule.getGlobalStudyClasses().stream()
                .flatMap(globalStudyClass -> globalStudyClass.getStudyClasses().stream())
                .filter(studyClass -> !studyClass.getStartTime().toLocalDate().isBefore(startDateOfWeek) &&
                        !studyClass.getStartTime().toLocalDate().isAfter(endDateOfWeek))
                .collect(Collectors.toList());

        return studyClasses.stream().map(studyClassMapper::toScheduleDto).collect(Collectors.toList());
    }

    @Override
    public Page<ViewScheduleResponse> findAllSchedulesWithPagination(RequestPage requestPage) {
        int pageNumber = requestPage.getPageNumber();
        int pageSize = requestPage.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        List<ViewScheduleResponse> scheduleViewRespons = schedulePage.stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
        Page<ViewScheduleResponse> pageResult = new PageImpl<>(scheduleViewRespons, pageable, schedulePage.getTotalElements());
        log.info("Found {} users", pageResult.getTotalPages());
        return pageResult;
    }

    @Override
    public void deleteSchedule(String id) {
        scheduleRepository.deleteById(id);
        log.info("Schedule with id {} was deleted", id);
    }

    @Override
    @Transactional
    public ScheduleClassesResponse getAllRequiredDataForAddingClassesToSchedule(String id) {
        Schedule schedule = findScheduleById(id);
        List<ScheduleTime> times = Arrays.asList(ScheduleTime.values());
        List<ScheduleDay> days = Arrays.asList(ScheduleDay.values());
        List<CourseDTO> courses = courseService.findAllCourses();
        List<TeacherResponse> teachers = userService.findAllTeachers();
        List<LocationDTO> locations = locationService.findAllLocations();

        ScheduleClassesResponse scheduleClassesResponse = ScheduleClassesResponse.builder()
                .times(times)
                .days(days)
                .scheduleId(id)
                .groupId(schedule.getGroup().getId())
                .groupName(schedule.getGroup().getName())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .courses(courses)
                .teachers(teachers)
                .locations(locations)
                .globalStudyClasses(schedule.getGlobalStudyClasses())
                .build();
        return scheduleClassesResponse;
    }

    @Override
    @Transactional
    public ScheduleViewResponse getAllRequiredDataForViewingSchedule(String id, LocalDate userDate) {
        LocalDate weekStart = userDate.with(java.time.DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        List<StudyClassScheduleResponse> scheduleByWeek = paginateScheduleByWeek(id, weekStart, weekEnd);
        List<ScheduleTime> times = Arrays.asList(ScheduleTime.values());
        List<ScheduleDay> days = Arrays.asList(ScheduleDay.values());

        return ScheduleViewResponse.builder()
                .times(times)
                .days(days)
                .scheduleByWeek(scheduleByWeek)
                .weekStart(weekStart)
                .weekEnd(weekEnd)
                .groupName(findScheduleById(id).getGroup().getName())
                .build();
    }

    @Override
    @Transactional
    public ScheduleViewResponse getAllRequiredDataForViewingScheduleThatAssignedToStudent(LocalDate userDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Student student = (Student) userService.findUserByUsername(username);

        String scheduleId = student.getGroup().getSchedule().getId();
        return getAllRequiredDataForViewingSchedule(scheduleId, userDate);
    }

    @Override
    @Transactional
    public ScheduleViewResponse getAllRequiredDataForViewingSchedulesThatAssignedToTeacher(String teacherId, LocalDate userDate) {
        LocalDate startDateOfWeek = userDate.with(java.time.DayOfWeek.MONDAY);
        LocalDate endDateOfWeek = startDateOfWeek.plusDays(6);

        Teacher teacher = (Teacher) userService.findUserById(teacherId);
        List<StudyClass> studyClassesWithTeacher = studyClassRepository.findAll().stream().filter(studyClass -> studyClass.getTeacher().equals(teacher)).collect(Collectors.toList());
        List<StudyClass> studyClassesByWeek = studyClassesWithTeacher.stream().filter(studyClass -> !studyClass.getStartTime().toLocalDate().isBefore(startDateOfWeek) &&
                        !studyClass.getStartTime().toLocalDate().isAfter(endDateOfWeek))
                .collect(Collectors.toList());

        List<ScheduleTime> times = Arrays.asList(ScheduleTime.values());
        List<ScheduleDay> days = Arrays.asList(ScheduleDay.values());

        return ScheduleViewResponse.builder()
                .times(times)
                .days(days)
                .scheduleByWeek(studyClassesByWeek.stream().map(studyClassMapper::toScheduleDto).collect(Collectors.toList()))
                .weekStart(startDateOfWeek)
                .weekEnd(endDateOfWeek)
                .build();
    }
}
